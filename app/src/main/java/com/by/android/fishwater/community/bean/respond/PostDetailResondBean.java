package com.by.android.fishwater.community.bean.respond;

import com.by.android.fishwater.community.bean.PostDetailBean;
import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/11/12.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class PostDetailResondBean extends BaseBean{
    public PostDetailBean data;
}
