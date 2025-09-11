package info.jacoblee.apparchitecture.ui.city.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.jacoblee.apparchitecture.R;
import info.jacoblee.apparchitecture.common.OnItemClickListener;
import info.jacoblee.apparchitecture.ui.city.model.CityModel;


public class CityFragment extends Fragment {

    public static final Integer CITY_SEARCH_RESULT = 1001;
    public static final String CITY_SEARCH_RESULT_DATA = "CITY_MODEL";
    private CityViewModel mViewModel;
    private CityRecyclerAdapter mAdapter;
    private EditText mEditText;
    private RecyclerView mRecyclerView;


    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CityViewModel.class);
        // TODO: Use the ViewModel

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.init();
        mViewModel.getCityList().observe(this.getViewLifecycleOwner(), new Observer<ArrayList<CityModel>>() {
            @Override
            public void onChanged(ArrayList<CityModel> cityModels) {
                mAdapter.setArrayList(cityModels);

            }
        });

        initView();
        mViewModel.getTopCities();
    }

    private void initView() {
        mAdapter = new CityRecyclerAdapter();
        mEditText = this.getView().findViewById(R.id.search_edit_text);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                if (!s.isEmpty()) {
                    mViewModel.setSearchCityName(s);
                    mViewModel.searchCities(s);
                }
            }
        });
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!v.getText().toString().isEmpty()) {
                    mViewModel.setSearchCityName(v.getText().toString());
                    mViewModel.searchCities(v.getText().toString());
                    mEditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mEditText.getWindowToken(),0);
                    return true;
                } else {
                    return false;
                }
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                //Toast.makeText(getActivity(), "点击了" + mViewModel.getCityList().getValue().get(postion).getName(), Toast.LENGTH_LONG).show();
                if (mViewModel.getCityList() != null && mViewModel.getCityList().getValue() != null) {
                    CityModel city = mViewModel.getCityList().getValue().get(postion);
                    if (city != null) {
                        Intent intent = new Intent();
                        intent.putExtra(CITY_SEARCH_RESULT_DATA, city);
                        CityFragment.this.getActivity().setResult(CITY_SEARCH_RESULT, intent);
                        getActivity().finish();
                    }
                }
            }
        });
        mRecyclerView = (RecyclerView)getView().findViewById(R.id.city_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

    }

}