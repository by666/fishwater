package com.by.android.fishwater.community.view;

import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.community.bean.CommunityBean;
import com.by.android.fishwater.community.bean.ForumBean;

import java.util.List;

/**
 * Created by by.huang on 2016/11/10.
 */

public interface ICommunityInterface {

    void requestPagerDataSuccess(List<BannerBean> datas);

    void requestPagerDataFail();

    void requestCircleSuccess(CommunityBean data);

    void requestCircleFail();

    void requestForumSuccess(List<ForumBean> datas);

    void requestForumFail();
}
