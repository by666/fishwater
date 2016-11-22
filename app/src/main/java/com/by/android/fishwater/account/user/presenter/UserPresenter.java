package com.by.android.fishwater.account.user.presenter;

import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.login.bean.respond.UserRespondBean;
import com.by.android.fishwater.account.user.view.IUserPageInterface;
import com.by.android.fishwater.bean.RespondObjBean;
import com.by.android.fishwater.bean.ResultBean;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.ToastUtil;

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
        map.put("userid", id);
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

    public void attendUser(int id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("targetid", id);
        map.put("a", "followUser");
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<RespondObjBean>() {
            @Override
            public void onSuccess(RespondObjBean result) {
                super.onSuccess(result);
                ResultBean data = (ResultBean) result.data;
                ToastUtil.show(result.msg);
                if (data.result == 1) {
                    mUserPageInterface.OnAttendUserSuccess(true);
                }else {
                    mUserPageInterface.OnAttendUserSuccess(false);
                }
                mUserPageInterface.OnAttendUserFail();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mUserPageInterface.OnAttendUserFail();
            }
        });
    }
}
