<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Handicraft Admin Model</title>
</head>
<body>
<h1>Login</h1>
<div class="container" >

    <a href="/calender/naverlogin" >naver</a>

    <form action="/model/upload" method="post" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="file" name="submitFile"/>
        <input type="submit" value="제출"/>
    </form>


</div>
</body>
</html>