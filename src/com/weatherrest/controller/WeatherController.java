 package com.weatherrest.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.weatherrest.model.WeatherDate;
import com.weatherrest.model.WeatherInfo;
import com.weatherrest.service.WeatherService;

@Path("/")
public class WeatherController {
	
	public WeatherService service = new WeatherService();
	/*@POST
	@Path("/historical")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(InputStream incomingData) {
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		} 
		System.out.println("Data Received: " + crunchifyBuilder.toString());
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(crunchifyBuilder.toString()).build();
	}*/
 
	@GET
	@Path("/historical")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WeatherDate> getDates() {
		List<WeatherDate> result = service.getDates();
//		String json = new Gson().toJson(result);
//		return Response.status(200).entity(json).build();
		return result;
	}
	
	@GET
	@Path("/historical/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public WeatherInfo getWeatherInfo(@PathParam("date") String date){
		WeatherInfo result = service.getWeatherInfo(date);
		if(result == null)
			throw new WebApplicationException(404);
		return result;
	}
	
	@POST
	@Path("/historical")
//	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addInformation(JSONObject inputJsonObj) {
		WeatherInfo info = new WeatherInfo();
		System.out.println(inputJsonObj);
		String json = "";
		try {
			info.setDATE((String)inputJsonObj.get("DATE"));
			info.setTMAX((double)((int)inputJsonObj.get("TMAX")));
			info.setTMIN((double)((int)inputJsonObj.get("TMIN")));
			json = new Gson().toJson(info);
//			return Response.status(200).entity(json).build();
			service.addInformation(info);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(201).entity(json).build();
	}
	
	@DELETE
	@Path("/historical/{date}")
	public void deleteInfo(@PathParam("date") String date){
		service.deleteRow(date);
	}
	
	@GET
	@Path("/forecast/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WeatherInfo> getforecast(@PathParam("date") String date){
		List<WeatherInfo> result = service.getForecast(date);
		return result;
	}
}
