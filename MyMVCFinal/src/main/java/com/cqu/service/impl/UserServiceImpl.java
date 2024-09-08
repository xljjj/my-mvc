package com.cqu.service.impl;

import com.cqu.annotation.MyService;
import com.cqu.pojo.User;
import com.cqu.service.UserService;

import java.util.ArrayList;
import java.util.List;

@MyService
public class UserServiceImpl implements UserService {
    //伪造数据库用于测试
    private static List<User> list=new ArrayList<User>();
    static {
        list.add(new User("root","123456"));
        list.add(new User("zhangsan","666"));
        list.add(new User("hacker","???"));
        list.add(new User("lisi","hahaha"));
        list.add(new User("sleeper","zzz"));
    }
    @Override
    public Boolean checkUser(String username) {
        for (User user:list){
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
}
