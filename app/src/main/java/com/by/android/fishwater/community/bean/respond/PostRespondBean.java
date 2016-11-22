package com.by.android.fishwater.community.bean.respond;

import com.by.android.fishwater.community.bean.PostBean;
import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/11/11.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class PostRespondBean extends BaseBean{
    public List<PostBean> data;
}
