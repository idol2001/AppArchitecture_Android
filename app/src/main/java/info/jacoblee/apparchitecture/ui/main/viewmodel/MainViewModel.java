package info.jacoblee.apparchitecture.ui.main.viewmodel;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
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
    private LiveData<String> timeText;
    @Getter
    private LiveData<String> nowTemp;
    @Getter
    private LiveData<String> detailWeather;
    @Getter
    private MutableLiveData<String> errorMessage;

    private MutableLiveData<RealTimeWeatherModel> realTimeWeather;
    private MutableLiveData<List<DailyWeatherModel>> futureWeather;
    @Setter
    private String location = "101031100";

    public void init(String cityName) {
        request = new WebApiRequest();
        this.cityName =new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        realTimeWeather = new MutableLiveData<>();
        futureWeather = new MutableLiveData<>();
        if (!TextUtils.isEmpty(cityName)) {
            this.cityName.setValue(cityName);
        } else {
            this.cityName.setValue("天津");
        }
        errorMessage.setValue("");
        realTimeWeather.setValue(new RealTimeWeatherModel());
        futureWeather.setValue(new ArrayList<>());
        timeText = Transformations.map(realTimeWeather, RealTimeWeatherModel::getObsTime);
        nowTemp = Transformations.map(realTimeWeather, model ->{
            return (model != null && model.getTemp() != null ? model.getTemp() : "--" )+ "℃";
        });
        detailWeather =Transformations.map(realTimeWeather, this::formatDetailWeather);
    }

    private String formatDetailWeather(RealTimeWeatherModel model) {
        if (model == null) return "";
        StringBuilder sb = new StringBuilder(model.getText() != null ? model.getText() : "");
        if (model.getWindDir() != null) {
            sb.append(" ").append(model.getWindDir());
        }
        if (model.getWindScale() != null) {
            sb.append(" ").append(model.getWindScale()).append("级");
        }
        if (model.getHumidity() != null) {
            sb.append(" 湿度：").append(model.getHumidity());
        }
        return sb.toString();
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