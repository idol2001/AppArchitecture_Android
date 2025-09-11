package info.jacoblee.apparchitecture.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.deeplinkdispatch.DeepLink;

import info.jacoblee.apparchitecture.R;
import info.jacoblee.apparchitecture.common.router.Router;
import info.jacoblee.apparchitecture.ui.main.fragment.MainFragment;

@DeepLink(Router.Main_Page)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("city_name");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(cityName))
                    .commitNow();
        }
    }

}