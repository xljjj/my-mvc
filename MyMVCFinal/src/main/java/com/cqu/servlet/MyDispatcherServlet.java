package com.cqu.servlet;

import com.cqu.annotation.MyController;
import com.cqu.annotation.MyRequestMapping;
import com.cqu.annotation.MyRequestParam;
import com.cqu.context.MyApplicationContext;
import com.cqu.handler.MyHandler;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "MyDispatcherServlet", value = "/MyDispatcherServlet")
public class MyDispatcherServlet extends HttpServlet {
    //容器
    private MyApplicationContext myApplicationContext;
    //handler
    Map<String, MyHandler> handlerMap = new ConcurrentHashMap<>();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatch(request,response);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response) {
        MyHandler handler = getHandler(request);
        try {
            if (handler == null){
                //提示信息
                response.getWriter().print("<h1>404 NOT FOUND</h1>");
            }else{
                Type[] parameterTypes = handler.getMethod().getGenericParameterTypes();
                Object[] params = new Object[parameterTypes.length];
                for (int i=0;i<parameterTypes.length;i++){
                    Class<?> parameterType= (Class<?>) parameterTypes[i];
                    if ("HttpServletRequest".equals(parameterType.getSimpleName())){
                        params[i]=request;
                    }
                    else if ("HttpServletResponse".equals(parameterType.getSimpleName())){
                        params[i]=response;
                    }
                }
                //得到请求中的参数
                Map<String,String[]> parameterMap=request.getParameterMap();
                for (Map.Entry<String,String[]> entry:parameterMap.entrySet()){
                    String name=entry.getKey();
                    String value=entry.getValue()[0];
                    int index=hasMyRequestParam(handler.getMethod(),name);
                    if (index!=-1){
                        params[index]=value;
                    }
                    else{
                        //取变量名
                        List<String> parameterNames = getParameterNames(handler.getMethod());
                        for (int i=0;i<parameterNames.size();i++){
                            if (name.equals(parameterNames.get(i))){
                                params[i]=value;
                            }
                        }
                    }
                }

                //调用对应的方法并进行转发
                Object result=handler.getMethod().invoke(handler.getController(),params);
                if (result instanceof String){
                    //跳转到JSP页面
                    String viewName=(String)result;
                    if (viewName.contains(":")){
                        String viewType=viewName.split(":")[0];
                        String viewPage=viewName.split(":")[1];
                        if (viewType.equals("forward")){
                            request.getRequestDispatcher(viewPage).forward(request,response);
                        }
                        else{
                            response.sendRedirect(viewPage);
                        }
                    }
                    else{
                        //默认转发
                        request.getRequestDispatcher(viewName).forward(request,response);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    //获取Handler
    public MyHandler getHandler(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        MyHandler handler = handlerMap.get(requestURI);
        return handler;
    }

    //判断是否有注解以及位置
    public int hasMyRequestParam(Method method,String name){
        Parameter[] parameters=method.getParameters();
        for (int i=0;i< parameters.length;i++){
            Parameter p=parameters[i];
            if (p.isAnnotationPresent(MyRequestParam.class)){
                MyRequestParam myRequestParam=p.getAnnotation(MyRequestParam.class);
                String MyValue=myRequestParam.value();
                if (name.equals(MyValue)){
                    return i;
                }
            }
        }
        return -1;
    }

    //得到方法参数的名字
    public List<String> getParameterNames(Method method){
        List<String> ans=new ArrayList<>();
        Parameter[] parameters=method.getParameters();
        for (Parameter parameter:parameters){
            ans.add(parameter.getName());
        }
        return ans;
    }

    @Override
    public void init() throws ServletException {
        //servlet初始化
        String myContextConfigLocation=this.getServletConfig().getInitParameter("MyContextConfigLocation");
        myApplicationContext=new MyApplicationContext(myContextConfigLocation);
        myApplicationContext.init();
        //初始化请求映射
        initHandlerMapping();
    }

    private void initHandlerMapping() {
        if (myApplicationContext.iocMap.isEmpty()) {
            return;
        }else{
            for (Map.Entry<String, Object> entry : myApplicationContext.iocMap.entrySet()) {
                Class<?> aClass = entry.getValue().getClass();
                if (aClass.isAnnotationPresent(MyController.class)){
                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        //判断注解
                        if (method.isAnnotationPresent(MyRequestMapping.class)){
                            MyRequestMapping annotation = method.getDeclaredAnnotation(MyRequestMapping.class);
                            String url = annotation.value();
                            //创建Handler
                            MyHandler handler = new MyHandler(url,entry.getValue(),method);
                            handlerMap.put(url,handler);
                        }
                    }
                }

            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
