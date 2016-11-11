package com.by.android.fishwater;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.by.android.fishwater.util.DeviceManager;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by by.huang on 2016/11/5.
 */

public class FWActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        DeviceManager.init(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
