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
