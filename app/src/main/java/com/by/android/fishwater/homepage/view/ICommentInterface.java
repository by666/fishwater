package com.by.android.fishwater.homepage.view;

import com.by.android.fishwater.homepage.bean.CommentBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;

import java.util.List;

/**
 * Created by by.huang on 2016/11/6.
 */

public interface ICommentInterface {

    void requestListDataSuccess(List<CommentBean> datas, boolean isLoadMore, boolean theEnd);

    void requestListDataFail();
}
