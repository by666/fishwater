package com.by.android.fishwater.bean;

import com.by.android.fishwater.net.JsonResponseParser;
import com.google.gson.annotations.SerializedName;

import org.xutils.http.annotation.HttpResponse;

import static android.R.attr.data;
import static u.aly.av.T;

/**
 * Created by by.huang on 2016/11/3.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class RespondObjBean extends BaseBean{
    public ResultBean data;
}
