package com.weatherrest.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.weatherrest.model.WeatherDate;
import com.weatherrest.model.WeatherInfo;

public class WeatherService {

	List<WeatherInfo> weatherObjs = new ArrayList<>();

	public List<WeatherDate> getDates() {
		String line = "";
		BufferedReader buffer1 = null;
		List<WeatherDate> dates = new ArrayList<>();
		try {
			URL url = getClass().getResource("dailyweather.csv");
			File file1 = new File(url.getPath());
			buffer1 = new BufferedReader(new FileReader(file1));
			buffer1.readLine();
			while ((line = buffer1.readLine()) != null) {
				WeatherDate info2 = new WeatherDate();
				String[] data = line.split(",");
				info2.setDate(data[0]);
				dates.add(info2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffer1 != null)
					buffer1.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dates;
	}

	public WeatherInfo getWeatherInfo(String date) {
		String line = "";
		BufferedReader buffer1 = null;
		try {
			URL url = getClass().getResource("dailyweather.csv");
			File file1 = new File(url.getPath());
			buffer1 = new BufferedReader(new FileReader(file1));
			buffer1.readLine();
			while ((line = buffer1.readLine()) != null) {
				if (line.split(",")[0].equals(date)) {
					WeatherInfo info = new WeatherInfo();
					String[] data = line.split(",");
					info.setDATE(data[0]);
					info.setTMAX(Double.parseDouble(data[1]));
					info.setTMIN(Double.parseDouble(data[2]));
					// weatherObjs.add(info);
					return info;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffer1 != null)
					buffer1.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void addInformation(WeatherInfo info) {
		String line;
		PrintWriter out = null;
		try {
			URL url = getClass().getResource("dailyweather.csv");
			File file2 = new File(url.getPath());
			FileWriter fw = new FileWriter(file2, true);
			out = new PrintWriter(fw);
			StringBuilder sb = new StringBuilder();
			sb.append(info.getDATE());
			sb.append(",");
			sb.append(info.getTMAX());
			sb.append(",");
			sb.append(info.getTMIN());
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null){
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteRow(String date) {
		URL url = getClass().getResource("dailyweather.csv");
		File file1 = new File(url.getPath());
	    try {
	    	List<String> out = Files.lines(file1.toPath())
	    			.filter(line -> !line.split(",")[0].equals(date))
	    			.collect(Collectors.toList());
			Files.write(file1.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<WeatherInfo> getForecast(String date) {
		String line = "";
		BufferedReader buffer1 = null;
		List<WeatherInfo> list = new ArrayList<>();
		try {
			URL url = getClass().getResource("dailyweather.csv");
			File file1 = new File(url.getPath());
			buffer1 = new BufferedReader(new FileReader(file1));
			buffer1.readLine();
			while ((line = buffer1.readLine()) != null) {
				String[] data = line.split(",");
					WeatherInfo info2 = new WeatherInfo();
					info2.setDATE(data[0]);
					info2.setTMAX(Double.parseDouble(data[1]));
					info2.setTMIN(Double.parseDouble(data[2]));
					list.add(info2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffer1 != null)
					buffer1.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<WeatherInfo> result = new ArrayList<>();
		int input = Integer.parseInt(date);
		int index = 0;
		for (WeatherInfo info : list) {
			if (!info.getDATE().equals(date)) {
				index++;
			} else
				break;
		}
		for (int i = index; i < index + 7; i++) {
			if (i >= list.size()) {
				WeatherInfo info = new WeatherInfo();
				info.setDATE((input + i) + "");
				info.setTMAX(7 + i);
				info.setTMIN(i);
				result.add(info);
			} else {
				result.add(list.get(i));
			}
		}
		return result;
	}

}