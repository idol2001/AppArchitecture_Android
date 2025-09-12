package info.jacoblee.apparchitecture.ui.main.model;

import lombok.Data;

@Data
public class RealTimeWeatherModel {
	private String obsTime = ""; //  2020-06-30T21; // 40+08; // 00,
	private String temp; //  24,
	private String feelsLike; //  26,
	private String icon; //  101,
	private String text; //  多云,
	private String wind360; //  123,
	private String windDir; //  东南风,
	private String windScale; //  1,
	private String windSpeed; //  3,
	private String humidity; //  72,
	private String precip; //  0.0,
	private String pressure; //  1003,
	private String vis; //  16,
	private String cloud; //  10,
	private String dew; //  21
}
