package com.cqu.context;

import com.cqu.annotation.MyAutoWired;
import com.cqu.annotation.MyController;
import com.cqu.annotation.MyService;
import com.cqu.xml.XmlParser;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {
    String MyContextConfigLocation;
    List<String> classNameList = new ArrayList<String>();
    public Map<String,Object> iocMap = new ConcurrentHashMap<>();
    public MyApplicationContext(String MyContextConfigLocation){
        this.MyContextConfigLocation = MyContextConfigLocation;
    }

    //初始化
    public void init(){
        //扫描包
        String basePackage = XmlParser.getBasePackage(MyContextConfigLocation.split(":")[1]);
        String[] basePackages = basePackage.split(",");
        if (basePackages.length > 0){
            for (String aPackage : basePackages) {
                scanPackage(aPackage);
            }
        }
        System.out.println("解析到的类名："+classNameList); //测试扫描是否成功
        //实例化
        createInstance();
        System.out.println("对象：："+iocMap);  //测试实例化是否成功
        //自动注入
        autoWired();
    }

    //扫描包得到所有类
    private void scanPackage(String aPackage) {
        URL resource = this.getClass().getClassLoader().getResource(aPackage.replaceAll("\\.", "/"));
        assert resource != null;
        String path = resource.getFile();

        File dir = new File(path);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()){
                //当前是一个文件目录
                scanPackage(aPackage+"."+file.getName());
            }else{
                String className = aPackage +"."+file.getName().replaceAll(".class","");
                classNameList.add(className);
            }
        }
    }

    //实例化容器中的类
    private void createInstance() {
        if (classNameList.isEmpty()) {
            return;
        } else {
            for (String className : classNameList) {
                try {
                    Class<?> aClass = Class.forName(className);
                    if (aClass.isAnnotationPresent(MyController.class)) {
                        //控制层
                        String beanName = aClass.getSimpleName().substring(0, 1).toLowerCase() + aClass.getSimpleName().substring(1);
                        iocMap.put(beanName, aClass.newInstance());
                    } else if (aClass.isAnnotationPresent(MyService.class)) {
                        MyService annotation = aClass.getAnnotation(MyService.class);
                        String beanName = annotation.value();
                        if ("".equals(beanName)) {
                            Class<?>[] interfaces = aClass.getInterfaces();
                            for (Class<?> anInterface : interfaces) {
                                String beanName1 = anInterface.getSimpleName().substring(0, 1).toLowerCase() + anInterface.getSimpleName().substring(1);

                                iocMap.put(beanName1, aClass.newInstance());
                            }
                        } else {
                            iocMap.put(beanName, aClass.newInstance());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }

            }
        }
    }

    //实现自动注入
    private void autoWired() {
        try {
            if (iocMap.isEmpty()){
                return;

            }else {
                for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
                    String beanName = entry.getKey();
                    Object bean = entry.getValue();
                    Field[] fields = bean.getClass().getDeclaredFields();

                    for (Field field : fields) {
                        if (field.isAnnotationPresent(MyAutoWired.class)){
                            MyAutoWired annotation = field.getAnnotation(MyAutoWired.class);
                            String value = annotation.value();
                            if ("".equals(value)){
                                Class<?> type = field.getType();
                                beanName = type.getSimpleName().substring(0,1).toLowerCase()+type.getSimpleName().substring(1);
                            }
                            field.setAccessible(true);
                            field.set(bean,iocMap.get(beanName));
                        }

                    }

                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
