package com.by.android.fishwater.order.bean.respond;

import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;
import com.by.android.fishwater.order.bean.OrderDetailBean;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/11/8.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class OrderDetailRespondBean extends BaseBean{
    public List<OrderDetailBean> data;
}
