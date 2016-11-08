package com.by.android.fishwater.mine.view;

import android.os.Bundle;

import com.by.android.fishwater.FWActivity;

import org.xutils.x;

import static android.view.View.X;

/**
 * Created by by.huang on 2016/11/7.
 */

public class SettingPage extends FWActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
