package com.by.android.fishwater.shopping.bean.respond;

import com.by.android.fishwater.bean.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;
import com.by.android.fishwater.shopping.bean.CategoryBean;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by by.huang on 2016/10/13.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class CategoryRespondBean extends BaseBean{

    public List<CategoryBean> data;

}
