$(document).ready(
    $('#panel').hide(),

    // TODO: url 바꾸기
    $(document).on("click", ".sheetTitle", function () {
        var title = $(this).text();
        var url = $(this).parent().parent().prev().find('.rUrl').val();
        var splitUrl = url.split('/');
        var spreadSheetId = splitUrl[5];
        $.ajax({
            type: 'GET',
            url: 'http://127.0.0.1:9090/member/sheets/' + spreadSheetId + '/' + title,
            data: {},
            dataType: 'json',
            success: function (response) {
                $('#panel').show();
                emptyTable();
                showData(response);
            },
            timeout: 10000,
            error: function (xhr, status, err) {
                alert(status.message);
            }
        });
        return false;
    })
);

function emptyTable(){
    $("#table-content").empty();
    $(".table-tbody").empty();
}

function showData(data){
    var cnt = 1;
    for(var i = 0; i < data.length; i++){

        if(i === 0){
            var thData = '<th>No.</th>';
            for(var j = 0; j < data[i].length; j++){
                thData = thData.concat('<th>',data[i][j],'</th>');
            }

            $('#table-content').append(thData);
        }

        else{
            for(var k = 0; k < data[i].length; k++){

                if(k === 0){
                    var tdData = '<tr>';
                    tdData = tdData.concat('<td>', cnt, '</td>');
                }
                tdData = tdData.concat('<td>', data[i][k],'</td>');

                if(k === data[i].length - 1){
                    tdData = tdData.concat('</tr>');
                    $('.table-tbody').append(tdData);
                }
            }
            cnt += 1;
        }
    }
}