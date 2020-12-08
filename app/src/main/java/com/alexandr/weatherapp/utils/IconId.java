package com.alexandr.weatherapp.utils;

import com.alexandr.weatherapp.R;

enum IconId{
    Day01("01d", R.drawable.d2x01), Day02("02d", R.drawable.d2x02),
    Day03("03d", R.drawable.d2x03), Day04("04d", R.drawable.d2x04),
    Day09("09d", R.drawable.d2x09), Day10("10d", R.drawable.d2x10),
    Day11("11d", R.drawable.d2x11), Day13("13d", R.drawable.d2x13),
    Day50("50d", R.drawable.d2x50),
    Night01("01n", R.drawable.n2x01), Night02("02n", R.drawable.n2x02),
    Night03("03n", R.drawable.n2x03),Night04("04n", R.drawable.n2x04),
    Night09("09n", R.drawable.n2x09),Night10("10n", R.drawable.n2x10),
    Night11("11n", R.drawable.n2x11),Night13("13n", R.drawable.n2x13),
    Night50("50n", R.drawable.n2x50);

    IconId(String id, Integer res){
        this.id = id;
        this.res = res;
    }
    private String id;
    private Integer res;

    public String getId() {return id;}
    public Integer getRes() {return res;}
    public boolean compare(String id){return (this.id.equals(id))? true: false;}
}