package info.jacoblee.apparchitecture.ui.city;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.airbnb.deeplinkdispatch.DeepLink;

import info.jacoblee.apparchitecture.R;
import info.jacoblee.apparchitecture.common.router.Router;
import info.jacoblee.apparchitecture.ui.city.fragment.CityFragment;

@DeepLink(Router.City_Page)
public class CityActivity extends AppCompatActivity {

    @Override()
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CityFragment.newInstance())
                    .commitNow();
        }
    }
}