package info.jacoblee.apparchitecture.ui.city;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.jacoblee.apparchitecture.R;
import info.jacoblee.apparchitecture.common.OnItemClickListener;
import info.jacoblee.apparchitecture.ui.city.model.CityModel;
import lombok.Getter;
import lombok.Setter;

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.CityViewHolder> {
    @Setter
    private OnItemClickListener onItemClickListener;
    private ArrayList<CityModel> arrayList = new ArrayList<>();
    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_city_item, parent,false);

        return new CityViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        CityModel city = arrayList.get(position);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(city.getAdm1());
        if (city.getAdm2() != null && !city.getAdm2().equals(city.getAdm1())) {
            stringBuilder.append(" ").append(city.getAdm2());
        }
        if (city.getName() != null && !city.getName().equals(city.getAdm2())) {
            stringBuilder.append(" ").append(city.getName());
        }
        holder.cityTextView.setText(stringBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<CityModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Getter
        private TextView cityTextView;
        private final OnItemClickListener onClickListener;

        public CityViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            cityTextView = itemView.findViewById(R.id.city_text);
            onClickListener = listener;
        }


        @Override
        public void onClick(View view) {
            if (onClickListener != null)
                onClickListener.onItemClick(view, getBindingAdapterPosition());
        }
    }
}
