package info.jacoblee.apparchitecture.ui.main.model;

import info.jacoblee.apparchitecture.common.webservice.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NowWeatherResponse extends BaseResponse {
    private String updateTime;
    private String fxLink;
    private RealTimeWeatherModel now;
}
