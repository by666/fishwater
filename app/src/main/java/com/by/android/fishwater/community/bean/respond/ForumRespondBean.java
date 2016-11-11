package com.by.android.fishwater.community.bean.respond;

import com.by.android.fishwater.community.bean.ForumBean;
import com.by.android.fishwater.net.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/11/11.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class ForumRespondBean extends BaseBean{
    public List<ForumBean> data;
}
