package com.by.android.fishwater.bean;

import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/10/12.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class BannerRespondBean extends BaseBean{
    public List<BannerBean> data;

}
