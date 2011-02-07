/**
 * CheckLoginServlet.java: Responsible for informing clients about
 *     login statuses.
 **
 * © 2011 Christopher E. Granade (cgranade@gmail.com).
 ** 
 * This file is part of Shadowcloud.
 *
 * Shadowcloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shadowcloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Shadowcloud.  If not, see <http://www.gnu.org/licenses/>.
 */

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
