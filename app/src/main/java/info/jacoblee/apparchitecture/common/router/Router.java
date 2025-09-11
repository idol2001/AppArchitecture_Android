package info.jacoblee.apparchitecture.common.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private PageEnum page;
    private Map<String, String> param = new HashMap<>();

    private Router() {
        // 禁用直接初始化Router对象
    }
    public static Router page(@NotNull PageEnum page) {
        Router router = SingletonHolder.sInstance;
        router.page = page;
        router.param.clear();
        return router;
    }

    public Router with(String ...params) {
        this.param.clear();
        if (page == null || page.getParamList().isEmpty()) return this;
        if (params == null || params.length<=0) return this;
        for (int i=0; i<page.getParamList().size(); i++) {
            String value = "";
            if (params.length >= i+1) value = params[i];
            this.param.put(page.getParamList().get(i), value);
        }
        return this;
    }

    public void to(Context context) {
        String deeplink = page.getPageUrl();
        Intent intent = new Intent(context, RouterActivity.class);

        if (param != null && !param.isEmpty()) {
            for (String key : param.keySet()) {
                deeplink = deeplink.replace("{"+key+"}", param.get(key));
            }
        }
        intent.setData(Uri.parse(deeplink));
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent);
    }

    public void to(Context context, @NotNull ActivityResultLauncher<Intent> resultLauncher) {
        String deeplink = page.getPageUrl();
        Intent intent = new Intent(context, RouterActivity.class);

        if (param != null && !param.isEmpty()) {
            for (String key : param.keySet()) {
                deeplink = deeplink.replace("{"+key+"}", param.get(key));
            }
        }
        intent.setData(Uri.parse(deeplink));
        resultLauncher.launch(intent);
    }

    private static class SingletonHolder {
        private static final Router sInstance = new Router();
    }
}
