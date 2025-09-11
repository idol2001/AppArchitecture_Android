package info.jacoblee.apparchitecture.ui.main.model;

import lombok.Data;

@Data
public class DailyWeatherModel {
	private String fxDate; //  2021-11-15,
	private String sunrise; //  06; // 58,
	private String sunset; //  16; // 59,
	private String moonrise; //  15; // 16,
	private String moonset; //  03; // 40,
	private String moonPhase; //  盈凸月,
	private String moonPhaseIcon; //  803,
	private String tempMax; //  12,
	private String tempMin; //  -1,
	private String iconDay; //  101,
	private String textDay; //  多云,
	private String iconNight; //  150,
	private String textNight; //  晴,
	private String wind360Day; //  45,
	private String windDirDay; //  东北风,
	private String windScaleDay; //  1-2,
	private String windSpeedDay; //  3,
	private String wind360Night; //  0,
	private String windDirNight; //  北风,
	private String windScaleNight; //  1-2,
	private String windSpeedNight; //  3,
	private String humidity; //  65,
	private String precip; //  0.0,
	private String pressure; //  1020,
	private String vis; //  25,
	private String cloud; //  4,
	private String uvIndex; //  3
}
