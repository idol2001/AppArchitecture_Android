package info.jacoblee.apparchitecture.ui.main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import info.jacoblee.apparchitecture.R;
import info.jacoblee.apparchitecture.common.router.PageEnum;
import info.jacoblee.apparchitecture.common.router.Router;
import info.jacoblee.apparchitecture.ui.city.CityFragment;
import info.jacoblee.apparchitecture.ui.city.model.CityModel;
import info.jacoblee.apparchitecture.ui.main.viewmodel.MainViewModel;
import info.jacoblee.apparchitecture.ui.main.model.RealTimeWeatherModel;

public class MainFragment extends Fragment {

    private String cityName;
    private MainViewModel viewModel;
    private TextView timeTextView;
    private TextView nowTempTextView;
    private TextView cityNameTextView;
    private TextView detailWeatherTextView;
    private ImageButton cityButton;
    private ActivityResultLauncher<Intent> resultLauncher;

    public static MainFragment newInstance(String cityName) {
        MainFragment fragment = new MainFragment();
        fragment.cityName = cityName;
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && CityFragment.CITY_SEARCH_RESULT.equals(result.getResultCode()) && result.getData() != null) {
                    CityModel city = (CityModel) result.getData().getSerializableExtra(CityFragment.CITY_SEARCH_RESULT_DATA);
                    if (city != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(city.getAdm1());
                        if (city.getAdm2() != null && !city.getAdm2().equals(city.getAdm1())) {
                            stringBuilder.append(" ").append(city.getAdm2());
                        }
                        if (city.getName() != null && !city.getName().equals(city.getAdm2())) {
                            stringBuilder.append(" ").append(city.getName());
                        }
                        viewModel.getCityName().setValue(stringBuilder.toString());
                        viewModel.setLocation(city.getId());
                        viewModel.requestNowWeatherInfo();
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        viewModel.init(this.cityName);
        viewModel.getTimeText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String time) {
                timeTextView.setText(time);
            }
        });
        viewModel.getNowTemp().observe(this.getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String temp) {
                nowTempTextView.setText(temp);
            }
        });
        viewModel.getDetailWeather().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String detail) {
                detailWeatherTextView.setText(detail);
            }
        });
        viewModel.getCityName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String city) {
                cityNameTextView.setText(city);
            }
        });
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String err) {
                if (err != null && !err.isEmpty()) {
                    Toast.makeText(getActivity(), err, Toast.LENGTH_LONG).show();
                }
            }
        });

        initView();
        viewModel.requestNowWeatherInfo();
    }

    public void initView() {
        timeTextView = getView().findViewById(R.id.real_time);
        nowTempTextView = getView().findViewById(R.id.real_time_temperature);
        detailWeatherTextView = getView().findViewById(R.id.detail_weather);
        cityNameTextView = getView().findViewById(R.id.city_name);
        cityButton = getView().findViewById(R.id.click_button);
        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router.page(PageEnum.CITY_PAGE).with("乌鲁木齐").to(getActivity(), resultLauncher);
            }
        });
    }

}