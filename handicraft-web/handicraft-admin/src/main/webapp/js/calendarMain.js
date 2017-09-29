// TODO: event들 다 db 저장, 이벤트 추가, 삭제, 누르면 상세정보, 들어왔을때 controller에서
// TODO: js로 db에 있는것들 뿌려준다, 참여하는 사람정보, getJSON 으로 받아온다

$(document).ready(function() {

    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listMonth'
        },
        // header: {
        //     center: 'month,agendaFourDay' // buttons for switching between views
        // },
        // views: {
        //     agendaFourDay: {
        //         type: 'agenda',
        //         duration: { days: 4 },
        //         buttonText: '4 day'
        //     }
        // },
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        selectHelper: true,
        // timeFormat: 'h:mm',
        locale: 'ko',
        dayClick: function (date) {
            $('.event-title').val("");
            var time = (date._d.getMonth() + 1) + "/" + date._d.getDate() + "/" + date._d.getFullYear();
            // var time = date._d.getFullYear() + "-" + (date._d.getMonth() + 1) + "-" + date._d.getDate();
            $('.daterange-time').val(time);
        },

        eventClick: function (event) {
            $('.event-title').val(event.title);
            var start = event.start._i;
            var end = event.end._i;
            $('.daterange-time').val(convertTime(start, end));
            $('.event-id').attr('data-id', event.id);
        },

        editable: false,     // 이거 false로 놓으면 drag 불가
        eventLimit: true, // allow "more" link when too many events
        eventSources: [{
            url: '/calender/event',
            type: 'GET',
            success: function (response) {
                alert("success!");
            },
            error: function () {
                alert('there was an error while fetching events!');
            }}]
    });

    $(document).on('click', '.register', function () {
        var title = $('.event-title').val();
        var time = $('.daterange-time').val();
        manageEvent('newevent', title, time);
    });

    $(document).on('click', '.modify', function () {
        var title = $('.event-title').val();
        var time = $('.daterange-time').val();
        manageEvent('modifyevent', title, time);
    });

    $(document).on('click', '.delete', function () {
        var id = $('.event-id').attr('data-id');
        $.ajax({
            type: 'GET',
            url: 'http://localhost/calender/deleteevent',
            data: {'id': id},
            dataType: 'json',
            success: function (response) {
                $('#calendar').fullCalendar('removeEvents', id);
                alert("삭제가 완료되었습니다");
            },
            timeout: 10000,
            error: function (xhr, status, err) {
                alert(status.message);
            }

        });
    });

});

function manageEvent(url, title, time) {
    var start = time.split(" - ")[0].trim();
    var end = time.split(" - ")[1].trim();

    $.ajax({
        type: 'GET',
        url: 'http://localhost/calender/' + url,
        data: {'title': title, 'start': start, 'end': end},
        dataType: 'json',
        success: function (response) {
            if (url === 'newevent')
            {
                if (title) {
                    var eventData = {
                        title: response.title,
                        start: response.start,
                        end: response.end,
                        id: response.eid
                    };
                    $('#calendar').fullCalendar('renderEvent', eventData, true);
                }
                $('#calendar').fullCalendar('unselect');
            }

            else {
                var id = $('.event-id').attr('data-id');
                var selectedEvent = $('#calendar').fullCalendar('clientEvents', id);
                var times = time.split('-');
                var start = times[0].trim();
                var end = times[1].trim();
                selectedEvent.start = start;
                selectedEvent.end = end;
                selectedEvent.title = title;
                $('#calendar').fullCalendar('removeEvents',id);
                $('#calendar').fullCalendar('renderEvent', selectedEvent, true);
            }
        },
        timeout: 10000,
        error: function (xhr, status, err) {
            alert("정확한 시간을 입력해 주세요");
        }

    });
}

function convertTime(start, end) {
    return start + " - " + end;
}

function frontToBack(time) {       // TODO: MM/DD/YYYY h:mm a -> yyyy-MM-dd hh:mm:ss
    var times = time.split(" ");
}

function backToFront(time) {

}

// start :Mon Sep 25 2017 00:00:00 GMT+0000 -> 09/25/2017 12:00 am
// end: Mon Sep 25 2017 23:59:00 GMT+0000 -> 09/25/2017 11:59 pm
// 필요할때 마다 해당하는 데이터를 가져온다 -> 2013-12-01 이런 형식으로 된거로 구분