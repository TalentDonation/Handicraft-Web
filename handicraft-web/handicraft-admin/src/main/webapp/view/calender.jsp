<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">

    <title>Handicraft Admin Calendar</title>

    <link href='./css/calender/fullcalendar.min.css' rel='stylesheet'/>
    <link href='./css/calender/fullcalendar.print.css' rel='stylesheet' media='print'/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900" rel="stylesheet"
          type="text/css">
    <link href="./css/others/css/icons/icomoon/styles.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/core.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/components.css" rel="stylesheet" type="text/css">
    <link href="./css/others/css/colors.css" rel="stylesheet" type="text/css">
    <script src='./js/calender/moment.min.js'></script>
    <script src='./js/calender/jquery.min.js'></script>
    <script src='./js/calender/fullcalendar.min.js'></script>
    <script src="./js/calender/picker_date.js"></script>
    <script src="./js/calender/daterangepicker.js"></script>
    <script src="./js/calender/ko.js"></script>
    <script src="./js/calendarMain.js"></script>


</head>
<body>

<div class="page-container">
    <div class="page-content">
        <div class="content-wrapper">
            <div class="panel panel-flat">

                <div class="panel-body">

                    <div class="row">

                        <div class="col-md-9">
                            <div id='calendar'></div>
                        </div>

                        <div class="col-md-3">

                            <div class="content-group" id="external-events">
                                <h6>일정 정보</h6>
                                <div class="fc-events-container content-group">
                                    <div class="form-group">
                                        <label>일정 제목</label>
                                        <input type="text" placeholder="title" name="title" class="form-control input-xs event-title">
                                    </div>

                                    <div class="form-group">
                                        <label>시작시간 및 종료시간</label>
                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="icon-calendar22"></i></span>
                                            <input type="text" class="form-control daterange-time" name="time">
                                            <div class="hidden event-id" data-id="" data-eid=""></div>
                                        </div>
                                    </div>

                                    <div style="text-align: right">
                                        <input type="submit" class="btn btn-default register" value="등록">
                                        <input type="submit" class="btn btn-primary modify" value="수정">
                                        <input type="submit" class="btn btn-danger delete" value="삭제">
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>


</body>
</html>