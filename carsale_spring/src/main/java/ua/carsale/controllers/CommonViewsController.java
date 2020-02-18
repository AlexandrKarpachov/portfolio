package ua.carsale.controllers;


import java.security.Principal;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CommonViewsController {

	@GetMapping(value={"/main", "/"})
	public String main(Principal principal, Model model) {
		return "main";
	}
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, Model model, String error){
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		return "login";
	}

	
}
