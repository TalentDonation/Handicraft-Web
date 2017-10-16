$(document).ready(
    $(document).on("click", ".aws-folder", function () {
        var path = $(this).find(".folder-path").text();

        ajax(path, "forward");

    }),

    $(document).on("click", ".path", function () {
        var path = $(this).find('.hidden').text();

        ajax(path, "backward");

    }),

    $(document).on("click", ".glyphicon-trash", function () {
        var path = $(this).parent().parent().find('.hidden').text();
        var boolean = confirm("해당내용을 삭제하시겠습니까?");

        if(boolean === true){
            ajax(path, "delete");
        }
        else {
            alert("취소되었습니다");
        }

    })
);

function ajax (path, url) {
    $.ajax({
        type: "GET",
        url: "http://www.half-handicraft.com/" + url,
        data: {"path": path},    // clicked를 보내서 클릭된 것까지 split한다
        dataType: "json",
        success: function (response) {
            console.log("success!");
            console.log(response.toString());
            location.reload();
        },
        timeout: 10000,
        error: function (xhr, status, err) {
            alert(status.message);
        }

    });
}

