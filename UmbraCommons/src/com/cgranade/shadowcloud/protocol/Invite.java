/**
 * Invite.java: Represents an invite sent to a player.
 **
 * © 2011 Christopher E. Granade (cgranade@gmail.com).
 ** 
 * This file is part of UmbraCommons.
 *
 * UmbraCommons is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * UmbraCommons is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with UmbraCommons.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cgranade.shadowcloud.protocol;

public class Invite {
	private String gameName, hostNickname, hostAddress;
	
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public String getHostAddress() {
		return hostAddress;
	}
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	
	public String getHostNickname() {
		return hostNickname;
	}
	public void setHostNickname(String hostNickname) {
		this.hostNickname = hostNickname;
	}
}