package ru.job4j.carstore.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.job4j.carstore.models.Brand;
import ru.job4j.carstore.models.Car;
import ru.job4j.carstore.models.CarFilter;
import ru.job4j.carstore.models.FuelType;
import ru.job4j.carstore.models.Model;
import ru.job4j.carstore.models.ShiftGear;
import ru.job4j.carstore.models.User;

public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IValidator validator = Validator.getInstance();
    private final Map<String, Function<HttpServletRequest, String>> answersGet = new ConcurrentHashMap<>();
    private final Map<String, Function<HttpServletRequest, String>> answersPost = new ConcurrentHashMap<>();
    private static final int MIN_YEAR = 1900;
    private final List<Integer> yearList = new CopyOnWriteArrayList<Integer>();
    
	@Override
	public void init() throws ServletException  {
		answersGet.put("engines", this::getEnginesJson);
		answersGet.put("shiftGears", this::getShiftGearsJson);
		answersGet.put("brands", this::getBrandsJson);
		answersGet.put("models", this::getModelsJson);
		answersGet.put("years", this::getYearsJson);
		answersGet.put("cars", this::getCarsJson);
		answersGet.put("userId", this::getUserID);
		answersGet.put("pages", this::getRowCount);
		answersGet.put("carById", this::getCarJson);
		answersGet.put("user", this::getUserJson);
		answersGet.put("sell", this::makeInactive);
		answersPost.put("logout", this::logout);
		answersPost.put("login", this::login);
		answersPost.put("register", this::register);
		
		
		yearList.addAll(getYearsList());
		super.init();
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter  out = response.getWriter();
		String query = request.getParameter("query");
		String jsonAnswer = answersGet.get(query).apply(request);
		out.print(jsonAnswer);
		out.flush();
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter  out = response.getWriter();
		String query = request.getParameter("query");
		String jsonAnswer = answersPost.get(query).apply(request);
		out.print(jsonAnswer);
		out.flush();
	}

	private String register(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		String login = req.getParameter("login");
		User user = validator.getUserByLogin(login);
		boolean result = false;
		String jsonRst = "";
		if (user == null) {
			String name = req.getParameter("name");
			String surname = req.getParameter("surname");
			String phone = req.getParameter("phone");
			String pass = req.getParameter("password");
			user = new User(name, surname, login, pass, phone);
			validator.addUser(user);
			result = true;
		}
		try {
			jsonRst = mapper.writeValueAsString(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonRst;
	}
	
	private String makeInactive(HttpServletRequest req) {
		Long id = Long.parseLong(req.getParameter("carId"));
		Car car = validator.getCarById(id);
		car.setActive(false);	
		validator.updateCar(car);
		return "{\"result\" : \"true\"}";
	}
	
	private String getUserJson(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			Long id = (Long) req.getSession().getAttribute("userId");
			User user = validator.getUserById(id);
			result = mapper.writeValueAsString(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String logout(HttpServletRequest req) {
		req.getSession().invalidate();
		return "{\"result\" : \"true\"}";
	}
	
	private String getCarJson(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		String result = "";
		try {
			Car car = validator.getCarById(id);
			map.put("car", car);
			map.put("name", car.getUser().getName());
			map.put("surname", car.getUser().getSurname());
			map.put("phone", car.getUser().getPhone());
			result = mapper.writeValueAsString(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String getRowCount(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			String jsonFilter = req.getParameter("filter");
			CarFilter filter = mapper.readValue(jsonFilter, CarFilter.class);
			Long cars = validator.getPagesCount(filter);
			result = mapper.writeValueAsString(cars);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String getUserID(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "none";
		try {
			Long id = (Long) req.getSession().getAttribute("userId");
			result = mapper.writeValueAsString(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String login(HttpServletRequest request) {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		User user = validator.getUserByLogin(login);
		boolean isWrong = true;
		if (user != null && user.checkPass(password)) {
			request.getSession().setAttribute("login", user.getLogin());
			request.getSession().setAttribute("userId", user.getId());
			isWrong = false;
		} 
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			result = mapper.writeValueAsString(isWrong);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getCarsJson(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			String jsonFilter = req.getParameter("filter");
			CarFilter filter = mapper.readValue(jsonFilter, CarFilter.class);
			List<Car> cars = validator.getCarByFilter(filter);
			result = mapper.writeValueAsString(cars);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String getModelsJson(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		Long brandId = Long.parseLong(req.getParameter("brandId"));
		Brand brand = validator.getBrandById(brandId);
		Set<Model> models = brand.getModels();
		String result = "";
		try {
			result = mapper.writeValueAsString(models);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private String getBrandsJson(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		List<Brand> brands = validator.getAllBrands();
		String result = "";
		try {
			result = mapper.writeValueAsString(brands);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private String getEnginesJson(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		FuelType[] types = FuelType.values();
		String result = "";
		try {
			result = mapper.writeValueAsString(types);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private String getShiftGearsJson(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		ShiftGear[] types = ShiftGear.values();
		String result = "";
		try {
			result = mapper.writeValueAsString(types);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private List<Integer> getYearsList() {
		List<Integer> result = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		for (int i = year; i > MIN_YEAR; i--) {
			result.add(i);
		}
		return result;
	}
	
	private String getYearsJson(HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			result = mapper.writeValueAsString(this.yearList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
