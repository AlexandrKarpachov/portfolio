<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" %>

<c:url var="contextPath" value="${pageContext.servletContext.contextPath}"></c:url>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
	function fillYearsSelect() {
		$.ajax("./ajax/years", {
			method : "get",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					$('#year').append('<option>' + data[i] + '</option>\n');
				}
			}
		});
	}
	
	function addUploadField(index) {
		$('#imgFiles').append(
				'<input type="file" class="form-control-file border"'
				+ 'name="imgFiles" onchange="addUploadField()">');
	}
	
	function fillMarkSelect() {
		$.ajax("./ajax/brands", {
			method : "get",
			success : function(data) {
				var result = "";
				result += "<option>none</option>\n"
				for (var i = 0; i < data.length; i++) {
					result += '<option id="' + data[i].id + '">' + data[i].name
							+ "</option>"
				}
				var select = document.getElementById("brands");
				select.innerHTML = result;
			}
		});
	}
	function fillModelSelect() {
		var result = "<option>none</option>";
		var markId = $("#brands :selected").attr("id");
		var select = document.getElementById("models");
		select.innerHTML = result;
		if (markId != "none") {
			$.ajax("./ajax/models", {
				method : "get",
				data : {
					"brandId" : markId
				},
				success : function(models) {
					for (var i = 0; i < models.length; i++) {
						result += '<option>' + models[i].name + "</option>\n"
					}
					select.innerHTML = result;
				}
			});
		}
	}
	function fillEngineSelect() {
		var result = "<option>none</option>";
		var select = document.getElementById("fuel");
		select.innerHTML = result;
		$.ajax("./ajax/fuel", {
			method : "get",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					result += '<option value="' + data[i].name + '">'
							+ data[i].value + "</option>\n"
				}
				select.innerHTML = result;
			}
		});
	}
	
	function fillShiftGearSelect() {
		var result = "<option>none</option>";
		var select = document.getElementById("shiftGear");
		select.innerHTML = result;
		$.ajax("./ajax/shiftGears", {
			method : "get",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					result += '<option  value="' + data[i].name + '">'
							+ data[i].value + "</option>\n"
				}
				select.innerHTML = result;
			}
		});
	}

	function getUserId() {
		$.ajax("./ajax", {
			method : "get",
			data : {
				"query" : "userId"
			},
			success : function(data) {
				$('#userId').val(data);
			}
		});
	}
	function logout() {
		$.ajax("./ajax", {
			method : "POST",
			data : {
				"query" : "logout"
			},
			success : function(data) {
				window.location = "main.html";
				return true;
			}
		});
	}
	
	window.onload = function() {
		fillYearsSelect();
		fillMarkSelect();
		fillShiftGearSelect();
		fillEngineSelect();
		getUserId();
	}
</script>
</head>
<body>
	<div class="container">
		<%@ include file = "navbar.jsp" %>

		<form:form action="addCar" method="post" enctype="multipart/form-data"  modelAttribute="carForm">
			<input type="hidden" class="form-control" id="userId" name="userId">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			
			<div class="row">
			
				<spring:bind path="brand">
					<div class="form-group col ${status.error ? 'has-error' : ''}">
						<label for="brands">Марка :</label> 
						<form:select path="brand" placeholder="Марка" onchange="fillModelSelect()"
									 id="brands" class="form-control"></form:select>
						<form:errors path="brand" class="text-danger"></form:errors>
					</div>
				</spring:bind>
				
				<spring:bind path="model">
					<div class="form-group col ${status.error ? 'has-error' : ''}">
						<label for="model">Модель:</label>
						<form:select path="model" placeholder="Модель" id="models"
									class="form-control">
							<option>none</option>
						</form:select>
						<form:errors path="model" class="text-danger"></form:errors>
					</div>
				</spring:bind>				

				<spring:bind path="year">
					<div class="form-group col ${status.error ? 'has-error' : ''}">
						<label for="year">Год: </label> 
						<form:select path="year" id="year" 
								class="form-control">
							<option>none</option>
						</form:select>
						<form:errors path="year" class="text-danger"></form:errors>
					</div>
				</spring:bind>			
				
			</div>
			
			<div class="row" id="engine">
			
				<spring:bind path="engine.type">
					<div class="form-group col-4 ${status.error ? 'has-error' : ''}">
						<label for="fuel">Топливо:</label> 
						<form:select path="engine.type" id="fuel"
									class="form-control">
							<option>none</option>
						</form:select>
						<form:errors path="engine.type" class="text-danger"></form:errors>
					</div>
				</spring:bind>	
				
				<spring:bind path="engine.volume">
					<div class="form-group col-4" ${status.error ? 'has-error' : ''}>
						<label for="volume">Объем:</label> 
						<form:input path="engine.volume" id="volume" type="number" step="0.1"
									 placeholder="Объем" class="form-control"></form:input>
						<form:errors path="engine.volume" class="text-danger"></form:errors>
					</div>
				</spring:bind>	
				
				<spring:bind path="engine.power">
					<div class="form-group col-4 ${status.error ? 'has-error' : ''}">
						<label for="power">Мощность:</label> 
						<form:input path="engine.power" id="power" type="number" step="1"
									 placeholder="Мощность" class="form-control"></form:input>
						<form:errors path="engine.power" class="text-danger"></form:errors>
					</div>
				</spring:bind>	
				
				<div class="form-group col"></div>
			</div>
			
			<div class="row">
				<spring:bind path="body.type">
					<div class="form-group col-4 ${status.error ? 'has-error' : ''}">
						<label for="body">Тип кузова:</label> 
						<form:input path="body.type" id="body" type="text"
									 placeholder="Кузов" class="form-control"></form:input>
						<form:errors path="body.type" class="text-danger"></form:errors>
					</div>
				</spring:bind>	

				<spring:bind path="shiftGear">
					<div class="form-group col-4 ${status.error ? 'has-error' : ''}">
						<label for="shiftGear">Коробка передач :</label>
						<form:select path="shiftGear" id="shiftGear"
									class="form-control">
							<option>none</option>
						</form:select>
						<form:errors path="shiftGear" class="text-danger"></form:errors>
					</div>
				</spring:bind>	
				
				<div class="form-group col"></div>

			</div>
		
				<spring:bind path="price">
					<div class="form-group col-4 ${status.error ? 'has-error' : ''}">
						<label for="price">Цена:</label> 
						<form:input path="price" id="power" type="number"
									 placeholder="цена" class="form-control"></form:input>
						<form:errors path="price" class="text-danger"></form:errors>
					</div>
				</spring:bind>	
			
			
			

			<div class="form-group col-4">
				<label for="desc">Описание:</label>
				<textarea class="form-control" rows="4" name="desc" maxlength="1000"></textarea>
			</div>
			
			<spring:bind path="imgFiles">
				<div class="form-group col-4" id="imgFiles">
					<label for="imgFiles">Фото:</label> 
					<form:input path="imgFiles" name="imgFiles" type="file"
								onchange="addUploadField()" class="form-control-file border"></form:input>
				</div>
				<div class="form-group col-4">
					<form:errors path="imgFiles" class="text-danger"></form:errors>
				</div>
			</spring:bind>	
			
			
			<button type="submit" class="btn btn-success" onclick="">Success
			</button>
		</form:form>
	</div>
</body>
</html>

