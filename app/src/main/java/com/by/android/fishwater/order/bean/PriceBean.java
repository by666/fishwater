package com.by.android.fishwater.order.bean;

/**
 * Created by by.huang on 2016/10/31.
 */

public class PriceBean {

    public String name;
    public float price;

    public static PriceBean createPriceBean(String name,float price)
    {
        PriceBean data = new PriceBean();
        data.name = name;
        data.price = price;
        return data;
    }
}
