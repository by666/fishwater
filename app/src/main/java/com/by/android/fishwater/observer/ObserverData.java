package com.by.android.fishwater.observer;

/**
 * Created by by.huang on 2016/11/5.
 */

public class ObserverData {

    public static final String Update_Address_Good = "update_address_good";
    public static final String Update_Address_List = "update_address_list";
    public String key;
    public FWObserver observer;

    public static ObserverData build(String key, FWObserver observer) {
        ObserverData data = new ObserverData();
        data.key = key;
        data.observer = observer;
        return data;
    }
}
