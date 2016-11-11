package com.by.android.fishwater.account.user.view;

import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.mine.bean.FansFollowerBean;

import java.util.List;

/**
 * Created by by.huang on 2016/11/8.
 */

public interface IUserPageInterface {
    void OnRequestUserinfoSuccess(UserBean data);
    void OnRequestUserinfoFail();
    void OnAttendUserSuccess(boolean isAttend);
    void OnAttendUserFail();

}
