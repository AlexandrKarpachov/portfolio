package ua.carsale.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.carsale.domain.*;
import ua.carsale.persistance.BrandRepository;
import ua.carsale.persistance.CarRepository;


@RestController
@RequestMapping("ajax")
public class AjaxController {
	private static final int MIN_YEAR = 1900;
	
	@Autowired
	private BrandRepository brandStorage;
	
	@Autowired
	private CarRepository carStorage;
	
	@RequestMapping(value = "/brands", method = RequestMethod.GET, produces = "application/json")
	public Iterable<Brand> getBrands() {
		return brandStorage.findAll();
	}
	
	@RequestMapping(value = "/years", method = RequestMethod.GET, produces = "application/json")
	private List<Integer> getYears(HttpServletRequest req) { 
		List<Integer> result = new ArrayList<>(); 
		Calendar calendar = Calendar.getInstance(); 
		int year = calendar.get(Calendar.YEAR); 
		for (int i = year; i > MIN_YEAR; i--) {
			result.add(i); 
		} 
		return result; 
	}
	
	@RequestMapping(value = "/models", method = RequestMethod.GET, produces = "application/json")
	private Set<Model> getModelsJson(@RequestParam long brandId) {
		Brand brand = brandStorage.findByIdAndFetchModelsEagerly(brandId);
		Set<Model> models = brand.getModels();
		return models;
	}
	
	@RequestMapping(value = "/fuel", method = RequestMethod.GET, produces = "application/json")
	private FuelType[] getEnginesJson() {
		return FuelType.values();
	}

	@RequestMapping(value = "/shiftGears", method = RequestMethod.GET, produces = "application/json")
	private ShiftGear[] getShiftGears() {
		return ShiftGear.values();
	}

	@RequestMapping(value = "/car", method = RequestMethod.GET, produces = "application/json")
	private Map<String, Object> getCarJson(@RequestParam long carId) {
		Map<String, Object> result = new HashMap<>();
		Car car = carStorage.findById(carId).get();
		result.put("car", car);
		result.put("name", car.getUser().getName());
		result.put("surname", car.getUser().getSurname());
		result.put("phone", car.getUser().getPhone());
		return result;
	}

	@RequestMapping(value = "/pageCount", method = RequestMethod.GET, produces = "application/json")
	private Long getPageCount(@ModelAttribute CarFilter filter) {
		return carStorage.getPagesCount(filter);
	}
	
	@RequestMapping(value = "/cars", method = RequestMethod.GET, produces = "application/json")
	private List<Car> getCarsJson(@ModelAttribute CarFilter filter) {
		return carStorage.findByCarFilter(filter);
	}
	
}
