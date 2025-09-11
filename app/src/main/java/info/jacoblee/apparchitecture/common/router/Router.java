package info.jacoblee.apparchitecture.common.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import info.jacoblee.apparchitecture.common.utils.DeviceUtil;

public class Router {
    private String routerUrl;
    private Map<String, String> param = new HashMap<>();

    public static final String Main_Page = "customscheme://example.com/main_page/{board_name}";
    public static final String City_Page = "customscheme://example.com/city_page";

    public static Router MainPage(String cityName) {
        Router router = SingletonHolder.sInstance;
        String encoded = cityName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            encoded = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        }
        router.routerUrl = Router.Main_Page;
        router.setParams(Map.of("city_name", encoded));
        return router;
    }

    public static Router CityPage() {
        Router router = SingletonHolder.sInstance;
        router.routerUrl = Router.City_Page;
        router.setParams(null);
        return router;
    }

    public void to(Context context) {
        Intent intent = new Intent(context, RouterActivity.class);
        intent.setData(Uri.parse(routerUrl));
        if (param != null && !param.isEmpty()) {
            for (String key : param.keySet()) {
                intent.putExtra(key, param.get(key));
            }
        }
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent);
    }

    public void to(Context context, @NotNull ActivityResultLauncher<Intent> resultLauncher) {
        Intent intent = new Intent(context, RouterActivity.class);
        intent.setData(Uri.parse(routerUrl));
        if (param != null && !param.isEmpty()) {
            for (String key : param.keySet()) {
                intent.putExtra(key, param.get(key));
            }
        }
        resultLauncher.launch(intent);
    }

    private void setParams(Map<String, String> map) {
        this.param.clear();
        if (map == null) return;
        this.param.putAll(map);
        if (!map.isEmpty() && routerUrl != null && !routerUrl.isEmpty()) {
            for (String key : map.keySet()) {
                if (map.get(key) != null) {
                    routerUrl = routerUrl.replace("{" + key + "}", map.get(key));
                } else {
                    routerUrl = routerUrl.replace("{" + key + "}", "");
                }
            }
        }
    }

    private static class SingletonHolder {
        private static final Router sInstance = new Router();
    }
}
