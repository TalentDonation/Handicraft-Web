<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<html>
<head>
    <title>member</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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
                        <div class="input-group content-group" id="register">
                            <div class="has-feedback has-feedback-left">
                                <input type="text" placeholder="google url" class="form-control input-xlg"/>
                                <div class="form-control-feedback">
                                    <i class="icon-search4 text-muted text-size-base"></i>
                                </div>
                            </div>
                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-danger btn-xlg register">URL 등록</button>
                                <br>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="panel panel-flat">
                <div class="panel-heading">
                    <h5 class="panel-title">등록된 Google URL<a class="heading-elements-toggle"></a></h5>
                </div>

                <div class="panel-body">
                    <div class="main-search">
                        <div class="input-group content-group" id="clone">

                            <div class="has-feedback has-feedback-left">
                                <p class="form-control rUrl">${url}</p>
                                <div class="form-control-feedback">
                                    <i class="icon-search4 text-muted text-size-base"></i>
                                </div>
                            </div>

                            <div class="input-group-btn">
                                <button type="button" class="btn btn-primary dropdown-toggle search" data-toggle="dropdown">조회<span class="caret"></span></button>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <c:if test="${title.size() >= 1}" var="bool">
                                        <c:forEach begin="0" end="${title.size() - 1}" step="1" var="i">
                                            <c:forEach var="sTitle" items="${title.get(i)}">
                                                <%--<button type="button" class="btn btn-default btn-title search">${sTitle}</button>--%>
                                                <li class="btn sheetTitle"><a href="">${sTitle}</a></li>
                                            </c:forEach>
                                        </c:forEach>
                                    </c:if>
                                </ul>
                            </div>

                        </div>
                    </div>
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



<script>
    $(document).ready(
        $('#panel').hide(),

//        $('#btnAdd').click(function () {
//            var clone = $('#clone').clone().find('.form-control').val('').end();
//            $('.main-search').append(clone);
//        }),

        // TODO: url 바꾸기
        $(document).on("click", ".sheetTitle", function () {
            var title = $(this).text();
            var url = $(this).parent().parent().prev().find('.rUrl').text();
            var spreadSheetId = url.split('/');
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/sheets/' + spreadSheetId[5] + '/' + title,
//                data: {"google": $(this).parent().prev().find('.form-control').val()},
                data: {},
                dataType: 'json',
                success: function (response) {
                    $('#panel').show();
                    var data = response.toString();
                    var arr = data.split(',');
                    var cnt = 1;
                    $("#table-content").empty();
                    $(".table-tbody").empty();

                    // TODO: 표에 나타나야할 개수에 따라 i 바꾸기

                    for (var i = 0; i < arr.length - 2; i += 3) {
                        if(i === 0){
                            $('#table-content').append('<th>No.</th><th>' + arr[i] + '</th>' + '<th>' + arr[i + 1] + '</th>' + '<th>' + arr[i + 2] + '</th>');
                        }
                        else{
                            $(".table-tbody").append('<tr><td>'+ cnt +'</td><td>' + arr[i] + '</td><td>' + arr[i + 1] + '</td><td>' + arr[i + 2] + '</td></tr>');
                            cnt += 1;
                        }
                    }
                },
                timeout: 10000,
                error: function (xhr, status, err) {
                    alert(xhr.message);
                }
            });
            return false;
        })
    );
</script>


</body>
</html>