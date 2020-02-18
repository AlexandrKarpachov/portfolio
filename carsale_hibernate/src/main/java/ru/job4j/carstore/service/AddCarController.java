package ru.job4j.carstore.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ru.job4j.carstore.models.Body;
import ru.job4j.carstore.models.Car;
import ru.job4j.carstore.models.Engine;
import ru.job4j.carstore.models.FuelType;
import ru.job4j.carstore.models.ShiftGear;
import ru.job4j.carstore.models.User;

/**
 * Servlet implementation class AddCarController
 */
public class AddCarController extends HttpServlet {
	 private static final long serialVersionUID = 1L;
     
	    // location to store file uploaded
	    private static final String UPLOAD_DIRECTORY = "upload";
	 
	    // upload settings
	    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
	    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 5; // 5MB
	    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	 
	    private static final List<String> PARAMETERS = new CopyOnWriteArrayList<String>();
	    
	    /*
	     * Initiates a list of parameters coming from the site
	     */
	    @Override
		public void init() throws ServletException {
			PARAMETERS.add("brand");
			PARAMETERS.add("model");
			PARAMETERS.add("year");
			PARAMETERS.add("price");
			PARAMETERS.add("volume");
			PARAMETERS.add("fuel");
			PARAMETERS.add("shiftGear");
			PARAMETERS.add("body");
			PARAMETERS.add("power");
			PARAMETERS.add("desc");
			super.init();
		}


		/**
	     * Creates and save car instance to db, and downloads photo to disk.
	     */
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	    		throws ServletException, IOException {
	        if (!ServletFileUpload.isMultipartContent(request)) {
	            PrintWriter writer = response.getWriter();
	            writer.println("Error: Form must has enctype=multipart/form-data.");
	            writer.flush();
	            return;
	        }
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(MEMORY_THRESHOLD);
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setFileSizeMax(MAX_FILE_SIZE);
	        upload.setSizeMax(MAX_REQUEST_SIZE);
	        String uploadPath = getServletContext().getRealPath("")
	                + File.separator + UPLOAD_DIRECTORY;
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }
	        Map<String, String> parameters = new HashMap<>();
	        List<String> images = new ArrayList<>();
	        try {
	            List<FileItem> formItems = upload.parseRequest(request);
	            if (formItems != null && formItems.size() > 0) {
	                for (FileItem item : formItems) {
	                    
	                    if (item.isFormField()) {
	                    	String name = item.getFieldName();
	                    	if (PARAMETERS.contains(name)) {
	                    		String value = new String(item.getString().getBytes(StandardCharsets.ISO_8859_1),
	                                    StandardCharsets.UTF_8);
	                    		parameters.put(name, value);
	                    	}
	                    } else if (item.getSize() > 0) {
  	                    	String fileName = new File(item.getName()).getName();
  	                    	images.add(fileName);
	                        String filePath = uploadPath + File.separator + fileName;
	                        File storeFile = new File(filePath);
	                        item.write(storeFile);
	                    }
	                }
	            }
	        } catch (Exception ex) {
	           ex.printStackTrace();
	        }
	        Validator validator = Validator.getInstance();
	        Long id = (Long) request.getSession().getAttribute("userId");
	        User user = validator.getUserById(id);
	        Car car = new Car();
	        car.setActive(true);
	        car.setBody(new Body(parameters.get("body")));
	        car.setBrand(parameters.get("brand"));
	        car.setModel("model");
	        Engine engine = new Engine(
	        		FuelType.valueOf(parameters.get("fuel")), 
	        		Double.parseDouble(parameters.get("volume")), 
	        		Integer.parseInt(parameters.get("power"))
	        );
	        car.setEngine(engine);
	        car.setModel(parameters.get("model"));
	        car.setPrice(Integer.parseInt(parameters.get("price")));
	        car.setShiftGear(ShiftGear.valueOf(parameters.get("shiftGear")));
	        car.setYear(Integer.parseInt(parameters.get("year")));
	        car.setDescription(parameters.get("desc"));
	        for (String img : images) {
	        	car.addImage(img);
	        }
	        
	        user.addCar(car);
	        validator.updateUser(user);
	        response.sendRedirect(request.getContextPath() + "/main.html");
	        
	    }
}
