<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Handicraft Admin Model</title>

    <script src="./js/calender/jquery.min.js"></script>
    <script src="./js/fileUpload.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet" type="text/css">
    <link href="./css/others/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/core.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/components.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/colors.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/FileUploadBtn.css" rel="stylesheet" type="text/css">

</head>

<body>
<div class="page-container">
    <div class="page-content">
        <div class="content-wrapper">
            <div class="panel panel-flat">

                <div class="panel-heading">
                    <h5 class="panel-title">업로드할 파일을 선택하세요<a class="heading-elements-toggle"></a></h5>
                    <%--<a href="/calender/naverlogin" >naver</a>--%>
                </div>

                <div class="panel-body">
                    <div class="main-search">
                        <form action="/model/upload" method="post" enctype="multipart/form-data" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <%--<input type="file" name="submitFile"/>--%>
                            <%--<input type="submit" value="제출"/>--%>
                            <div class="filebox">
                                <input value="파일선택" disabled="disabled" class="upload-name" />
                                <label for="ex_filename" class="btn btn-primary">찾아보기</label>
                                <%--<input type="file" id="ex_filename" class="upload-hidden">--%>
                                <input type="file" id="ex_filename" class="btn btn-primary upload-hidden" name="submitFile" />
                                <button type="submit" class="btn btn-primary" id="upload">업로드</button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>