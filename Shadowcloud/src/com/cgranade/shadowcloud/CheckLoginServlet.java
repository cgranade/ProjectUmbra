package com.cgranade.shadowcloud;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgranade.shadowcloud.protocol.LoginStatusMessage;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

public class CheckLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 7556222900346620282L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("application/json");
		
		final UserService users = UserServiceFactory.getUserService();
		
		Gson gson = new Gson();
		LoginStatusMessage respObj = new LoginStatusMessage();
		respObj.setLoggedIn(users.isUserLoggedIn());
		// TODO: Accept query or JSON parameter for continue URL.
		respObj.setLogoutURL(users.createLogoutURL("/index.html"));
		respObj.setLoginURL(users.createLoginURL("/index.html"));
		
		resp.getWriter().println(gson.toJson(respObj));
	}

}
