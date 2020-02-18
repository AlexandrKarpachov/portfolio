package ru.job4j.carstore.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.job4j.carstore.models.User;

public class SignInController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/carsale-views/SignIn.html").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Validator validator = Validator.getInstance();
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		User user = validator.getUserByLogin(login);
			
		if (user != null && user.checkPass(password)) {
			request.getSession().setAttribute("login", user.getLogin());
			response.sendRedirect("main.html");
			//request.getRequestDispatcher("main.html").forward(request, response);
		} else {
			request.setAttribute("error", "true");
            request.getRequestDispatcher("WEB-INF/carsale-views/SignIn.html").forward(request, response);
		}
		
		
        
        //response.sendRedirect(request.getContextPath() + "/users");
		//doGet(request, response);
	}

}
