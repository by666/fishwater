package com.by.android.fishwater.community.presenter;

import com.by.android.fishwater.bean.BaseResondBean;
import com.by.android.fishwater.bean.ResultBean;
import com.by.android.fishwater.community.bean.ForumBean;
import com.by.android.fishwater.community.bean.PostBean;
import com.by.android.fishwater.community.bean.respond.ForumRespondBean;
import com.by.android.fishwater.community.bean.respond.PostRespondBean;
import com.by.android.fishwater.community.view.IPostInterface;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.net.BaseBean;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by by.huang on 2016/11/11.
 */

public class PostPresenter {
    private IPostInterface mPostInterface;
    private int currentPosition = 0;
    private List<PostBean> currentDatas = new ArrayList<>();

    public PostPresenter(IPostInterface postInterface) {
        this.mPostInterface = postInterface;
    }

    /**
     * 获取帖子列表new
     * @param classid
     */
    public void getNewPostList(int classid)
    {
        currentPosition = 0;
        currentDatas.removeAll(currentDatas);
        getPostList(classid,false);
    }

    /**
     * 获取帖子列表
     * @param classid
     * @param isLoadMore
     */
    public void getPostList(int classid,final boolean isLoadMore) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "postsList");
        map.put("classid", classid);
        HttpRequest.Post(Constant.ForumUrl, map, new MyCallBack<PostRespondBean>() {
            @Override
            public void onSuccess(PostRespondBean result) {
                super.onSuccess(result);
                List<PostBean> datas = result.data;

                currentPosition += datas.size();
                if(datas.size() == 0) {
                    mPostInterface.OnRequestPostListSuccess(currentDatas,isLoadMore,true);
                }
                else {
                    currentDatas.addAll(datas);
                    mPostInterface.OnRequestPostListSuccess(currentDatas,isLoadMore,false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mPostInterface.OnRequestPostListFail();
            }
        });
    }

    public void praise(final PostBean data)
    {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "praise");
        map.put("typeid", 3);
        map.put("targetid",data.id);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<BaseResondBean>() {
            @Override
            public void onSuccess(BaseResondBean result) {
                super.onSuccess(result);
                ResultBean resultBean = (ResultBean) result.data;
                ToastUtil.show(result.msg);
                if(resultBean.result == 1) {
                    mPostInterface.OnPraiseSuccess(data,1);
                }
                else {
                    mPostInterface.OnPraiseSuccess(data,0);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mPostInterface.OnPraiseFail();
            }
        });
    }
}
