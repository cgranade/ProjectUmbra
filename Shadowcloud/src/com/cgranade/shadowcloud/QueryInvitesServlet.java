package com.cgranade.shadowcloud;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgranade.shadowcloud.protocol.Invite;
import com.cgranade.shadowcloud.protocol.InviteListMessage;
import com.cgranade.shadowcloud.protocol.LoginStatusMessage;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

public class QueryInvitesServlet extends HttpServlet {

	private static final long serialVersionUID = 7516710593031715738L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// TODO: integrate with Objectify and return sensible results.
		
		resp.setContentType("application/json");
		
		final UserService users = UserServiceFactory.getUserService();
		
		InviteListMessage msg = new InviteListMessage();
		Invite i = new Invite();
		i.setGameName("PI Gaming Club");
		i.setHostAddress("cgranade@gmail.com");
		i.setHostNickname("Chris G.");
		msg.add(i);
		
		Gson gson = new Gson();
		resp.getWriter().println(gson.toJson(msg));
	}

}
