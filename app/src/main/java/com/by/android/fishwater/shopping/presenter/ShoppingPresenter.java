package com.by.android.fishwater.shopping.presenter;

import android.os.Bundle;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.bean.BannerRespondBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.bean.respond.HomeListRespondBean;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.shopping.bean.CategoryBean;
import com.by.android.fishwater.shopping.bean.GoodsBean;
import com.by.android.fishwater.shopping.bean.respond.CategoryRespondBean;
import com.by.android.fishwater.shopping.bean.respond.GoodsRespondBean;
import com.by.android.fishwater.shopping.view.IShoppingPageInterface;
import com.by.android.fishwater.shopping.view.ShoppingSearchPage;
import com.by.android.fishwater.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.category;

/**
 * Created by by.huang on 2016/10/13.
 */

public class ShoppingPresenter {
    private IShoppingPageInterface mShoppingPageInterface;
    private int currentPosition = 0;
    private List<GoodsBean> currentDatas = new ArrayList<>();

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

    /**
     * 获取分类
     */
    public void getGoodsCategoryList()
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","class");
        HttpRequest.Post(Constant.DictUrl, map, new MyCallBack<CategoryRespondBean>() {
            @Override
            public void onSuccess(CategoryRespondBean result) {
                super.onSuccess(result);
                List<CategoryBean> datas = result.data;
                mShoppingPageInterface.OnCategorySuccess(datas);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mShoppingPageInterface.OnCategoryFail();
            }
        });
    }


    /**
     * 重新拉去列表
     * @param key
     * @param category
     */
    public void getNewGoodsListData(int key,int category)
    {
        currentPosition = 0;
        currentDatas.removeAll(currentDatas);
        getGoodListData(false,key,category);
    }


    /**
     * 列表加载更多
     * @param isLoadMore
     * @param key
     * @param category
     */
    public void getGoodListData(final boolean isLoadMore,int key,int category)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","goodsList");
        map.put("da","recommend");
        if(key!= -1) {
            map.put("key",key);
        }
        if(category != -1) {
            map.put("class",category);
        }
        map.put("begin",currentPosition);
        HttpRequest.Post(Constant.GoodsUrl, map, new MyCallBack<GoodsRespondBean>() {
            @Override
            public void onSuccess(GoodsRespondBean result) {
                super.onSuccess(result);
                List<GoodsBean> datas = result.data;
                currentPosition += datas.size();
                if(datas.size() == 0) {
                    mShoppingPageInterface.OnGoodsSuccess(currentDatas,isLoadMore,true);
                }
                else {
                    currentDatas.addAll(datas);
                    mShoppingPageInterface.OnGoodsSuccess(currentDatas,isLoadMore,false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mShoppingPageInterface.OnGoodsFail();
            }
        });
    }

    /**
     * 跳转到分类列表
     * @param data
     */
    public void goShoppingSearchPage(CategoryBean data)
    {
        ShoppingSearchPage page = new ShoppingSearchPage();
        Bundle bundle = new Bundle();
        bundle.putInt("category",data.id);
        bundle.putString("title",data.title);
        page.setArguments(bundle);
        FWPresenter.getInstance().addFragment(page);
    }
}
