package com.by.android.fishwater.shopping.view;

import com.by.android.fishwater.shopping.bean.GoodsDetailBean;

/**
 * Created by by.huang on 2016/10/24.
 */

public interface IGoodsDetailInterface {
    void OnRequstDetailSuccess(GoodsDetailBean data);
    void OnRequestDetailFail();
}
