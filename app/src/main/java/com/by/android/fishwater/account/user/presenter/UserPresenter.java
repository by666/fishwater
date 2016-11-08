package com.by.android.fishwater.account.user.presenter;

import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.login.bean.respond.UserRespondBean;
import com.by.android.fishwater.account.user.view.IUserPageInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;

import java.util.HashMap;

/**
 * Created by by.huang on 2016/11/8.
 */

public class UserPresenter {


    private IUserPageInterface mUserPageInterface;

    public UserPresenter(IUserPageInterface userPageInterface) {
        this.mUserPageInterface = userPageInterface;
    }

    /**
     * 获取他人资料
     *
     * @param id
     */
    public void getOtherUserInfo(int id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userid", 1);
        map.put("a", "profileByUserid");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<UserRespondBean>() {
            @Override
            public void onSuccess(UserRespondBean result) {
                super.onSuccess(result);
                UserBean data = result.data;
                if (data == null) {
                    mUserPageInterface.OnRequestUserinfoFail();
                    return;
                }
                mUserPageInterface.OnRequestUserinfoSuccess(data);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mUserPageInterface.OnRequestUserinfoFail();
            }
        });
    }
}
