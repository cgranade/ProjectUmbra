/**
 * CharacterServlet.java: Used for querying about the status of a character.
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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cgranade.gamemodel.sr4.Combatant;
import com.cgranade.gamemodel.sr4.Combatant.InitiativeScore;
import com.cgranade.gamemodel.sr4.InitiativeType;
import com.cgranade.shadowcloud.protocol.Invite;
import com.cgranade.shadowcloud.protocol.InviteListMessage;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

public class CharacterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// TODO: integrate with Objectify and return sensible results.
		
		resp.setContentType("application/json");
		
		final UserService users = UserServiceFactory.getUserService();
		
		
		
//		Map<InitiativeType, InitiativeScore> initScores = new HashMap<InitiativeType, Combatant.InitiativeScore>();
//		initScores.put(InitiativeType.PHYSICAL, new InitiativeScore(10, 3, 2));
//		initScores.put(InitiativeType.ASTRAL, new InitiativeScore(10, 3, 2));
//		initScores.put(InitiativeType.MATRIX, new InitiativeScore(10, 3, 2));
//		
//		Combatant comb = new Combatant(
//				"Arain",
//				initScores
//				); 
		
		Gson gson = new Gson();
		Combatant comb = gson.fromJson("{\"name\":\"Arain\",\"initScores\":{\"ASTRAL\":{\"rawScore\":10,\"ip\":3,\"tie\":2,\"score\":14},\"PHYSICAL\":{\"rawScore\":10,\"ip\":3,\"tie\":2,\"score\":11},\"MATRIX\":{\"rawScore\":10,\"ip\":3,\"tie\":2,\"score\":15}},\"initType\":\"PHYSICAL\",\"conditions\":{\"P\":{\"damage\":0,\"max\":10},\"S\":{\"damage\":0,\"max\":10}}}", Combatant.class);
		resp.getWriter().println(gson.toJson(comb));
	}

}
