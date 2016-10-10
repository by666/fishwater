package com.by.android.fishwater.splash;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.by.android.fishwater.R;

/**
 * Created by by.huang on 2016/10/9.
 */
public class SplashPage extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_splash,container,false);
    }


}
