package com.alexandr.weatherapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alexandr.weatherapp.R;
import com.alexandr.weatherapp.mvp.presenter.list.IForecastWeatherPresenter;
import com.alexandr.weatherapp.mvp.view.list.DailyWeatherRowView;
import com.alexandr.weatherapp.utils.Indicators;
import com.alexandr.weatherapp.utils.Utils;
import com.alexandr.weatherapp.utils.ui.UIUtils;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyForecastRVAdapter extends RecyclerView.Adapter<DailyForecastRVAdapter.ViewHolder> {
    private IForecastWeatherPresenter presenter;

    public DailyForecastRVAdapter(IForecastWeatherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_forecast, parent, false));
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


    public class ViewHolder extends RecyclerView.ViewHolder implements DailyWeatherRowView {
        int pos = 0;

        @BindView(R.id.tv_weekday)
        TextView weekdayTV;
        @BindView(R.id.tv_date)
        TextView dateTV;
        @BindView(R.id.iv_icon_daily)
        ImageView iconIV;
        @BindView(R.id.tv_temp_afternoon)
        TextView tempAfternoonTV;
        @BindView(R.id.tv_temp_night)
        TextView tempNightTV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos++;
        }

        @Override
        public void setWeekday(String weekday) {
            weekdayTV.setText(weekday);
        }

        @Override
        public void setDate(String date) {
            dateTV.setText(date);
        }


        @Override
        public void setIcon(String id) {
            iconIV.setImageDrawable(itemView.getResources().getDrawable(Utils.getIconRes(id)));
        }

        @Override
        public void setTemperatureAfternoon(Float temp) {
            tempAfternoonTV.setText(UIUtils.addUnits(temp, Indicators.Temperature,Utils.getUnits()));
        }

        @Override
        public void setTemperatureNight(Float temp) {
            tempNightTV.setText(UIUtils.addUnits(temp, Indicators.Temperature,Utils.getUnits()));
        }

    }
}
