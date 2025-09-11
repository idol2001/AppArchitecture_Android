package info.jacoblee.apparchitecture.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import info.jacoblee.apparchitecture.MyApplication;

public class PropertiesUtil {
    public static String loadProperties(String fileName, String key) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = MyApplication.instance.getAssets().open(fileName);
            properties.load(inputStream);
            String value = properties.getProperty(key);
            return value;
        } catch (IOException e) {
            return "";
        }

    }
}
