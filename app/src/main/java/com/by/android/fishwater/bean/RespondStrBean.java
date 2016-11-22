package com.by.android.fishwater.bean;

import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/11/22.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class RespondStrBean extends BaseBean{
    public String data;
}
