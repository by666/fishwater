package com.by.android.fishwater;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

/**
 * Created by by.huang on 2016/10/9.
 */
public class FWApplication extends Application{

    public static FWApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init()
    {

        mApplication = this;
        x.Ext.init(this);
        Fresco.initialize(this);
    }

}
