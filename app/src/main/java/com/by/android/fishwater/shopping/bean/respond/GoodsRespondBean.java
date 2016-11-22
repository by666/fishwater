package com.by.android.fishwater.shopping.bean.respond;

import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;
import com.by.android.fishwater.shopping.bean.GoodsBean;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/10/24.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class GoodsRespondBean extends BaseBean{
    public List<GoodsBean> data;
}
