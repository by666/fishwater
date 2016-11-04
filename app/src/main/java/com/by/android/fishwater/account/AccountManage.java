package com.by.android.fishwater.account;

import android.content.Context;
import android.content.SharedPreferences;

import com.by.android.fishwater.FWApplication;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.util.Constant;

/**
 * Created by by.huang on 2016/10/10.
 */
public class AccountManage {

    private static AccountManage mInstance;
    private SharedPreferences mSharedPreferences;
    private UserBean mUserBean;

    public static AccountManage getInstance() {
        if (mInstance == null) {
            synchronized (AccountManage.class) {
                if (mInstance == null) {
                    mInstance = new AccountManage();
                }
            }
        }
        return mInstance;
    }

    public AccountManage() {
        mSharedPreferences = FWApplication.mApplication.getSharedPreferences(Constant.PREFERENCE_ACCOUNT, Context.MODE_PRIVATE);
    }

    public String getSessionId() {
        String sessionid = mSharedPreferences.getString(Constant.PREFERENCE_SESSIONID, null);
        return sessionid;
    }

    public void setSessionId(String sessionId) {
        mSharedPreferences.edit().putString(Constant.PREFERENCE_SESSIONID, sessionId).commit();
    }

    public String getUserId() {
        String userid = mSharedPreferences.getString(Constant.PREFERENCE_USERID, null);
        return userid;
    }

    public void setUserId(String userId) {
        mSharedPreferences.edit().putString(Constant.PREFERENCE_USERID, userId).commit();
    }

    public void setUserBean(UserBean userBean) {
        this.mUserBean = userBean;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }


    public boolean isLogin() {
        if (mUserBean != null) {
            return true;
        }
        return false;
    }
}
