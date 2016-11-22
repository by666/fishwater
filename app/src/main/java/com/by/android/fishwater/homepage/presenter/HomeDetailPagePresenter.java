package com.by.android.fishwater.homepage.presenter;

import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.bean.PrideCollectBean;
import com.by.android.fishwater.homepage.bean.respond.PrideCollectRespondBean;
import com.by.android.fishwater.homepage.view.IHomeDetailInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;

import java.util.HashMap;

/**
 * Created by by.huang on 2016/11/7.
 */

public class HomeDetailPagePresenter {

    private IHomeDetailInterface mHomeDetailInterface;

    public HomeDetailPagePresenter(IHomeDetailInterface homeDetailInterface) {
        this.mHomeDetailInterface = homeDetailInterface;
    }

    public void requsetPride(HomeListBean data) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "praise");
        map.put("typeid", data.typeID);
        map.put("targetid", data.targetID);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<PrideCollectRespondBean>() {
            @Override
            public void onSuccess(PrideCollectRespondBean result) {
                super.onSuccess(result);
                PrideCollectBean data = result.data;
                if (data.result == PrideCollectBean.Handle_Success) {
                    mHomeDetailInterface.OnRequestPrideSuccess();
                } else {
                    mHomeDetailInterface.OnRequestPrideFail();

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mHomeDetailInterface.OnRequestPrideFail();
            }
        });
    }

    public void requsetCollect(HomeListBean data) {
//      cancel  1-允许取消收藏、0-不允许取消收藏 默认为0
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "collect");
        map.put("typeid", data.typeID);
        map.put("targetid", data.targetID);
        map.put("cancel", 1);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<PrideCollectRespondBean>() {
            @Override
            public void onSuccess(PrideCollectRespondBean result) {
                super.onSuccess(result);
                PrideCollectBean data = result.data;
                if (data.result == PrideCollectBean.Handle_Success) {
                    mHomeDetailInterface.OnRequestCollectSuccess();
                } else {
                    mHomeDetailInterface.OnRequestCollectFail();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mHomeDetailInterface.OnRequestCollectFail();
            }
        });
    }
}
