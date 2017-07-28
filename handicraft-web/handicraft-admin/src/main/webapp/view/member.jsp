<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<html>
<head>
    <title>member</title>
</head>
<body>
    <form action="/upload" accept-charset="utf-8" method="get">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="text" name="name" placeholder="이름"/>
        <input type="submit" value="upload" />
    </form>
</body>
</html>