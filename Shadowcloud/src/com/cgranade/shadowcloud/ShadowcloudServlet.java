package com.cgranade.shadowcloud;

import java.io.IOException;
import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class ShadowcloudServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		//resp.setContentType("text/plain");
		
		UserService users = UserServiceFactory.getUserService();
		
		if (req.getQueryString() == "check_login") {
			
		}
		
		resp.getWriter().println(users.getCurrentUser().getNickname());
		resp.getWriter().println(users.getCurrentUser().hashCode());
	}
	
}
