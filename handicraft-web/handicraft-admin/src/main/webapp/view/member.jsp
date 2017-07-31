<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<html>
<head>
    <title>member</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
    <form action="/upload" accept-charset="utf-8" method="get" id="urlUpload">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="text" name="name" placeholder="google url"/>
        <input type="submit" value="upload" name="btnSubmit"/><br>
    </form>

    <form accept-charset="utf-8" method="get">
        <input type="button" value="+" id="btnAdd" />
    </form>

    <script>
        $(document).ready(
            $('#btnAdd').click(function(){
                $('#urlUpload').append('<input type="text" name="name" placeholder="google url"/>');
                $('#urlUpload').append('<input type="submit" value="upload"/><br>');
            })
        )
    </script>
</body>
</html>