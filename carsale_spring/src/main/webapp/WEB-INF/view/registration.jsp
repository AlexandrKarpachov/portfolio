<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<title>Registration</title>
</head>
<script>
function deleteInvalid(id) {
	$(id).removeClass('is-invalid');
}
</script>
<body>
	<div class="container">
		<form:form  modelAttribute="userForm" action="addUser">
			<h2>Регистрация :</h2>
			<spring:bind path="login">
				<div class="form-group ">
					<label for="login">Логин:</label> 
					<form:input type="text" path="login" placeholder="Login" onclick="deleteInvalid(login)"
								id="login" class="form-control ${status.error ? 'is-invalid' : ''}"></form:input>
					<form:errors path="login" class="invalid-feedback"></form:errors>
				</div>
			</spring:bind>
			
			<spring:bind path="name">
				<div class="form-group">
					<label for="name" >Имя:</label> 
					<form:input type="text" path="name" placeholder="Имя" 
								id="firstName" onclick="deleteInvalid(firstName)"
								class="form-control ${status.error ? 'is-invalid' : ''}"></form:input>
					<form:errors path="name" class="invalid-feedback"></form:errors> 
				</div>
			</spring:bind>
			
			<spring:bind path="surname">
				<div class="form-group">
					<label for="surname">Фамилия:</label>
					<form:input type="text" placeholder="Фамилия" 
								onclick="deleteInvalid(surname)" id="surname" path="surname"
								class="form-control ${status.error ? 'is-invalid' : ''}"></form:input>
					<form:errors path="surname" class="invalid-feedback"></form:errors> 
				</div>
			</spring:bind>
			
			<spring:bind path="phone">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label for="phone">Телефон:</label>
					<form:input type="number" placeholder="Телефон" onclick="deleteInvalid(phone)"
								id="phone" class="form-control ${status.error ? 'is-invalid' : ''}"
								path="phone"></form:input>
					<form:errors path="phone" class="invalid-feedback"></form:errors> 
				</div>
			</spring:bind>
			
			<spring:bind path="password">
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label for="password">Пароль:</label>
					<form:input type="password" placeholder="Пароль" 
								onclick="deleteInvalid(password)" path="password" id="password" 
								class="form-control ${status.error ? 'is-invalid' : ''}"></form:input>
					<form:errors path="password" class="invalid-feedback"></form:errors> 
				</div>
			</spring:bind>
			
			
			<spring:bind path="confirmPassword">
	            <div class="form-group ${status.error ? 'has-error' : ''}">
	                <form:input type="password" path="confirmPassword" id="confirmPassword"
                            placeholder="Подтвердите пароль" onclick="deleteInvalid(confirmPassword)" 
							class="form-control ${status.error ? 'is-invalid' : ''}"></form:input>
					<form:errors path="confirmPassword" class="invalid-feedback"></form:errors>
	            </div>
        	</spring:bind>

        	<button class="btn btn-lg btn-primary btn-block" type="submit">Регистрация</button>
		</form:form>
	</div>
</body>
</html>