package com.weatherrest.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

//@XmlRootElement
public class WeatherInfo  implements Serializable, Comparable  {

//	@XmlElement(name="DATE")
	String DATE;
	
//	@XmlElement(name="TMAX")
	double TMAX;
	
//	@XmlElement(name="TMIN")
	double TMIN;
	
	public WeatherInfo() {
		// TODO Auto-generated constructor stub
	}
	
	@JsonProperty("DATE")
	public String getDATE() {
		return DATE;
	}



	public void setDATE(String dATE) {
		DATE = dATE;
	}


	@JsonProperty("TMAX")
	public double getTMAX() {
		return TMAX;
	}



	public void setTMAX(double tMAX) {
		TMAX = tMAX;
	}


	@JsonProperty("TMIN")
	public double getTMIN() {
		return TMIN;
	}



	public void setTMIN(double tMIN) {
		TMIN = tMIN;
	}



	@Override
	public int compareTo(Object info) {
		int date = Integer.parseInt(((WeatherInfo)info).getDATE());
        return Integer.parseInt(this.DATE)-date;
	}
	
}
