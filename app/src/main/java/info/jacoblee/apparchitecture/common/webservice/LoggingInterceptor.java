package info.jacoblee.apparchitecture.common.webservice;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import info.jacoblee.apparchitecture.MyApplication;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.i(MyApplication.TAG, String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        if (response != null) {
            Log.i(MyApplication.TAG, String.format("Received response status code: %d for %s in %.1fms%n%s", response.code(),
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            if (response.code() != 200) {
                Log.i(MyApplication.TAG, String.format("response: %s", response.body().string()));
            }
        }

        return response;
    }
}
