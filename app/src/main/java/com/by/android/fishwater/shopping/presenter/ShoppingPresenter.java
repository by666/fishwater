package com.by.android.fishwater.shopping.presenter;

import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.bean.BannerRespondBean;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.shopping.view.IShoppingPageInterface;
import com.by.android.fishwater.util.Constant;

import java.util.HashMap;
import java.util.List;

/**
 * Created by by.huang on 2016/10/13.
 */

public class ShoppingPresenter {
    private IShoppingPageInterface mShoppingPageInterface;

    public ShoppingPresenter(IShoppingPageInterface shoppingPageInterface)
    {
        this.mShoppingPageInterface = shoppingPageInterface;
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
                mShoppingPageInterface.OnBannerSuccess(datas);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mShoppingPageInterface.OnBannerFail();
            }
        });
    }
}
