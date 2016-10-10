package com.by.android.fishwater.util;

import android.widget.Toast;

import com.by.android.fishwater.FWApplication;

/**
 * Created by by.huang on 2016/10/10.
 */
public class ToastUtil {

    public static void show (String msg)
    {
        Toast.makeText(FWApplication.mApplication.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
