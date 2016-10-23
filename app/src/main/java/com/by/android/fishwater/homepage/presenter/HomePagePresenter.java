package com.by.android.fishwater.homepage.presenter;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.bean.BannerRespondBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.bean.respond.HomeListRespondBean;
import com.by.android.fishwater.homepage.view.HomeDetailPage;
import com.by.android.fishwater.homepage.view.IHomePageInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by by.huang on 2016/10/9.
 */
public class HomePagePresenter {


    private IHomePageInterface mHomePageInterface;
    private int currentPosition = 0;
    private List<HomeListBean> currentDatas = new ArrayList<>();

    public HomePagePresenter(IHomePageInterface homePageInterface)
    {
        this.mHomePageInterface = homePageInterface;
    }

    /**
     * 获取banner图片
     */
    public void getBannerListData()
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","slideshowList");
        map.put("position",1);
        HttpRequest.Post(Constant.HomeUrl, map, new MyCallBack<BannerRespondBean>() {
            @Override
            public void onSuccess(BannerRespondBean result) {
                super.onSuccess(result);
                List<BannerBean> datas = result.data;
                mHomePageInterface.requestPagerDataSuccess(datas);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mHomePageInterface.requestPagerDataFail();
            }
        });
    }

    public void getNewHomeListData()
    {
        currentPosition = 0;
        currentDatas.removeAll(currentDatas);
        getHomeListData(false);
    }

    public void getHomeListData(final boolean isLoadMore)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","contentList");
        map.put("begin",currentPosition);
        HttpRequest.Post(Constant.HomeUrl, map, new MyCallBack<HomeListRespondBean>() {
            @Override
            public void onSuccess(HomeListRespondBean result) {
                super.onSuccess(result);
                List<HomeListBean> datas = result.data;
                currentPosition += datas.size();
                if(datas.size() == 0) {
                    mHomePageInterface.requestListDataSuccess(currentDatas,isLoadMore,true);
                }
                else {
                    currentDatas.addAll(datas);
                    mHomePageInterface.requestListDataSuccess(currentDatas,isLoadMore,false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mHomePageInterface.requestListDataFail();
            }
        });
    }


    public void goHomeDetailPresenter(HomeListBean data)
    {
        HomeDetailPage page = new HomeDetailPage();
        page.data = data;
        FWPresenter.getInstance().addFragment(page);
    }
}
