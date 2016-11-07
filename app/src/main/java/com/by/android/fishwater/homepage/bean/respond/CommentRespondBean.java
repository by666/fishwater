package com.by.android.fishwater.homepage.bean.respond;

import com.by.android.fishwater.homepage.bean.CommentBean;
import com.by.android.fishwater.net.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/11/6.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class CommentRespondBean extends BaseBean{
    public List<CommentBean> data;

}


