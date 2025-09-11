package info.jacoblee.apparchitecture.ui.main;

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

@DeepLink(PageEnum.MAIN_PAGE_DEEPLINK)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String cityId = intent.getStringExtra("city_id");
        if (!TextUtils.isEmpty(cityId)) {
            try {
                cityId = URLDecoder.decode(cityId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(cityId))
                    .commitNow();
        }
    }

}