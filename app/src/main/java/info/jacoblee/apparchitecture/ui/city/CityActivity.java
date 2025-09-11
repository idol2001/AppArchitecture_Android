package info.jacoblee.apparchitecture.ui.city;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.airbnb.deeplinkdispatch.DeepLink;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import info.jacoblee.apparchitecture.R;
import info.jacoblee.apparchitecture.common.router.PageEnum;
import info.jacoblee.apparchitecture.common.router.Router;

@DeepLink(PageEnum.CITY_PAGE_DEEPLINK)
public class CityActivity extends AppCompatActivity {

    @Override()
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("city_name");
        if (!TextUtils.isEmpty(cityName)) {
            try {
                cityName = URLDecoder.decode(cityName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CityFragment.newInstance(cityName))
                    .commitNow();
        }
    }
}