<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<html>
<head>
    <title>member</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
    <div id="addInput">
        <input type="text" placeholder="google url" class="url"/>
        <input type="button" value="조회" name="btnSubmit" id="search" class="btnSearch" /><br>
    </div>

    <input type="button" value="+" id="btnAdd" />

    <div id="table"></div>

    <script>
        var count;
        $(document).ready(
            $('#btnAdd').click(function(){
                count++;
                var url = "url" + count;
                $('#addInput').append('<input type="text" placeholder="google url" class="url"/>' + '<input type="button" value="조회" name="btnSubmit" class="btnSearch"/><br>');
            }),

            // TODO: url 바꾸기
            $(document).on("click",".btnSearch", function(){
                $.ajax({
                    type: 'GET',
                    url: 'http://localhost:8080/sheets',
                    data: {"google" : $(this).prev().val()},
                    dataType: 'json',
                    success: function(response){
                        var data = response.toString();
                        var arr = data.split(',');
                        // TODO: 표에 나타나야할 개수에 따라 i 바꾸기
                        for(var i = 0; i < arr.length - 2; i += 3){
                            $("#table").append('<tr><td>'+ arr[i] +'</td><td>'+ arr[i + 1] +'</td><td>'+ arr[i + 2] +'</td></tr>');
                        }
                    },
                    timeout: 10000,
                    error: function(xhr, status, err){
                        alert("error detected!");
                    }
                });
            })
        );
    </script>


</body>
</html>