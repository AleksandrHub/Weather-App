package com.alexandr.weatherapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alexandr.weatherapp.R;
import com.alexandr.weatherapp.mvp.presenter.list.IForecastWeatherPresenter;
import com.alexandr.weatherapp.mvp.view.list.HourlyWeatherRowView;
import com.alexandr.weatherapp.utils.Indicators;
import com.alexandr.weatherapp.utils.Utils;
import com.alexandr.weatherapp.utils.ui.UIUtils;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyForecastRVAdapter extends RecyclerView.Adapter<HourlyForecastRVAdapter.ViewHolder> {
    private IForecastWeatherPresenter presenter;

    public HourlyForecastRVAdapter(IForecastWeatherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hourly_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pos = position;
        presenter.bind(holder);
        RxView.clicks(holder.itemView).map(o -> holder).subscribe(presenter.getClickSubject());
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements HourlyWeatherRowView {
        int pos = 0;

        @BindView(R.id.tv_time)
        TextView timeTV;
        @BindView(R.id.iv_icon)
        ImageView iconIV;
        @BindView(R.id.tv_temp)
        TextView tempTV;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos++;
        }

        @Override
        public void setTime(String title) {
            timeTV.setText(title);
        }

        @Override
        public void setIcon(String id) {
            iconIV.setImageDrawable(itemView.getResources().getDrawable(Utils.getIconRes(id)));
        }

        @Override
        public void setTemperature(Float temp) {
            tempTV.setText(UIUtils.addUnits(temp, Indicators.Temperature,Utils.getUnits()));
        }
    }
}
