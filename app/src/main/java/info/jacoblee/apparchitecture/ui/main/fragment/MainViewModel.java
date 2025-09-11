package info.jacoblee.apparchitecture.ui.main.fragment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import info.jacoblee.apparchitecture.MyApplication;
import info.jacoblee.apparchitecture.common.webservice.WebApiRequest;
import info.jacoblee.apparchitecture.ui.main.model.DailyWeatherModel;
import info.jacoblee.apparchitecture.ui.main.model.RealTimeWeatherModel;
import info.jacoblee.apparchitecture.ui.main.model.NowWeatherResponse;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private WebApiRequest request;
    @Getter
    private MutableLiveData<String> cityName;
    @Getter
    private MutableLiveData<RealTimeWeatherModel> realTimeWeather;
    @Getter
    private MutableLiveData<List<DailyWeatherModel>> futureWeather;
    @Getter
    private MutableLiveData<String> errorMessage;
    @Setter
    private String location = "101031100";

    public void init(String cityName) {
        request = new WebApiRequest();
        this.cityName = new MutableLiveData<>();
        realTimeWeather = new MutableLiveData<>();
        futureWeather = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        if (cityName != null & !"".equals(cityName)) {
            this.cityName.setValue(cityName);
        } else {
            this.cityName.setValue("天津");
        }
        realTimeWeather.setValue(new RealTimeWeatherModel());
        futureWeather.setValue(new ArrayList<>());
        errorMessage.setValue("");
    }

    public void requestNowWeatherInfo() {
        request.get_now_weather(location, new Callback<NowWeatherResponse>() {
            @Override
            public void onResponse(Call<NowWeatherResponse> call, Response<NowWeatherResponse> response) {
                if (response.code() == 200) {
                    String code = response.body().getCode();
                    if ("200".equals(code)) {
                        realTimeWeather.setValue(response.body().getNow());
                        return;
                    }
                }
                errorMessage.setValue("网络错误，请稍后重试");
            }

            @Override
            public void onFailure(Call<NowWeatherResponse> call, Throwable t) {
                errorMessage.setValue("网络错误，请稍后重试");
            }
        });
    }

    public void clickButton() {
        Log.i(MyApplication.TAG, "click button");
    }

}