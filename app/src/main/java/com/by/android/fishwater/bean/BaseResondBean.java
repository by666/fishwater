package com.by.android.fishwater.bean;

import android.support.annotation.Nullable;

import com.by.android.fishwater.net.BaseBean;
import com.by.android.fishwater.net.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by by.huang on 2016/11/3.
 */

@HttpResponse(parser = JsonResponseParser.class)

public class BaseResondBean extends BaseBean{
    @Nullable public ResultBean data;
}
