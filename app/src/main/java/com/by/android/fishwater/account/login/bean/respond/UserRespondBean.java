package com.by.android.fishwater.account.login.bean.respond;

import android.support.annotation.Nullable;

import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.net.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/10/10.
 */
@HttpResponse(parser = JsonResponseParser.class)

public class UserRespondBean extends BaseBean{

    @Nullable
    public UserBean data;
}
