package com.by.android.fishwater.homepage.view;

import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;

import java.util.List;

/**
 * Created by by.huang on 2016/10/9.
 */
public interface IHomePageInterface {

    void requestListDataSuccess(List<HomeListBean> datas,boolean isLoadMore,boolean theEnd);

    void requestListDataFail();

    void requestPagerDataSuccess(List<BannerBean> datas);

    void requestPagerDataFail();
}
