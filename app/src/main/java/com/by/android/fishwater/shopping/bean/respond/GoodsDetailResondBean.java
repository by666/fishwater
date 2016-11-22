package com.by.android.fishwater.shopping.bean.respond;

import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;
import com.by.android.fishwater.shopping.bean.GoodsDetailBean;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/10/24.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class GoodsDetailResondBean extends BaseBean{
    public GoodsDetailBean data;
}
