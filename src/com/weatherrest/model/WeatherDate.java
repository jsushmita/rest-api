package com.weatherrest.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class WeatherDate {

	String date;

	@JsonProperty("DATE")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
