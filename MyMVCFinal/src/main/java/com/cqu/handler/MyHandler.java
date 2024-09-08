package com.cqu.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

//记录对每个url的控制器和方法
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyHandler {
    private String url;
    private Object controller;
    private Method method;
}
