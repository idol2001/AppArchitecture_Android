package info.jacoblee.apparchitecture.common.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.airbnb.deeplinkdispatch.DeepLinkModule;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@DeepLinkModule
public class RouterModule {
    private final static String schemeURI = "apparchitecture://jacoblee.info";
    public final static String RTLoginPage = schemeURI + "/login";

    public static String RTCityWeatherPage(String cityName) {
        String cityEncoded = cityName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            cityEncoded = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        }
        return schemeURI + "/city_weather/" + cityEncoded;
    }

    public static void RedirectTo(Context context, String deeplink) {
        Intent intent = new Intent(context, RouterActivity.class);
        intent.setData(Uri.parse(deeplink));
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent);
    }

    public static void RedirectTo(Context context, String deeplink, Map<String, String> parameters) {
        Intent intent = new Intent(context, RouterActivity.class);
        intent.setData(Uri.parse(deeplink));
        if (parameters != null && parameters.size() > 0) {
            for (String key : parameters.keySet()) {
                intent.putExtra(key, parameters.get(key));
            }
        }
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent);
    }
}
