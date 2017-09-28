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
        dayClick: function (date) {
            $('.event-title').val("");
            var time = "0" + (date._d.getMonth() + 1) + "/" + date._d.getDate() + "/" + date._d.getFullYear();
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
        events: [       // TODO: url로 바꾼다 그 url에 mapping된 거에서 json으로 리턴
            {
                title: 'All Day Event',
                start: '09/25/2017 12:00 am',
                end: '09/25/2017 11:59 pm'
            },
            {
                title: 'Long Event',
                start: '2017-09-07',
                end: '2017-09-10',
                id: 1
            },
            {
                id: 999,
                title: 'Repeating Event',
                start: '2017-09-09T16:00:00'
            },
            {
                id: 266,
                title: 'Repeating Event',
                start: '2017-09-16T16:00:00'
            },
            {
                id: 2,
                title: 'Conference',
                start: '2017-09-11',
                end: '2017-09-13'
            },
            {
                title: 'Meeting',
                start: '2017-09-12T10:30:00',
                end: '2017-09-12T12:30:00'
            },
            {
                title: 'Lunch',
                start: '2017-09-12T12:00:00'
            },
            {
                title: 'Meeting',
                start: '2017-09-12T14:30:00'
            },
            {
                title: 'Happy Hour',
                start: '2017-09-12T17:30:00'
            },
            {
                title: 'Dinner',
                start: '2017-09-12T20:00:00'
            },
            {
                title: 'Birthday Party',
                start: '09/24/2017 12:00 am',
                end: '09/24/2017 02:00 pm',
                id: 3
            },
            {
                title: 'Click for Google',
                url: 'http://google.com/',
                start: '2017-09-28'
            }
        ]
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
    $.ajax({
        type: 'GET',
        url: 'http://localhost/calender/' + url,
        data: {'title': title, 'time': time},
        dataType: 'json',
        success: function (response) {
            if (url === 'newevent')
            {
                if (title) {
                    var eventData = {
                        title: response[0],
                        start: response[1],
                        end: response[2]
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

// start :Mon Sep 25 2017 00:00:00 GMT+0000 -> 09/25/2017 12:00 am
// end: Mon Sep 25 2017 23:59:00 GMT+0000 -> 09/25/2017 11:59 pm
// 필요할때 마다 해당하는 데이터를 가져온다 -> 2013-12-01 이런 형식으로 된거로 구분