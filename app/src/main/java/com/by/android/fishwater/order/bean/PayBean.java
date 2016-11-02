package com.by.android.fishwater.order.bean;

import com.by.android.fishwater.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public class PayBean {
    public String url;
    public String name;
    public boolean isSelect;


    public static List<PayBean> getDatas() {
        List<PayBean> datas= new ArrayList<>();
        datas.add(createPayBean("res:///"+ R.drawable.order_pay_zfb,"支付宝",true));
        datas.add(createPayBean("res:///"+ R.drawable.order_pay_wx,"微信",false));
        return datas;
    }

    private static PayBean createPayBean(String url, String name, boolean isSelect) {
        PayBean data = new PayBean();
        data.url = url;
        data.name = name;
        data.isSelect = isSelect;
        return data;
    }
}
