package com.by.android.fishwater.order.bean.respond;

import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;
import com.by.android.fishwater.order.bean.AddressBean;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class AddressRespondBean extends BaseBean{

    public List<AddressBean> data;
}
