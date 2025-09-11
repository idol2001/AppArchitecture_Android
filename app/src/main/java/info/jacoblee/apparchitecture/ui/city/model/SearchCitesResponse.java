package info.jacoblee.apparchitecture.ui.city.model;

import java.util.ArrayList;

import info.jacoblee.apparchitecture.common.webservice.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchCitesResponse extends BaseResponse {
    private ArrayList<CityModel> location;
}
