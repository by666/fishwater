package com.by.android.fishwater.homepage.presenter;

import com.by.android.fishwater.homepage.bean.CommentBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.bean.respond.CommentRespondBean;
import com.by.android.fishwater.homepage.bean.respond.HomeListRespondBean;
import com.by.android.fishwater.homepage.view.ICommentInterface;
import com.by.android.fishwater.homepage.view.IHomePageInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by by.huang on 2016/11/6.
 */

public class CommentPresenter {

    private ICommentInterface mCommentInterface;
    private int currentPosition = 0;
    private List<CommentBean> currentDatas = new ArrayList<>();

    public CommentPresenter(ICommentInterface commentInterface)
    {
        this.mCommentInterface = commentInterface;
    }


    public void getNewHomeListData(HomeListBean data)
    {
        currentPosition = 0;
        currentDatas.removeAll(currentDatas);
        getHomeListData(data,false);
    }

    public void getHomeListData(HomeListBean data,final boolean isLoadMore)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","commentList");
        map.put("typeid",data.typeID);
        map.put("targetid",data.targetID);
        map.put("begin",currentPosition);

        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<CommentRespondBean>() {
            @Override
            public void onSuccess(CommentRespondBean result) {
                super.onSuccess(result);
                List<CommentBean> datas = result.data;
                currentPosition += datas.size();
                if(datas.size() == 0) {
                    mCommentInterface.requestListDataSuccess(currentDatas,isLoadMore,true);
                }
                else {
                    currentDatas.addAll(datas);
                    mCommentInterface.requestListDataSuccess(currentDatas,isLoadMore,false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mCommentInterface.requestListDataFail();
            }
        });
    }

}
