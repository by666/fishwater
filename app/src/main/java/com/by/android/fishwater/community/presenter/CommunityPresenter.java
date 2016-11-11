package com.by.android.fishwater.community.presenter;

import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.bean.BannerRespondBean;
import com.by.android.fishwater.community.bean.CommunityBean;
import com.by.android.fishwater.community.bean.ForumBean;
import com.by.android.fishwater.community.bean.respond.CommunityResondBean;
import com.by.android.fishwater.community.bean.respond.ForumRespondBean;
import com.by.android.fishwater.community.view.ICommunityInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;

import java.util.HashMap;
import java.util.List;

/**
 * Created by by.huang on 2016/11/10.
 */

public class CommunityPresenter {

    private ICommunityInterface mICommunityInterface;

    public CommunityPresenter(ICommunityInterface commubityInterface) {
        this.mICommunityInterface = commubityInterface;
    }

    /**
     * 获取banner图片
     */
    public void getBannerListData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "slideshowList");
        map.put("position", 1);
        HttpRequest.Post(Constant.HomeUrl, map, new MyCallBack<BannerRespondBean>() {
            @Override
            public void onSuccess(BannerRespondBean result) {
                super.onSuccess(result);
                List<BannerBean> datas = result.data;
                if (mICommunityInterface != null) {
                    mICommunityInterface.requestPagerDataSuccess(datas);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if (mICommunityInterface != null) {
                    mICommunityInterface.requestPagerDataFail();
                }
            }
        });
    }

    /**
     * 获取我的圈子，推荐圈子，推荐帖子
     */
    public void getCircle() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "forumRecommend");
        HttpRequest.Post(Constant.ForumUrl, map, new MyCallBack<CommunityResondBean>() {
            @Override
            public void onSuccess(CommunityResondBean result) {
                super.onSuccess(result);
                CommunityBean communityBean = result.data;
                mICommunityInterface.requestCircleSuccess(communityBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if (mICommunityInterface != null) {
                    mICommunityInterface.requestCircleFail();
                }
            }
        });
    }

    /**
     * 获取所有圈子分类
     */
    public void getForum() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "circle");
        HttpRequest.Post(Constant.ForumUrl, map, new MyCallBack<ForumRespondBean>() {
            @Override
            public void onSuccess(ForumRespondBean result) {
                super.onSuccess(result);
                List<ForumBean> datas = result.data;
                mICommunityInterface.requestForumSuccess(datas);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if (mICommunityInterface != null) {
                    mICommunityInterface.requestForumFail();
                }
            }
        });
    }


}
