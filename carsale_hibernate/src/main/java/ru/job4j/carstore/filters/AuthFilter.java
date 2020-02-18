package ru.job4j.carstore.filters;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AuthFilter implements Filter {

    private final static List<String> NEED_LOGIN_PAGES = new CopyOnWriteArrayList<String>();
    private final static List<String> NEED_LOGOUT_PAGES = new CopyOnWriteArrayList<String>();
    
    public AuthFilter() {
        
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init(FilterConfig fConfig) throws ServletException {
		NEED_LOGIN_PAGES.add("add_car.html");
		NEED_LOGIN_PAGES.add("user.html");
		NEED_LOGOUT_PAGES.add("signIn.html");
		NEED_LOGOUT_PAGES.add("registration.html");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
        if ((session == null || session.getAttribute("login") == null) 
        		&& check(req.getRequestURI(), NEED_LOGIN_PAGES)) {
            resp.sendRedirect(String.format("%s/signIn.html", req.getContextPath()));
        } else if ((session != null && session.getAttribute("login") != null) 
        		&& check(req.getRequestURI(), NEED_LOGOUT_PAGES)) {
        	resp.sendRedirect(String.format("%s/main.html", req.getContextPath()));
        } else {
        	chain.doFilter(request, response);
        }
		
	}

	private boolean check(String uri, List<String> links) {
		boolean rst = false;
		for (String link : links) {
			if (uri.endsWith(link)) {
				rst = true;
				break;
			}
		}
		return rst;
	}

}
