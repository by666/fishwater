package com.by.android.fishwater.homepage.bean.respond;

import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/10/11.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class HomeListRespondBean extends BaseBean{

    public List<HomeListBean> data;
}
