package ua.carsale.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.carsale.domain.User;
import ua.carsale.persistance.UserRepository;
import ua.carsale.validators.UserValidator;

@Controller
public class UserViewsController {
	
	@Autowired
	UserValidator userValidator;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		userRepository.save(userForm);
		return "redirect: main";
	}

	@RequestMapping(value="/user/{login}", method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable("login") String login, 
			Model model) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		User user = userRepository.findByLogin(login);
		String result = mapper.writeValueAsString(user);
		model.addAttribute("user", result);
		return "user";
	}
}
