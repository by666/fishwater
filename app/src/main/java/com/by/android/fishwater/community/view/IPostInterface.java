package com.by.android.fishwater.community.view;

import com.by.android.fishwater.community.bean.PostBean;

import java.util.List;

import static u.aly.av.P;

/**
 * Created by by.huang on 2016/11/11.
 */

public interface IPostInterface {

    void OnRequestPostListSuccess(List<PostBean> datas,boolean isLoadMore,boolean theEnd);
    void OnRequestPostListFail();
    void OnPraiseSuccess(PostBean data,int praise);
    void OnPraiseFail();
}
