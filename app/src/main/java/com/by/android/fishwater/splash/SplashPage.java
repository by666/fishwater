package com.by.android.fishwater.splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.account.login.bean.LoginBean;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.login.bean.respond.LoginRespondBean;
import com.by.android.fishwater.account.login.bean.respond.UserRespondBean;
import com.by.android.fishwater.account.login.view.LoginPage;
import com.by.android.fishwater.homepage.view.HomePageActivity;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.DeviceManager;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.ToastUtil;

import org.xutils.x;

import java.util.HashMap;

/**
 * Created by by.huang on 2016/10/9.
 */
public class SplashPage extends FWActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_splash);
        x.view().inject(this);
        initScreen();
        initView();
    }

    private void initView()
    {
//        startActivity(new Intent(this, PayDemoActivity.class));
//        return;
        if (!TextUtils.isEmpty(AccountManage.getInstance().getSessionId())) {
            autoLogin();
        }
        else
        {
            Intent intent= new Intent(this,LoginPage.class);
            startActivity(intent);
            finish();
        }
    }

    private void initScreen()
    {

        Display d = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        d.getMetrics(dm);
        HardwareUtil.screenWidth = dm.widthPixels;
        HardwareUtil.screenHeight = dm.heightPixels;
        HardwareUtil.windowWidth = dm.widthPixels;
        HardwareUtil.windowHeight = dm.heightPixels;

        HardwareUtil.density = dm.density;
    }


    /**
     * 自动登录
     */
    public void autoLogin() {
        DeviceManager.getInstance().hideInputMethod();

        AccountManage manage = AccountManage.getInstance();
        String sessionId = manage.getSessionId();
        if (sessionId == null) {
            return;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("sessionid", sessionId);
        map.put("a", "autoLogin");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<LoginRespondBean>() {
            @Override
            public void onSuccess(LoginRespondBean result) {
                super.onSuccess(result);
                LoginBean loginBean = result.data;
                AccountManage.getInstance().setSessionId(loginBean.sessionid);
                AccountManage.getInstance().setUserId(loginBean.userid);
                ToastUtil.show("登录成功!");
                getUserInfo();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.show("登录失败!");
            }
        });

    }


    /**
     * 获取个人资料
     */
    public void getUserInfo() {
        AccountManage manage = AccountManage.getInstance();
        String sessionId = manage.getSessionId();
        if (sessionId == null) {
            return;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("sessionid", sessionId);
        map.put("a", "profile");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<UserRespondBean>() {
            @Override
            public void onSuccess(UserRespondBean result) {
                super.onSuccess(result);
                UserBean userBean = result.data;
                AccountManage.getInstance().setUserBean(userBean);
                startActivity(new Intent(SplashPage.this, HomePageActivity.class));
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });

    }


}
