package com.by.android.fishwater;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.account.login.bean.LoginBean;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.login.bean.respond.LoginRespondBean;
import com.by.android.fishwater.account.login.bean.respond.UserRespondBean;
import com.by.android.fishwater.buycar.BuycarPage;
import com.by.android.fishwater.community.CommunityPage;
import com.by.android.fishwater.homepage.view.HomePage;
import com.by.android.fishwater.mine.MinePage;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.shopping.view.ShoppingPage;
import com.by.android.fishwater.splash.SplashPage;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.DeviceManager;
import com.by.android.fishwater.util.ToastUtil;

import java.util.HashMap;

/**
 * Created by by.huang on 2016/10/10.
 */
public class FWPresenter {
    private FishWaterActivity mActivity;
    private static FWPresenter mInstance;
    private Fragment mCurrentFrament;

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
        this.mActivity = (FishWaterActivity) activity;
    }

    //初始化视图
    public void setDefaultFragment()
    {
        SplashPage splashPage = new SplashPage();
        replaceFragment(splashPage);
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layout_content, fragment);
        transaction.commit();
    }

    public void addFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_enter,R.anim.slide_left_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
        transaction.replace(R.id.layout_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void backLastFragment()
    {
        mActivity.getSupportFragmentManager().popBackStack();
    }

    public void removeFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void showFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    public void hideFragment(Fragment fragment)
    {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

    /**
     * 显示tab栏
     * @param visiable
     */
    public void showTabLayout(int visiable)
    {
        mActivity.showTab(visiable);
    }

    /**
     * 自动登录
     */
    public void autoLogin()
    {
        DeviceManager.getInstance().hideInputMethod();

        AccountManage manage = AccountManage.getInstance();
        String sessionId = manage.getSessionId();
        if(sessionId == null)
        {
            return;
        }
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("sessionid", sessionId);
        map.put("a","autoLogin");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<LoginRespondBean>() {
            @Override
            public void onSuccess(LoginRespondBean result) {
                super.onSuccess(result);
                LoginBean loginBean = result.data;
                AccountManage.getInstance().setSessionId(loginBean.sessionid);
                AccountManage.getInstance().setUserId(loginBean.userid);
                ToastUtil.show("登录成功!");
                goHomePage();
                getUserInfo();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.show("登录失败!");
                goHomePage();
                getUserInfo();
            }
        });

    }


    /**
     * 获取个人资料
     */
    public void getUserInfo()
    {
        AccountManage manage = AccountManage.getInstance();
        String sessionId = manage.getSessionId();
        if(sessionId == null)
        {
            return;
        }
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("sessionid", sessionId);
        map.put("a","profile");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<UserRespondBean>() {
            @Override
            public void onSuccess(UserRespondBean result) {
                super.onSuccess(result);
                UserBean userBean = result.data;
                goHomePage();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });

    }

    /**
     * 跳转到主页
     */
    public void goHomePage()
    {
        HomePage page = new HomePage();
        replaceFragment(page);
    }

    /**
     * 跳转到社区
     */
    public void goCommunityPage()
    {
        CommunityPage page = new CommunityPage();
        replaceFragment(page);
    }
    /**
     * 跳转到商城
     */
    public void goShoppingPage()
    {
        ShoppingPage page = new ShoppingPage();
        replaceFragment(page);
    }

    /**
     * 跳转到购物车
     */
    public void goBuycarPage()
    {
        BuycarPage page = new BuycarPage();
        replaceFragment(page);
    }

    /**
     * 跳转到个人
     */
    public void goMinePage()
    {
        MinePage page = new MinePage();
        replaceFragment(page);
    }
}
