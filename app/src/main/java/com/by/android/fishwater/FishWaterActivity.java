package com.by.android.fishwater;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;

import com.by.android.fishwater.account.login.view.LoginPage;
import com.by.android.fishwater.util.DeviceManager;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.MarketChannelManager;
import com.by.android.fishwater.util.SettingFlags;
import com.by.android.fishwater.util.SystemHelper;


public class FishWaterActivity extends Activity {

    private FWPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fishwater);

        init();
        mPresenter = FWPresenter.getInstance();
        mPresenter.init(this);
        mPresenter.setDefaultFragment();

    }

    private void init()
    {
        MarketChannelManager.init(this);
        SettingFlags.init(this);
        HardwareUtil.initialize(this);
        SystemHelper.init(this);
        DeviceManager.init(this);
        SystemHelper.init(this);

        Display d = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        d.getMetrics(dm);
        HardwareUtil.screenWidth = dm.widthPixels;
        HardwareUtil.screenHeight = dm.heightPixels;
        HardwareUtil.windowWidth = dm.widthPixels;
        HardwareUtil.windowHeight = dm.heightPixels;

        HardwareUtil.density = dm.density;
    }

}
