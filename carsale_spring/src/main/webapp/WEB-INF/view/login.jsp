<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="ru">
<head>
	<meta charset="utf-8">
	<title>Log in with your account</title>

	<link  rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<body>

<div class="container">
	<form method="POST" action="${contextPath}/login" class="form-signin">
		<h2 class="form-heading">Вход</h2>

		<div class="form-group ${error != null ? 'has-error' : ''}">
			<span>${message}</span>
			<input name="login" type="text" class="form-control" placeholder="login"
				   autofocus="true"/>
			<input name="password" type="password" class="form-control" placeholder="Password"/>
			<span>${error}</span>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
			<h4 class="text-center"><a href="${contextPath}/registration">Регистрация</a></h4>
		</div>
	</form>
</div>
</body>
</html>