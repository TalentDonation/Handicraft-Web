<%--
  Created by IntelliJ IDEA.
  User: 고승빈
  Date: 2017-06-28
  Time: 오후 8:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <link href="./css/login/login.css" rel="stylesheet"/>

    <title>Admin Login</title>
</head>
<body>
    <div class = "container">
        <div class="wrapper">
            <form action="/login" accept-charset="utf-8" method="post" class="form-signin">

                <h3 class="form-signin-heading">Welcome Back! Please Sign In</h3>
                <hr class="colorgraph"><br>

                <c:choose >
                    <c:when test ="${param.error != null}">
                        <p>
                            Invalid ID and PASSWORD
                        </p>
                    </c:when>
                    <c:when test ="${param.logout != null}">
                        <p>
                            You have been logged out.
                        </p>
                    </c:when>

                </c:choose>

                <input type="text" class="form-control" name="username" placeholder="Username" required="" autofocus="" />
                <input type="password" class="form-control" name="password" placeholder="Password" required=""/>
               <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                <button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Login" type="Submit">Login</button>
            </form>
        </div>
    </div>

</body>


</html>
