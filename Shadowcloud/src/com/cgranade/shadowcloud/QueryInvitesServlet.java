/**
 * QueryInvitesServlet.java: Queried by clients to discover outstanding
 *     invites.
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
		i.setGameSystem("com.cgranade.gamemodel.sr4");
		msg.add(i);
		
		Gson gson = new Gson();
		resp.getWriter().println(gson.toJson(msg));
	}

}
