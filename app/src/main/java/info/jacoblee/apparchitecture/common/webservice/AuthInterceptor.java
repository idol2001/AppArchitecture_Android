package info.jacoblee.apparchitecture.common.webservice;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;

import info.jacoblee.apparchitecture.MyApplication;
import info.jacoblee.apparchitecture.R;
import info.jacoblee.apparchitecture.common.utils.DeviceUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final String privateKeyString = MyApplication.instance.getString(R.string.qweather_private_key); // 从 https://dev.qweather.com 获取
    private final String keyId = MyApplication.instance.getString(R.string.qweather_key_id);
    private final String projectId = MyApplication.instance.getString(R.string.qweather_project_id);

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        DeviceUtil device = DeviceUtil.getInstance();
        // Request customization: add request headers
        Request request = original.newBuilder()
                    .removeHeader("User-Agent")//移除旧的
                    .addHeader("User-Agent", device.getUserAgent())
                    .addHeader("Authorization", "Bearer " + getJWTToken()) // 增加token header
                    .addHeader("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();
        return chain.proceed(request);
    }

    private String getJWTToken() {
        String jwt = "";
        try {
            byte[] privateKeyBytes = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            }
            Security.addProvider(new BouncyCastleProvider());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EdDSA", new BouncyCastleProvider());
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Header
            String headerJson = "{\"alg\": \"EdDSA\", \"kid\": \""+ keyId +"\"}";

            // Payload
            long iat = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                iat = ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() - 30;
            }
            long exp = iat + 900;
            String payloadJson = "{\"sub\": \"" + projectId + "\", \"iat\": " + iat + ", \"exp\": " + exp + "}";

            // Base64url header+payload
            String headerEncoded = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                headerEncoded = Base64.getUrlEncoder().encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
            }
            String payloadEncoded = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                payloadEncoded = Base64.getUrlEncoder().encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
            }
            String data = headerEncoded + "." + payloadEncoded;

// Sign
            Signature signer = Signature.getInstance("EdDSA", new BouncyCastleProvider());
            signer.initSign(privateKey);
            signer.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signature = signer.sign();

            String signatureEncoded = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                signatureEncoded = Base64.getUrlEncoder().encodeToString(signature);
            }

            jwt = data + "." + signatureEncoded;
        } catch (Exception e) {
            Log.e(MyApplication.TAG, "getJWTToken error: ", e);
        }
        return jwt;
    }
}
