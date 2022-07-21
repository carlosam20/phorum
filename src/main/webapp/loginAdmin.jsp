<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

Indentificate admin!
<form method="post" action="admin/">
pass: <input type="password" value="${sessionScope.campo_pass}" name="pass" id="pass" /> <br>
<input type="checkbox" checked="checked" name="recordar_pass"/> recordar pass<br>
<input type="submit"/>
</form>

</body>
</html>