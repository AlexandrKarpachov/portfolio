package ru.job4j.todo.servlets;

import java.io.IOException;
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.job4j.todo.models.Item;
import ru.job4j.todo.validator.Validator;

/**
 * Servlet implementation class StatusController
 */

public class StatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Validator validator = Validator.getInstance();
		long id = Long.parseLong(req.getParameter("itemId"));
        Item item = validator.getByID(id);
        item.setDone(!item.getDone());
        validator.updateItem(item);
	}

}
