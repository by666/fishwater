package com.by.android.fishwater.order.bean.respond;

import com.by.android.fishwater.net.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;
import com.by.android.fishwater.order.bean.OrderBean;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/11/7.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class OrderResondBean extends BaseBean{
    public OrderBean data;
}
