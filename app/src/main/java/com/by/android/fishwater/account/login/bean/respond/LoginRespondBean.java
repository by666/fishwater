package com.by.android.fishwater.account.login.bean.respond;

import android.support.annotation.Nullable;

import com.by.android.fishwater.account.login.bean.LoginBean;
import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/10/10.
 */
@HttpResponse(parser = JsonResponseParser.class)

public class LoginRespondBean extends BaseBean{

    @Nullable
    public LoginBean data;
}
