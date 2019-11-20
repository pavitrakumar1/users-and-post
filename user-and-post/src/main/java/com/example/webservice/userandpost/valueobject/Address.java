package com.example.webservice.userandpost.valueobject;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	String street;
	String suite;
	String city;
	String zipcode;
	Geography geo;

	public Address() {
		super();
	}
	
	public Address(String street, String suite, String city, String zipcode, Geography geo) {
		super();
		this.street = street;
		this.suite = suite;
		this.city = city;
		this.zipcode = zipcode;
		this.geo = geo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSuite() {
		return suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Geography getGeo() {
		return geo;
	}

	public void setGeo(Geography geo) {
		this.geo = geo;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", suite=" + suite + ", city=" + city + ", zipcode=" + zipcode + ", geo="
				+ geo + "]";
	}

}
