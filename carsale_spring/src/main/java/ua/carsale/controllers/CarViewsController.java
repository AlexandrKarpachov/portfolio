package ua.carsale.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.carsale.domain.Car;
import ua.carsale.domain.User;
import ua.carsale.persistance.CarRepository;
import ua.carsale.persistance.UserRepository;
import ua.carsale.validators.CarValidator;

@Controller
public class CarViewsController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private CarValidator carValidator;
	
	@Autowired
	private CarRepository carStorage;
	
	@RequestMapping(value = "/carView/{id}", method = RequestMethod.GET)
	private String carView(@PathVariable("id") long id, Model model) throws JsonProcessingException {
		Car car = carStorage.findById(id).get();
		User user = car.getUser();
		ObjectMapper mapper = new ObjectMapper();
		String carJson = mapper.writeValueAsString(car);
		String userJson = mapper.writeValueAsString(user);
		model.addAttribute("car", carJson);
		model.addAttribute("user", userJson);
		return "carView";
	}
	
	@RequestMapping(value="/addCarForm", method=RequestMethod.GET)
	public String addCar(final Model model, @ModelAttribute("carForm") final Car car) {
		if (model.asMap().containsKey("carFormBindingResult")) {
			model.addAttribute("org.springframework.validation.BindingResult.carForm",
					model.asMap().get("carFormBindingResult"));
		}
		return "addCar";
	}
	
	@RequestMapping(value="/addCar", method=RequestMethod.POST, 
			consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String addCarp(Model model, 
				@ModelAttribute("carForm") final Car car,
				final BindingResult bindingResult, 
				final RedirectAttributes redirectAttributes,
				HttpServletRequest servletRequest,
				final Principal principal) throws IllegalStateException, IOException {
		
		carValidator.validate(car, bindingResult);
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("carFormBindingResult", bindingResult);
            redirectAttributes.addFlashAttribute("carForm", car);
			return "redirect:/addCarForm";
		}
		ServletContext context = servletRequest.getSession().getServletContext();
		String userLogin = principal.getName();
		User user = userRepository.findByLogin(userLogin);
		car.setUser(user);
		this.saveImages(car, context);
		carStorage.save(car);
		return "redirect:/main";
	}
	
	private void saveImages(Car car, ServletContext context) throws IllegalStateException, IOException {
		String uploadFolder = context.getRealPath("/upload/photo");
		for (MultipartFile img : car.getImgFiles()) {
			if (!img.isEmpty()) {
				String fileName = img.getOriginalFilename();
				File imageFile = new File(uploadFolder, fileName);
				imageFile.createNewFile();
				img.transferTo(imageFile);
				car.addImage(fileName);
			}
		}
	}
	
	@RequestMapping(value = "/sell", method = RequestMethod.POST)
	private String makeInactive(@RequestParam("carId") long id, String login) {
		Car car = carStorage.findById(id).get();
		car.setActive(false);
		carStorage.save(car);
		return "redirect:user/" + login;
	}
}
