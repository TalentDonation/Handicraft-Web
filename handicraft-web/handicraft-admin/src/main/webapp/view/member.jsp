<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<html>
<head>
    <title>member</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="./js/memberInfo.js"></script>

    <link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
    <link href="./css/others/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/core.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/components.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/colors.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="page-container">
    <div class="page-content">
        <div class="content-wrapper">

            <div class="panel panel-flat">

                <div class="panel-heading">
                    <h5 class="panel-title">등록할 Google URL을 입력하세요<a class="heading-elements-toggle"></a></h5>
                </div>

                <div class="panel-body">
                    <div class="main-search">
                            <form method="post" action="/member" accept-charset="utf-8" class="input-group content-group">
                                <div class="has-feedback has-feedback-left">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <input type="text" placeholder="google url" name="url" class="form-control input-xlg"/>
                                    <div class="form-control-feedback">
                                        <i class="icon-search4 text-muted text-size-base"></i>
                                    </div>
                                </div>
                                <div class="input-group-btn">
                                    <button type="submit" class="btn btn-danger btn-xlg register">URL 등록</button>
                                    <br>
                                </div>
                            </form>
                    </div>
                </div>

            </div>

            <div class="panel panel-flat">
                <div class="panel-heading">
                    <h5 class="panel-title">등록된 Google URL<a class="heading-elements-toggle"></a></h5>
                </div>

                <div class="panel-body">
                    <c:forEach begin="0" end="${url.size() - 1}" step="1" var="i">
                        <div class="main-search">
                            <div class="input-group content-group">
                                <div class="has-feedback has-feedback-left">
                                    <input type="text" class="form-control rUrl" readonly="readonly" value="${url.get(i)}"/>
                                    <div class="form-control-feedback">
                                        <i class="icon-search4 text-muted text-size-base"></i>
                                    </div>
                                </div>
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-primary dropdown-toggle search" data-toggle="dropdown">조회<span class="caret"></span></button>
                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <c:if test="${title.size() >= 1}" var="bool">
                                            <c:forEach begin="0" end="${title.get(i).size() - 1}" step="1" var="j">
                                                <c:forEach var="sTitle" items="${title.get(i).get(j)}">
                                                    <li class="btn sheetTitle"><a href="">${sTitle}</a></li>
                                                </c:forEach>
                                            </c:forEach>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>


            <div class="panel panel-flat" id="panel">
                <%--표 제목--%>
                <div class="panel-heading">
                    <h5 class="panel-title" id="table-title">회원정보</h5>
                </div>

                <%--회원정보 표--%>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr class="bg-blue" id="table-content">
                        </tr>
                        </thead>
                        <tbody class="table-tbody">
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</div>


</body>
</html>