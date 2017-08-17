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
            url: 'http://localhost:8080/sheets/' + spreadSheetId + '/' + title,
            data: {},
            dataType: 'json',
            success: function (response) {
                $('#panel').show();
                var data = response.toString();
                var dataArray = data.split(',');

                emptyTable();

                showData(dataArray);
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

function showData(arr){
    var cnt = 1;
    // TODO: 표에 나타나야할 개수에 따라 i 바꾸기
    for (var i = 0; i < arr.length - 2; i += 3) {
        if(i === 0){
            $('#table-content').append('<th>No.</th><th>' + arr[i] + '</th><th>' + arr[i + 1] + '</th><th>' + arr[i + 2] + '</th>');
        }
        else{
            $(".table-tbody").append('<tr><td>'+ cnt +'</td><td>' + arr[i] + '</td><td>' + arr[i + 1] + '</td><td>' + arr[i + 2] + '</td></tr>');
            cnt += 1;
        }
    }
}