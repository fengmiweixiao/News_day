package com.example.model;

import java.util.List;

public class Personal {
	
	
	private String uid;
	private String portrait;
	private int integration;
	private int comnum;
	private List<Loginlog> loginlog;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public int getIntegration() {
		return integration;
	}
	public void setIntegration(int integration) {
		this.integration = integration;
	}
	public int getComnum() {
		return comnum;
	}
	public void setComnum(int comnum) {
		this.comnum = comnum;
	}
	public List<Loginlog> getLoginlog() {
		return loginlog;
	}
	public void setLoginlog(List<Loginlog> loginlog) {
		this.loginlog = loginlog;
	}
	
}
