package info.jacoblee.apparchitecture.common.webservice;

import info.jacoblee.apparchitecture.ui.city.model.SearchCitesResponse;
import info.jacoblee.apparchitecture.ui.city.model.TopCitiesResponse;
import info.jacoblee.apparchitecture.ui.main.model.DailyWeatherResponse;
import info.jacoblee.apparchitecture.ui.main.model.NowWeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    @GET("/geo/v2/city/top")
    Call<TopCitiesResponse> top_cities(@Query("range") String range, @Query("number") String number, @Query("lang") String lang);

    @GET("/geo/v2/city/lookup")
    Call<SearchCitesResponse> search_city(@Query("location") String location, @Query("adm") String adm,
                                          @Query("range") String range, @Query("number") String number, @Query("lang") String lang);

    @GET("/v7/weather/now")
    Call<NowWeatherResponse> weather_now(@Query("location") String location, @Query("lang") String lang, @Query("unit") String unit);

    @GET("/v7/weather/{days}")
    Call<DailyWeatherResponse> weather_days(@Path("days") String days, @Query("location") String location, @Query("lang") String lang, @Query("unit") String unit);
}
