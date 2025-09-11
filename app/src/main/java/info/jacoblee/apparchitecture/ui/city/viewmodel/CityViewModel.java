package info.jacoblee.apparchitecture.ui.city.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import info.jacoblee.apparchitecture.common.webservice.WebApiRequest;
import info.jacoblee.apparchitecture.ui.city.model.CityModel;

import info.jacoblee.apparchitecture.ui.city.model.SearchCitesResponse;
import info.jacoblee.apparchitecture.ui.city.model.TopCitiesResponse;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CityViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private WebApiRequest request;
    @Getter
    private MutableLiveData<ArrayList<CityModel>> cityList;
    @Getter
    private MutableLiveData<String> errorMessage;
    @Getter @Setter
    private String searchCityName;

    public void init() {
        request = new WebApiRequest();
        cityList = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        errorMessage.setValue("");
        searchCityName = "";
        cityList.setValue(new ArrayList<>());
    }

    public void getTopCities() {
        request.get_top_cities(new Callback<TopCitiesResponse>() {
            @Override
            public void onResponse(Call<TopCitiesResponse> call, Response<TopCitiesResponse> response) {
                if (response.code() == 200) {
                    String code = response.body().getCode();
                    if ("200".equals(code)) {
                        cityList.setValue(response.body().getTopCityList());
                        return;
                    }
                }
                errorMessage.setValue("网络错误，请稍后重试");
            }

            @Override
            public void onFailure(Call<TopCitiesResponse> call, Throwable t) {
                errorMessage.setValue("网络错误，请稍后重试");
            }
        });
    }

    public void searchCities(String searchWord) {
        if (TextUtils.isEmpty(searchWord)) {
            return;
        }
        request.search_cities(searchWord, new Callback<SearchCitesResponse>() {
            @Override
            public void onResponse(Call<SearchCitesResponse> call, Response<SearchCitesResponse> response) {
                if (response.code() == 200) {
                    String code = response.body().getCode();
                    if ("200".equals(code)) {
                        cityList.setValue(response.body().getLocation());
                        return;
                    }
                }
                errorMessage.setValue("网络错误，请稍后重试");
            }

            @Override
            public void onFailure(Call<SearchCitesResponse> call, Throwable t) {
                errorMessage.setValue("网络错误，请稍后重试");
            }
        });
    }
}