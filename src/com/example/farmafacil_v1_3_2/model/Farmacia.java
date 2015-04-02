package com.example.farmafacil_v1_3_2.model;

import java.io.Serializable;

public class Farmacia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private double latitude;
	private double longitude;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


}
