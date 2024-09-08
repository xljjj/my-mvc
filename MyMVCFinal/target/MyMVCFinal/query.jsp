<%--
  Created by IntelliJ IDEA.
  User: Xu Lingjue
  Date: 2022/11/29
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>查询用户</title>
    <!-- 新 Bootstrap5 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/5.1.1/css/bootstrap.min.css">

    <!--  popper.min.js 用于弹窗、提示、下拉菜单 -->
    <script src="https://cdn.staticfile.org/popper.js/2.9.3/umd/popper.min.js"></script>

    <!-- 最新的 Bootstrap5 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/5.1.1/js/bootstrap.min.js"></script>
</head>
<body>
    <h1>${requestScope.message}</h1>
    <div style="height: 80%">
        <div style="width: 49%; display: inline-block">
            <img src="https://img0.baidu.com/it/u=2547735653,2921682335&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=530" style="height: 100%; width: 100%">
        </div>
        <div style="width: 49%; display: inline-block">
            <form role="form" action="query" method="post">
                <div class="form-group">
                    <label for="name">查询用户名是否存在</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入用户名">
                </div>
                <button type="submit" class="btn btn-primary" style="width: 80%; margin-left: 10%">查询</button>
            </form>
        </div>
    </div>
    <div style="width: 80%;margin: 0 auto">
        <a href="index.jsp">
            <button type="button" class="btn btn-primary" style="width: 49%">回到主页</button>
        </a>
        <a href="upload.jsp">
            <button type="button" class="btn btn-primary" style="width: 49%">上传文件</button>
        </a>
    </div>
</body>
</html>
