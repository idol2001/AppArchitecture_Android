package info.jacoblee.apparchitecture.ui.city.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CityModel implements Serializable {
	private String name; // 北京,
	private String id; // 101010100,
	private String lat; // 39.90499,
	private String lon; // 116.40529,
	private String adm2; // 北京,
	private String adm1; // 北京市,
	private String country; // 中国,
	private String tz; // Asia/Shanghai,
	private String utcOffset; // +08; // 00,
	private String isDst; // 0,
	private String type; // city,
	private String rank; // 10,
	private String fxLink; // https; // //www.qweather.com/weather/beijing-101010100.html
}
