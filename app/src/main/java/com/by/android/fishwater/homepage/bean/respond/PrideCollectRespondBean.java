package com.by.android.fishwater.homepage.bean.respond;

import com.by.android.fishwater.homepage.bean.PrideCollectBean;
import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/11/7.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class PrideCollectRespondBean extends BaseBean{
    public PrideCollectBean data;
}
