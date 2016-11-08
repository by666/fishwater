package com.by.android.fishwater.account.login.presenter;

import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.account.login.bean.LoginBean;
import com.by.android.fishwater.account.login.bean.respond.LoginRespondBean;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.login.bean.respond.UserRespondBean;
import com.by.android.fishwater.account.login.view.ILoginInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.ToastUtil;

import org.xutils.common.util.MD5;

import java.util.HashMap;

/**
 * Created by by.huang on 2016/10/10.
 */
public class LoginPresenter {


    private ILoginInterface mLoginInterface;

    public LoginPresenter(ILoginInterface loginInterface)
    {
        mLoginInterface = loginInterface;
    }


    /**
     * 登录
     * @param phoneNum
     * @param password
     */
    public void login(String phoneNum,String password)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("tel",phoneNum);
        map.put("pass", MD5.md5(password));
        map.put("a","login");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<LoginRespondBean>() {
            @Override
            public void onSuccess(LoginRespondBean result) {
                super.onSuccess(result);
                LoginBean loginBean = result.data;
                AccountManage.getInstance().setSessionId(loginBean.sessionid);
                AccountManage.getInstance().setUserId(loginBean.userid);
                ToastUtil.show("登录成功!");
                mLoginInterface.GoHomePage();
                getUserInfo();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.show("登录失败!");
            }
        });

    }
//
//    /**
//     * 自动登录
//     */
//    public void autoLogin()
//    {
//        DeviceManager.getInstance().hideInputMethod();
//
//        AccountManage manage = AccountManage.getInstance();
//        String sessionId = manage.getSessionId();
//        if(sessionId == null)
//        {
//            return;
//        }
//        HashMap<String,Object> map = new HashMap<String, Object>();
//        map.put("sessionid", sessionId);
//        map.put("a","autoLogin");
//        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<LoginRespondBean>() {
//            @Override
//            public void onSuccess(LoginRespondBean result) {
//                super.onSuccess(result);
//                LoginBean loginBean = result.data;
//                AccountManage.getInstance().setSessionId(loginBean.sessionid);
//                AccountManage.getInstance().setUserId(loginBean.userid);
//                ToastUtil.show("登录成功!");
//                getUserInfo();
//                goHomePage();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//                ToastUtil.show("登录失败!");
//            }
//        });
//
//    }

    /**
     * 登出
     */
    public void logout()
    {
        AccountManage manage = AccountManage.getInstance();
        String sessionId = manage.getSessionId();
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("sessionid", sessionId);
        map.put("a","logout");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<LoginRespondBean>() {
            @Override
            public void onSuccess(LoginRespondBean result) {
                super.onSuccess(result);
                AccountManage.getInstance().setSessionId(null);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
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
                AccountManage.getInstance().setUserBean(userBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });

    }


    /**
     * 微信方式登录
     */
    public void loginWithWechat()
    {

    }

    /**
     * qq方式登录
     */
    public void loginWithQQ()
    {

    }

    /**
     * 微博方式登录
     */
    public void loginWithWeibo()
    {

    }

    public void register()
    {
        mLoginInterface.GoRegisterPage();
    }

    public void forgetPassword()
    {
        mLoginInterface.GoForgetPassWordPage();
    }

}
