package com.example.webservice.userandpost.valueobject;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Company implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String name;
	String catchPhrase;
	String bs;

	public Company() {
		super();
	}
	public Company(String name, String catchPhrase, String bs) {
		super();
		this.name = name;
		this.catchPhrase = catchPhrase;
		this.bs = bs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatchPhrase() {
		return catchPhrase;
	}

	public void setCatchPhrase(String catchPhrase) {
		this.catchPhrase = catchPhrase;
	}

	public String getBs() {
		return bs;
	}

	public void setBs(String bs) {
		this.bs = bs;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", catchPhrase=" + catchPhrase + ", bs=" + bs + "]";
	}

	

}
