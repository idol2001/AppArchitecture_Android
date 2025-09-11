package info.jacoblee.apparchitecture.ui.main.model;

import java.util.List;

import info.jacoblee.apparchitecture.common.webservice.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DailyWeatherResponse extends BaseResponse {
    private String updateTime;
    private String fxLink;
    private List<DailyWeatherModel> daily;
}

