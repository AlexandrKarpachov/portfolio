<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.carsale.domain.Car" %>
<!DOCTYPE html>
<html>
<head>
<title>CarSale</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script type="text/javascript">
var page = 0;

function fillYearsSelect() {
	$.ajax("./ajax/years", {
		method : "get",
		success : function(data) {
			var result = "";
			result += "<option>none</option>\n";
			for (var i = 0; i < data.length; i++) {
				$('#minYear').append('<option>' + data[i] + '</option>');
				$('#maxYear').append('<option>' + data[i] + '</option>');
			}
		}
	});
}

function fillMarkSelect() {
	$.ajax("./ajax/brands", {
		method : "get",
		success : function(data) {
			var result = "";
			result += "<option>none</option>\n"
			for (var i = 0; i < data.length; i++) {
				   result += '<option value="' + data[i].id + '">' + data[i].name + "</option>"
			}
			var select = document.getElementById("brands");
			select.innerHTML = result;
		}
	});
}

function fillModelSelect() {
	var result = "<option>none</option>";
	var markId = $("#brands :selected").val();
	var select = document.getElementById("models");
	select.innerHTML = result;
	if ("none" != markId) {
		$.ajax("./ajax/models", {
			method : "get",
			data : {
				"brandId" : markId
			},
			success : function(models) {
				
				for (var i = 0; i < models.length; i++) {
					   result += '<option value="' + models[i].id + '">' + models[i].name + "</option>\n"
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
				result += '<option value="' + data[i].name + '">' + data[i].value + "</option>\n"
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
				result += '<option value="' + data[i].name + '">' + data[i].value + "</option>\n"
			}
			select.innerHTML = result;
		}
	});
}

function fillCarTable(cars) {
	var table = document.getElementById("car-table");
	var result = "";
	var color = "";
	var sale = "";
	var image;
	var ctx = "${pageContext.request.contextPath}";
	for (var i = 0; i < cars.length; i++) {
		image = typeof cars[i].images[0] === 'undefined' ? 'default.png' : cars[i].images[0];
		if (cars[i].active) {
			color = 'primary';
			sale = "";
		} else {
			color = 'secondary';
			sale = '<h4><color="red">Продано!</color></h4>';
		}
		result += '<tr class="table-' + color + '"><th rowspan="2" class="w-25"><a href="carView/' + cars[i].id + '">'
			+ '<img src="'+ ctx + '/upload/photo/' + image + '" alt="Нет Фото" width="240" height="160"></a></th>\n'
			+ '<th colspan="2"><h5><a href="carView/' + cars[i].id + '">' + cars[i].brand + ' ' + cars[i].model +'</a></h5></th>'
			+ '<th rowspan="2" class="align-middle text-center">' + sale + '</th></tr>'		
			+ '<tr class="table-' + color + '"><th class="w-25"> Цена: ' + cars[i].price + '<br>' 
			+ 'Год: ' + cars[i].year + '<br>'
			+ 'Тип кузова: ' + cars[i].body.type + '</th>'
			+ '<th> Двигатель: ' + cars[i].engine.volume + ' ' + cars[i].engine.type.value + '<br>'
			+ ' КПП: ' + cars[i].shiftGear.value + '</th>';
	}
	if (result === "") {
		result = "<tr> не найдено автомоболей с такими параметрами</tr>";
	}
	table.innerHTML = result;
}

function getFilter() {
	var resultCount = $("#rowCount option:selected").text();
	var mark = $("#brands option:selected").text();
	var model = $("#models option:selected").text();
	var minVolume = document.getElementById('minVolume').value;
	var maxVolume = document.getElementById('maxVolume').value;
	var minPrice = document.getElementById('minPrice').value;
	var maxPrice = document.getElementById('maxPrice').value;
	var minYear = document.getElementById('minYear').value;
	var maxYear = document.getElementById('maxYear').value;
	var fuel = document.getElementById('fuel').value;
	var shiftGear = document.getElementById('shiftGear').value;
	var isActive = document.getElementById("only-active").checked;
	minYear = minYear === 'none' ? 0 : minYear;
	maxYear = maxYear === 'none' ? 0 : maxYear;
	minVolume = minVolume === "" ? 0 : minVolume;
	maxVolume = maxVolume === "" ? 0 : maxVolume;
	minPrice = minPrice === "" ? 0 : minPrice;
	maxPrice = maxPrice === "" ? 0 : maxPrice;
	
	if (minVolume != 0 && maxVolume != 0 && minVolume > maxVolume) {
		var temp = minVolume;
		minVolume = maxVolume;
		maxVolume = temp;
	}
	if (minYear != 0 && maxYear != 0 && minYear > maxYear) {
		temp = minYear;
		minYear = maxYear;
		maxYear = temp;
	}
	if (minPrice != 0 && maxPrice != 0 && minPrice > maxPrice) {
		temp = minPrice;
		minPrice = maxPrice;
		maxPrice = temp;
	}
	
	return {
		page:  page,
		maxResultSize: resultCount, 
		mark: mark, 
		model: model,
		fuelType: fuel,
		minVolume: minVolume, 
		maxVolume: maxVolume, 
		shiftGear: shiftGear, 
		minPrice: minPrice, 
		maxPrice: maxPrice, 
		minYear: minYear, 
		maxYear: maxYear, 
		active: !isActive
	};
}

function send() {
	var filter = getFilter();
	$.ajax("./ajax/cars", {
		method : "get",
		data : filter,
		success : function(data) {
			fillCarTable(data);
			return true;
		}
	});
}

function getPageCount(func) {
	$.ajax("./ajax/pageCount", {
		type: 'GET',
		data : getFilter(),
		success: function(response) {
			func(response);
		}
	});
 }

function pagination(move) {
	page += move;
	getPageCount(function(pageCount) {
		pageCount--;
		var previous = $('#previous');
		var next = $('#next');
		if (pageCount === 0) {
			$('#pagination').addClass("hidden");
		} else {
			$('#pagination').removeClass("hidden");

			if (page === 0) {
				previous.addClass("disabled");
				previous.attr("onclick", "");
			} else {
				previous.removeClass("disabled");
				previous.attr("onclick", "pagination(-1)");
			}
			if (page < pageCount) {
				next.removeClass("disabled");
				next.attr("onclick", "pagination(1)");
			} else {
				next.addClass("disabled");
				next.attr("onclick", "");
			}
		}
		
	});
	send(page)
} 

window.onload = function () {
	fillYearsSelect();
	fillMarkSelect();
	fillShiftGearSelect();
	fillEngineSelect();
	pagination(0);
}


</script>
<style>
.head-block {
	height: 240px; /* Высота блока */
	background: #68D98B; /* Цвет фона */
	background-image: url('./resources/img/back.jpg');
	background-size: cover;
	text-align: center;
	color: white;
	margin-bottom: 5px;
}

.center {
	height: 240px;
	line-height: 240px; /* same as height! */
}

.navbar {
	margin-bottom: 5px;
}

.centered {
	align-items: center;
	margin-bottom: 5px;
}

.hidden {
	display: none;
}
</style>
</head>
<body>
	<div class="container ">
		<div class="head-block">
			<div class="row">
				<div class="col-7">
					<h1 class="display-1 center">Car Sale</h1>
				</div>
			</div>
		</div>
		<%@ include file = "navbar.jsp" %>
		<div class="main">
			<div class="row">
				<!-- --------- Filters --------------- -->
				<aside class="col-sm-3">
					<div class="card">
						<article class="card-group-item">
							<header class="card-header">
								<h6 class="title">Фильтры</h6>
							</header>
							<div class="filter-content">
								<div class="card-body">
									<form>
										<div class="form-group form-check">
											<label class="form-check-label"> <input
												class="form-check-input" type="checkbox" id="only-active">
												Показывать проданные
											</label>
										</div>
										<div class="form-group">
											<label for="brands">Марка :</label> <select
												class="form-control" id="brands"
												onchange="fillModelSelect()">
												<option>none</option>
											</select>
										</div>
										<div class="form-group">
											<label for="models">Модель :</label> <select
												class="form-control" id="models">
												<option>none</option>
											</select>
										</div>
										<div class="form-group">
											<label>Год: </label>
											<div class="form-inline">
												<div class="minYear">
													<select class="form-control" id="minYear">
														<option>none</option>
													</select>
												</div>
												<div class="maxYear">
													<select class="form-control" id="maxYear">
														<option>none</option>
													</select>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label>Цена: </label>
											<div class="form-inline">
												<div class="minPrice">
													<input type="number" class="form-control" id="minPrice"
														placeholder="от">
												</div>
												<div class="maxPrice">
													<input type="number" class="form-control" id="maxPrice"
														placeholder="до">
												</div>
											</div>
										</div>

										<div class="form-group">
											<label>Объем: </label>
											<div class="form-inline">
												<div class="minValue">
													<input type="number" step="0.1" class="form-control"
														id="minVolume" placeholder="от">
												</div>
												<div class="maxValue">
													<input type="number" step="0.1" class="form-control"
														id="maxVolume" placeholder="до">
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="fuel">Тип топлива :</label> <select
												class="form-control" id="fuel">

											</select>
										</div>
										<div class="form-group">
											<label for="shiftGear">Коробка передач :</label> 
											<select	class="form-control" id="shiftGear"></select>
										</div>
									</form>
								</div>

								<button type="button"
									class="btn btn-outline-secondary mx-auto d-block centered"
									onclick="pagination(0)">Поиск</button>
							</div>
						</article>
					</div>
				</aside>
				<!-- -------------  Filters.//  ---------------------->
				<!-- -------------  Main Table  ---------------------->
				<div class="col-sm-9">
					<div class="row">
						<div class="col-5"></div>
						<div class="col-2">
							<div class="form-group">
								<select	class="form-control centered" id="rowCount">
									<option>2</option>
									<option>5</option>
									<option selected >10</option>
									<option>20</option>
									<option>50</option>
								</select>
							</div>
						
						</div>
						<div class="col-5"></div>
					</div>
					<table class="table table-primary">
						<tbody class="car table" id="car-table"></tbody>
					</table>
					<div>
						<ul class="pagination hidden justify-content-center" id="pagination">
							<li class="page-item" id="previous"><a class="page-link" href="#">Назад</a></li>
							<li class="page-item" id="next"><a class="page-link" href="#">Вперед</a></li>
						</ul>
					</div>
				</div>
				<!-- -------------  //Main Table  ---------------------->
			</div>
		</div>
	</div>
</body>
</html>