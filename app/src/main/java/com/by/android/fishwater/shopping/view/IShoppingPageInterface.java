package com.by.android.fishwater.shopping.view;

import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.shopping.bean.CategoryBean;
import com.by.android.fishwater.shopping.bean.GoodsBean;

import java.util.List;

/**
 * Created by by.huang on 2016/10/13.
 */

public interface IShoppingPageInterface {

    void OnBannerSuccess(List<BannerBean> datas);
    void OnBannerFail();

    void OnCategorySuccess(List<CategoryBean> datas);
    void OnCategoryFail();

    void OnGoodsSuccess(List<GoodsBean> datas, boolean isLoadMore, boolean theEnd);
    void OnGoodsFail();

}
