package com.by.android.fishwater.mine.bean.respond;

import com.by.android.fishwater.mine.bean.FansFollowerBean;
import com.by.android.fishwater.net.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/11/7.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class FansFollowerRespondBean extends BaseBean{

    public List<FansFollowerBean> data;
}
