<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Administrator Login</title>


    <link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
    <link href="./css/others/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/core.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/components.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/colors.css" rel="stylesheet" type="text/css">


</head>

<body class="navbar-bottom login-container">

<%--nav bar--%>
<div class="navbar navbar-inverse">
    <div class="navbar-collapse collapse" id="navbar-mobile">
        <ul class="nav navbar-nav navbar-left">
            <li>
                <a href="#" style="font-size: 16px">
                    반쪽이공방
                </a>
            </li>
        </ul>
    </div>
</div>

<!-- Page container -->
<div class="page-container">

    <!-- Page content -->
    <div class="page-content">

        <!-- Main content -->
        <div class="content-wrapper">

            <!-- Content area -->
            <div class="content">

                <!-- Advanced login -->
                <form action="/login" accept-charset="utf-8" method="post" class="form-signin">
                    <div class="login-form">
                        <div class="text-center">
                            <div class="icon-object border-indigo-400 text-indigo-400"><i class="icon-people"></i></div>
                            <h5 class="content-group-lg">Login to your account <small class="display-block">Enter your credentials</small></h5>
                        </div>

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

                        <div class="form-group has-feedback has-feedback-left">
                            <input type="text" class="form-control input-lg" placeholder="Username" name="username">
                            <div class="form-control-feedback">
                                <%--<i class="icon-user text-muted"></i>--%>
                            </div>
                        </div>

                        <div class="form-group has-feedback has-feedback-left">
                            <input type="password" class="form-control input-lg" placeholder="Password" name="password">
                            <div class="form-control-feedback">
                                <%--<i class="icon-lock2 text-muted"></i>--%>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />


                        <div class="form-group">
                            <button type="submit" class="btn bg-pink btn-block btn-lg">Login <i class="icon-arrow-right14 position-right"></i></button>
                        </div>
                    <%--</div>--%>
                </form>
                <!-- /advanced login -->

            </div>
            <!-- /content area -->

        </div>
        <!-- /main content -->

    </div>
    <!-- /page content -->

</div>
<!-- /page container -->

</body>

</html>
