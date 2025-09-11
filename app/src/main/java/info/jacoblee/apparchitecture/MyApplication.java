package info.jacoblee.apparchitecture;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.airbnb.deeplinkdispatch.DeepLink;

import info.jacoblee.apparchitecture.common.router.Router;

public class MyApplication extends Application {
    public static final String TAG = "AppArchitecture";
    public static MyApplication instance;
    public static SharedPreferences shared_preference;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        shared_preference = getSharedPreferences(TAG, Context.MODE_PRIVATE);

        // 其他App、第三方SDK初始化
    }
}
