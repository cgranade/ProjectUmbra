/**
 * LoginStatusMessage.java: Represents a message informing a client as to
 *     their login status.
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

public class LoginStatusMessage {

	private boolean isLoggedIn;
	private String loginURL, logoutURL;
	
	public LoginStatusMessage() { }
	/*public LoginStatusMessage(boolean isLoggedIn, String loginURL, String logoutURL) {
		this.isLoggedIn = isLoggedIn;
		this.loginURL = loginURL;
		this.logoutURL = logoutURL;
	}*/
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	public String getLoginURL() {
		return loginURL;
	}
	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}
	public String getLogoutURL() {
		return logoutURL;
	}
	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}
	
}
