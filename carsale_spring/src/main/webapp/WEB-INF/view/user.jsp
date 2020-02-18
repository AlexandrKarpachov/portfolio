<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<title>Личный кабинет</title>
<script type="text/javascript">

function fillPage() {
	var userJSON = '${user}';
	var user = jQuery.parseJSON(userJSON);
	fillCarTable(user.cars);
	fillUserData(user);
}

function fillUserData(user) {
	var car_count = 0;
	for (var i = 0; i < user.cars.length; i++) {
		if (user.cars[i].active) {
			car_count++;
		}
	}
	var result =  '<p><span>' + user.name + '</span><br>'
		+ '<span>' + user.surname + '</span></p>'
		+ '<p>Автомобилей выставлено ' + car_count + '</p>';
	$("#userData").html(result);	
}

function sellCar(id) {
	$.ajax("./ajax/sell", {
		method : "get",
		data : { "carId" : id },
		success : function(data) {
			fillPage();
		}
	});
}

function fillCarTable(cars) {
	var result = "";
	var sale;
	var color;
	for (var i = 0; i < cars.length; i++) {
		var c = $("#only-active").prop("checked") && !cars[i].active;
		if ($("#only-active").prop("checked") && !cars[i].active) {
			continue;
		}
		var image = typeof cars[i].images[0] === 'undefined' ? 'default.png' : cars[i].images[0];
		if (cars[i].active === true) {
			var login = '${pageContext.request.userPrincipal.name}';
			sale = '<form action="${pageContext.request.contextPath}/sell" method="POST">'
					+ '<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>'
					+ '<input type="hidden" name="carId" value="' + cars[i].id + '"/>'
					+ '<input type="hidden" name="login" value="' + login + '"/>'
					+ '<button type="submit" class="btn btn-danger btn-lg">Продать</button>'
					+ '</form>';
			color = 'primary';
		} else {
			sale = '<h4><color="red">Продано!</color></h4>';
			color = 'secondary';
		}
		
		
		result += '<tr class="table-' + color + '"><th rowspan="2" class="w-25">'
				+ '<a href="${pageContext.request.contextPath}/carView/' + cars[i].id + '">'
				+ '<img src="${pageContext.request.contextPath}/upload/photo/' +  image + '" alt="Нет Фото" width="240" height="160"></a></th>\n'
				+ '<th colspan="2"><h5><a href="car_view.html?id=' + cars[i].id + '">' + cars[i].brand + ' ' + cars[i].model +'</a></h5></th>'
				+ '<th rowspan="2" class="align-middle text-center">' + sale + '</th></tr>'
				+ '<tr class="table-' + color + '"><th class="w-25"> Цена:' + cars[i].price + '<br>'
				+ 'Год: ' + cars[i].year + '<br>'
				+ 'Тип кузова: ' + cars[i].body.type + '</th>'
				+ '<th class="w-25"> Двигатель: ' + cars[i].engine.volume + ' ' + cars[i].engine.type.value + '<br>'
				+ ' КПП: ' + cars[i].shiftGear.value + '</th></tr>';
	}
	$("#car-table").html(result);
}


window.onload = function() {
	fillPage();
}
</script>
<style>
.navbar {
	margin-bottom: 5px;
}
</style>
</head>
<body>
	<div class="container">
	<%@ include file = "navbar.jsp" %>
		<div class="row">
			<!-- --------- info --------------- -->
			<aside class="col-sm-3">
				<div class="card">
					<article class="card-group-item">
						<header class="card-header">
							<h6 class="title">Информация:</h6>
						</header>
						<div class="filter-content">
							<div class="card-body">
									<div class="form-group form-check">
										<label class="form-check-label"> 
											<input class="form-check-input" onclick="fillPage()" type="checkbox" id="only-active">
											Только Активные
										</label>
									</div>
									<div class="form-group" id="userData">
										
									</div>
							</div>
						</div>
					</article>
				</div>
			</aside><!-- -------------  //info.  ---------------------->
			
			<!-- -------------  Car Table  ---------------------->
			<div class="col-sm-9">
				<table class="table">
					<tbody class="car table" id="car-table">

					</tbody>
				</table>
			</div>
		</div> <!-- //row -->
	</div>
</body>
</html>