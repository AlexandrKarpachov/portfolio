<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java"  pageEncoding="UTF-8" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-info rounded">
	<ul class="navbar-nav mr-auto">
	
		<li class="nav-item active">
			<a class="nav-link" href="${pageContext.request.contextPath}/main">Главная </a>
		</li>
		<li class="nav-item active"><a class="nav-link"
			href="${pageContext.request.contextPath}/user/${pageContext.request.userPrincipal.name}">Личный кабинет</a></li>
		<li class="nav-item active"><a class="nav-link"
			href="${pageContext.request.contextPath}/addCarForm">Добавить автомобиль</a></li>
	</ul>
	<c:set var="id">
   		<%=(String) session.getAttribute("id")%>
	</c:set>
	<c:url var="logoutAction" value="/logout"></c:url>

	<c:choose>
		<c:when test="${id != ''}">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item active" id="exit">
					<form class="navbar-form"  action="${logoutAction}" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="submit" class="btn btn-info" id="logIn" value="Выход"/>
					</form>
				</li>
			</ul>
		</c:when> 
		<c:otherwise>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item active" id="entry">
					<a class="nav-link" href="${pageContext.request.contextPath}/login">Вход</a>
				</li>
			</ul>
		</c:otherwise>   
	</c:choose>
</nav>