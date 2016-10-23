package com.by.android.fishwater.splash;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.account.login.view.LoginPage;

/**
 * Created by by.huang on 2016/10/9.
 */
public class SplashPage extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_splash,container,false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView()
    {
        FWPresenter.getInstance().showTabLayout(View.GONE);
        if (!TextUtils.isEmpty(AccountManage.getInstance().getSessionId())) {
            FWPresenter.getInstance().autoLogin();
        }
        else
        {
            LoginPage mPage = new LoginPage();
            FWPresenter.getInstance().replaceFragment(mPage);
        }
    }




}
