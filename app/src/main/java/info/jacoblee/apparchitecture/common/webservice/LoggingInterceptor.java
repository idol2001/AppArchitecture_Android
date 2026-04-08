package info.jacoblee.apparchitecture.common.webservice;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import info.jacoblee.apparchitecture.MyApplication;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;

public class LoggingInterceptor implements Interceptor {
    // 建议通过构造函数传入，避免直接依赖 BuildConfig 导致的解耦失败
    private final boolean isDebug;

    public LoggingInterceptor(boolean isDebug) {
        this.isDebug = isDebug;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        // 1. Release 环境直接跳过，不做任何额外计算
        if (!isDebug) return chain.proceed(request);

        long t1 = System.nanoTime();
        // 打印请求信息
        Log.i(MyApplication.TAG, String.format("HTTP --> Sending request %s%n%s",
                request.url(), request.headers()));

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        double duration = (t2 - t1) / 1e6d;
        Log.i(MyApplication.TAG, String.format("HTTP <-- Received [%d] for %s in %.1fms%n%s",
                response.code(), response.request().url(), duration, response.headers()));

        // 2. 只有非 200 且 Body 不为空时尝试读取，且必须使用 clone
        if (response.code() != 200) {
            Log.i(MyApplication.TAG, String.format("HTTP <-- Error body: %s", response.body().string()));
        }

        return response;
    }
}