
$(document).ready(function () {

    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listMonth'
        },
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        selectHelper: true,
        locale: 'ko',
        dayClick: function (date) {
            $('.event-title').val("");
            var time = date._d.getFullYear() + "-" + (date._d.getMonth() + 1) + "-" + date._d.getDate();
            $('.daterange-time').val(time);
        },

        eventClick: function (event) {
            $('.event-title').val(event.title);
            var start = event.start._i;
            var end = event.end._i;
            $('.daterange-time').val(convertTime(start, end));
            $('.event-id').attr('data-id', event._id);
            $('.event-id').attr('data-eid', event.eid);
        },

        editable: false,     // 이거 false로 놓으면 drag 불가
        eventLimit: true, // allow "more" link when too many events
        eventSources: [{
            url: '/calender/event',
            type: 'GET',
            error: function () {
                alert('there was an error while fetching events!');
            }
        }]
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
        var eid = $('.event-id').attr('data-eid');
        var id = $('.event-id').attr('data-id');
        $.ajax({
            type: 'GET',
            url: 'http://www.half-handicraft.com/calender/deleteevent',
            data: {'id': eid},
            dataType: 'json',
            success: function () {
                $('#calendar').fullCalendar('removeEvents', id);
                alert('삭제가 완료되었습니다');
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
    var eid = $('.event-id').attr('data-eid');
    $.ajax({
        type: 'GET',
        url: 'http://www.half-handicraft.com/calender/' + url,
        data: {'title': title, 'start': frontToBack(start), 'end': frontToBack(end), 'eid': eid},
        dataType: 'json',
        success: function (response) {
            if (url === 'newevent') {
                if (title) {
                    var eventData = {
                        title: response.title,
                        start: response.start,
                        end: response.end,
                        eid: response.eid
                    };
                    $('#calendar').fullCalendar('renderEvent', eventData, true);
                }
                $('#calendar').fullCalendar('unselect');
            }

            else {
                var id = $('.event-id').attr('data-id');
                var selectedEvent = $('#calendar').fullCalendar('clientEvents', id);

                selectedEvent.start = response.start;
                selectedEvent.end = response.end;
                selectedEvent.title = response.title;
                selectedEvent.eid = response.eid;
                selectedEvent._id = id;
                $('#calendar').fullCalendar('removeEvents', id);
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

function frontToBack(time) {    // MM/DD/YYYY h:mm a -> yyyy-MM-dd hh:mm:ss

    var times = time.split(' ');
    var AmPm = times[2];

    if(AmPm === '오후') {
        var h = times[1].split(':')[0];
        h = parseInt(h);
        h += 12;
        times[1] = h + ':' + times[1].split(':')[1];
    }

    var front = times[0].split('/');
    var month = front[0];
    var day = front[1];
    var year = front[2];

    var result = year.concat('-', month, '-', day, ' ', times[1], ':00');
    return result;

}

// start :Mon Sep 25 2017 00:00:00 GMT+0000 -> 09/25/2017 12:00 am
// end: Mon Sep 25 2017 23:59:00 GMT+0000 -> 09/25/2017 11:59 pm
// 필요할때 마다 해당하는 데이터를 가져온다 -> 2013-12-01 이런 형식으로 된거로 구분