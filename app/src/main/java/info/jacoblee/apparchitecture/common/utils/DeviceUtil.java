package info.jacoblee.apparchitecture.common.utils;

import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.webkit.WebSettings;

import info.jacoblee.apparchitecture.MyApplication;

public class DeviceUtil {
    private String userAgent;
    private String packageName;
    private String appVersion;
    private Integer appVersionCode;
    private String osVersion;
    private String deviceBrand;
    private String deviceModel;

    public static DeviceUtil getInstance() {
        DeviceUtil instance = SingletonHolder.sInstance;
        return instance;
    }

    //静态内部类
    private static class SingletonHolder {
        private static final DeviceUtil sInstance = new DeviceUtil();
    }
    public String getUserAgent() {
        if (userAgent == null) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(MyApplication.instance);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
            if (TextUtils.isEmpty(userAgent)) userAgent = "okhttp 3.0";
            StringBuilder sb = new StringBuilder();
            for (int i = 0, length = userAgent.length(); i < length; i++) {
                char c = userAgent.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
            }
            sb.append(" AppArchitecure");
            sb.append("/");
            sb.append(getAppVersion());
            sb.append(" ");
            sb.append(getAppVersionCode());
            userAgent = sb.toString();
        }
        return userAgent;
    }

    public String getPackageName() {
        if (packageName == null) {
            packageName = MyApplication.instance.getPackageName();
        }
        return packageName;
    }

    public String getAppVersion() {
        if (appVersion == null) {
            try {
                appVersion = MyApplication.instance.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                appVersion = "";
            }
        }
        return appVersion;
    }


    public Integer getAppVersionCode() {
        if (appVersionCode == null) {
            try {
                appVersionCode = MyApplication.instance.getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                appVersionCode = 0;
            }
        }
        return appVersionCode;
    }
    public String getOsVersion() {
        if (osVersion == null) {
            osVersion = android.os.Build.VERSION.RELEASE;
        }
        return osVersion;
    }

    public String getDeviceBrand() {
        if (deviceBrand == null) {
            deviceBrand = android.os.Build.BRAND;
        }
        return deviceBrand;
    }

    public String getDeviceModel() {
        if (deviceModel == null) {
            deviceModel = android.os.Build.MODEL;
        }
        return deviceModel;
    }
}
