package com.example.webservice.userandpost.valueobject;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geography implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	double lat;
	double lng;

	public Geography() {
		super();
	}
	
	public Geography(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "Geography [lat=" + lat + ", lng=" + lng + "]";
	}

}
