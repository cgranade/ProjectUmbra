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