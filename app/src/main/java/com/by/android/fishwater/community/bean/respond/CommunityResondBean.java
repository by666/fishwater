package com.by.android.fishwater.community.bean.respond;

import com.by.android.fishwater.community.bean.CommunityBean;
import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by by.huang on 2016/11/11.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class CommunityResondBean extends BaseBean{
    public CommunityBean data;
}
