package info.jacoblee.apparchitecture.common.webservice;

import java.util.concurrent.TimeUnit;

import info.jacoblee.apparchitecture.MyApplication;
import info.jacoblee.apparchitecture.R;
import info.jacoblee.apparchitecture.ui.city.model.SearchCitesResponse;
import info.jacoblee.apparchitecture.ui.city.model.TopCitiesResponse;
import info.jacoblee.apparchitecture.ui.main.model.DailyWeatherResponse;
import info.jacoblee.apparchitecture.ui.main.model.NowWeatherResponse;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebApiRequest {
    private String baseUrl = "https://" + MyApplication.instance.getString(R.string.qweather_api_host) + "/";
    private WebService webService;

    public WebApiRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getClient())
                .baseUrl(baseUrl) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        webService = retrofit.create(WebService.class);
    }
    public static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.MINUTES))
                .build();
    }

    public void get_top_cities(Callback<TopCitiesResponse> callback) {
        Call<TopCitiesResponse> call = webService.top_cities("cn", "20", "zh");
        call.enqueue(callback);
    }

    public void search_cities(String cityName, Callback<SearchCitesResponse> callback) {
        Call<SearchCitesResponse> call = webService.search_city(cityName, "", "cn", "20", "zh");
        call.enqueue(callback);
    }

    public void get_now_weather(String cityName, Callback<NowWeatherResponse> callback) {
        Call<NowWeatherResponse> call = webService.weather_now(cityName, "zh", "m");
        call.enqueue(callback);
    }

    public void get_daily_weather(String cityName, Callback<DailyWeatherResponse> callback) {
        Call<DailyWeatherResponse> call = webService.weather_days("10d", cityName, "zh", "m");
        call.enqueue(callback);
    }
}
