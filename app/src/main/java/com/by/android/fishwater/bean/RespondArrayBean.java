package com.by.android.fishwater.bean;

import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/11/23.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class RespondArrayBean extends BaseBean{
    public List<Object> data;
}

