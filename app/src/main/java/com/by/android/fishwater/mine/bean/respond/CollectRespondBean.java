package com.by.android.fishwater.mine.bean.respond;

import com.by.android.fishwater.mine.bean.CollectBean;
import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/11/5.
 */

@HttpResponse(parser = JsonResponseParser.class)
public class CollectRespondBean extends BaseBean {
    public List<CollectBean> data;
}
