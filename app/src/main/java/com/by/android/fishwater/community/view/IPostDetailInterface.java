package com.by.android.fishwater.community.view;

import com.by.android.fishwater.community.bean.PostDetailBean;

/**
 * Created by by.huang on 2016/11/12.
 */

public interface IPostDetailInterface {

    void OnRequestPostDetailSuccess(PostDetailBean data);
    void OnRequestPostDetailFail();
    void OnSendCommentSuccess();
    void OnSendCommentFail();
}
