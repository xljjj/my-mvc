package com.cqu.controller;

import com.cqu.annotation.MyAutoWired;
import com.cqu.annotation.MyController;
import com.cqu.annotation.MyRequestMapping;
import com.cqu.annotation.MyRequestParam;
import com.cqu.pojo.User;
import com.cqu.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MyController
public class UserController {
    @MyAutoWired
    private UserService userservice;

    @MyRequestMapping("/query")
    public String checkUser(HttpServletRequest request, HttpServletResponse response, @MyRequestParam("name") String name){
        Boolean flag = userservice.checkUser(name);

        response.setContentType("text/html;charset=utf8");
        if (flag){
            request.setAttribute("message","该用户名已存在");
        }
        else{
            request.setAttribute("message","该用户名不存在");
        }
        //转发有两种方式：转发forward和重定向redirect，默认使用转发方式
        return "query.jsp";
    }
}
