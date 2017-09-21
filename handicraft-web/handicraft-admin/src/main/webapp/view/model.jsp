<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title>Handicraft Admin Model</title>

    <script src="./js/calender/jquery.min.js"></script>
    <script src="./js/fileUpload.js"></script>
    <script src="./js/fileDisplay.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet"
          type="text/css">
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
                        <form action="/model/upload" method="post" enctype="multipart/form-data"
                              class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="filebox">
                                <input value="파일선택" disabled="disabled" class="upload-name"/>
                                <label for="ex_filename" class="btn btn-primary">찾아보기</label>
                                <input type="file" id="ex_filename" class="btn btn-primary upload-hidden"
                                       name="submitFile"/>
                                <button type="submit" class="btn btn-primary" id="upload">업로드</button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>

            <div class="panel panel-flat">
                <div class="panel-heading">
                    <h5 class="panel-title">저장된 파일</h5>
                    <div class="heading-elements">
                        <ul class="icons-list">
                            <li>
                                <button type="button" class="btn btn-default" id="add-folder" data-toggle="modal" data-target="#modal_form_horizontal">폴더추가</button>
                            </li>
                        </ul>
                    </div>
                </div>

                <ul class="media-list media-list-linked">
                    <li>
                        <span class="media-header">
                            <span class="path" style="cursor: pointer">
                                    반쪽이공방
                                <div class="hidden"></div>
                            </span>
                            <c:forEach var="folder" items="${awsPath}">
                                <span class="path" style="cursor: pointer">
                                    <i class="icon-arrow-right5"></i>${folder.getName()}
                                    <div class="hidden">${folder.getPath()}</div>
                                </span>
                            </c:forEach>
                        </span>
                    </li>
                    <c:forEach var="folder" items="${awsFolder}">
                        <li class="media">
                            <div class="media-link" style="cursor: pointer">
                                <div class="media-left"><i class="icon-folder"></i></div>
                                <div class="media-body aws-folder">
                                    <div class="media-heading text-semibold">${folder.getName()}</div>
                                    <span class="text-muted">최근 변경일: ${folder.getLastModified()}</span>
                                    <div class="folder-path hidden">${folder.getPath()}</div>
                                </div>
                                <div class="media-right medi-middle text-nowrap">
                                    <i class="glyphicon glyphicon-trash" style="cursor: pointer"></i>
                                </div>
                            </div>
                        </li>
                    </c:forEach>

                    <c:forEach var="file" items="${awsFile}">
                        <li class="media">
                            <div class="media-link">
                                <div class="media-left"><i class="icon-file-text"></i></div>
                                <a href="${file.getUrl()}" class="media-body">
                                    <div class="media-heading text-semibold">${file.getName()}</div>
                                    <span class="text-muted">최근 변경일: ${file.getLastModified()}</span>
                                    <div class="file-path hidden">${file.getPath()}</div>
                                </a>
                                <div class="media-right medi-middle text-nowrap">
                                    <i class="glyphicon glyphicon-trash" style="cursor: pointer"></i>
                                </div>
                            </div>
                        </li>
                    </c:forEach>

                </ul>
            </div>

        </div>
    </div>
</div>

<div id="modal_form_horizontal" class="modal fade">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h5 class="modal-title">새 폴더 추가</h5>
            </div>

            <form action="/model/newfolder" class="form-horizontal" method="post">

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="modal-body">
                    <div class="form-group">
                        <input type="text" placeholder="폴더 이름" class="form-control" name="folderName">
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-link" data-dismiss="modal">취소</button>
                    <button type="submit" class="btn btn-primary">폴더 생성</button>
                </div>

            </form>
        </div>
    </div>
</div>

</body>
</html>