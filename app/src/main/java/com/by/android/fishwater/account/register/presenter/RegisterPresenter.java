package com.by.android.fishwater.account.register.presenter;

import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.account.login.bean.LoginBean;
import com.by.android.fishwater.account.login.bean.respond.LoginRespondBean;
import com.by.android.fishwater.account.register.view.IRegisterInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.ToastUtil;

import org.xutils.common.util.MD5;

import java.util.HashMap;

/**
 * Created by by.huang on 2016/10/10.
 */
public class RegisterPresenter {


    private IRegisterInterface mRegisterInterface;

    public RegisterPresenter(IRegisterInterface registerInterface)
    {
        mRegisterInterface = registerInterface;
    }

    public void register(String phoneNum,String password,String nickname)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("tel",phoneNum);
        map.put("pass", MD5.md5(password));
        map.put("name",nickname);
        map.put("a","register");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<LoginRespondBean>() {
            @Override
            public void onSuccess(LoginRespondBean result) {
                super.onSuccess(result);
                LoginBean loginBean = result.data;
                AccountManage.getInstance().setSessionId(loginBean.sessionid);
                ToastUtil.show("注册成功!");

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.show("注册失败!");
            }
        });

    }

    public void GoLoginPage()
    {
        mRegisterInterface.GoLoginPage();
    }


}
