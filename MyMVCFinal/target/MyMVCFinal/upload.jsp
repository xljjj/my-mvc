<%--
  Created by IntelliJ IDEA.
  User: Xu Lingjue
  Date: 2022/11/29
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>上传文件</title>
    <!-- 新 Bootstrap5 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/5.1.1/css/bootstrap.min.css">

    <!--  popper.min.js 用于弹窗、提示、下拉菜单 -->
    <script src="https://cdn.staticfile.org/popper.js/2.9.3/umd/popper.min.js"></script>

    <!-- 最新的 Bootstrap5 核心 JavaScript 文件 -->
    <script src="https://cdn.staticfile.org/twitter-bootstrap/5.1.1/js/bootstrap.min.js"></script>
</head>
<body>
    <h1>文件保存路径：E:\\FileStore</h1>
    <h1>${requestScope.message}</h1>
    <div style="height: 80%">
        <div style="width: 49%; display: inline-block" >
            <img src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbpic.51yuansu.com%2Fpic2%2Fcover%2F00%2F31%2F67%2F5810c41806eca_610.jpg&refer=http%3A%2F%2Fbpic.51yuansu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1672302311&t=39e988884ef2abf33cdb3f36417eee8f" style="height: 100%; width: 100%">
        </div>
        <div style="width: 49%; display: inline-block">
            <form role="form" action="upload" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="inputfile">选择文件</label>
                    <input type="file" id="inputfile" name="file">
                </div>
                <button type="submit" class="btn btn-primary" style="width: 80%; margin-left: 10%">上传文件</button>
            </form>
        </div>
    </div>
    <div style="width: 80%;margin: 0 auto">
        <a href="index.jsp">
            <button type="button" class="btn btn-primary" style="width: 49%">回到主页</button>
        </a>
        <a href="query.jsp">
            <button type="button" class="btn btn-primary" style="width: 49%">查询用户</button>
        </a>
    </div>
</body>
</html>
