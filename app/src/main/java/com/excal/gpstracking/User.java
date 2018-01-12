package com.excal.gpstracking;

import java.io.Serializable;

public class User implements Serializable{

	private String name;
	private String uname;
	private String pass;
	private String lat;
	private String lon;
	private String dates;

	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	private String v_number;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getV_number() {
		return v_number;
	}
	public void setV_number(String vNumber) {
		v_number = vNumber;
	}

}
