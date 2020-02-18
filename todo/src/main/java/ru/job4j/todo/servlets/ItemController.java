package ru.job4j.todo.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ru.job4j.todo.models.Item;
import ru.job4j.todo.validator.Validator;

/**
 * Servlet implementation class ItemController
 */
public class ItemController extends HttpServlet {
	private final Validator validator = Validator.getInstance();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Item> items = validator.getAll();
		ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonItems = mapper.writeValueAsString(items);
        ServletOutputStream out = response.getOutputStream();
        out.print(jsonItems);
        out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String desc = req.getParameter("desc");
        Item item = new Item(desc, new Timestamp(System.currentTimeMillis()));
        validator.addItem(item);
	}

}
