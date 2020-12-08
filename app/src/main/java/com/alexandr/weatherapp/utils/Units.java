package com.alexandr.weatherapp.utils;

public enum Units{
    standard(1, "standard"), metric(2, "metric"), imperial(3, "imperial");
    Units(Integer id, String value){
        this.id=id;
        this.value=value;
    }

    public static Units intToUnits(Integer value){
        Units[] array = Units.values();
        Units units=null;
        for (Units o:array) {
            if(value == o.id) {
                units = o;
                break;
            }
        }

        System.out.println("units in Units = "+units);
        return units;
    }



    private Integer id;
    private String value;
    public Integer getId(){return id;}
    public String getValue(){return value;}
}
