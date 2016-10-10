package com.by.android.fishwater;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.by.android.fishwater.splash.SplashPage;
import com.by.android.fishwater.util.DeviceManager;

/**
 * Created by by.huang on 2016/10/10.
 */
public class FWPresenter {
    private Activity mActivity;
    private static FWPresenter mInstance;

    public static FWPresenter getInstance()
    {
        if(mInstance == null)
        {
            synchronized (FWPresenter.class)
            {
                if(mInstance == null)
                {
                    mInstance = new FWPresenter();
                }
            }
        }
        return mInstance;
    }

    public void init(Activity activity)
    {
        this.mActivity = activity;
    }

    //初始化视图
    public void setDefaultFragment()
    {
        SplashPage splashPage = new SplashPage();
        addFragment(splashPage);
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layout_content, fragment);
        transaction.commit();
    }

    public void addFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.layout_content, fragment);
        transaction.commit();
    }

    public void removeFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void showFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    public void hideFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

}
