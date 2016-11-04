package com.by.android.fishwater.buycar.presenter;

import android.os.Bundle;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.buycar.view.IBuycarPageInterface;
import com.by.android.fishwater.order.view.GoodPage;
import com.by.android.fishwater.shopping.view.GoodsDetailPage;

/**
 * Created by by.huang on 2016/10/27.
 */

public class BuycarPresenter{


    private IBuycarPageInterface mBuycarPageInterface;

    public BuycarPresenter(IBuycarPageInterface buycarPageInterface)
    {
        this.mBuycarPageInterface = buycarPageInterface;
    }

    public void updatePrice()
    {
        if(mBuycarPageInterface!=null)
        {
            mBuycarPageInterface.OnUpdatePrice();
        }
    }


}
