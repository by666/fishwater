package com.by.android.fishwater.mine.view;

import com.by.android.fishwater.mine.bean.CollectBean;

import java.util.List;

/**
 * Created by by.huang on 2016/11/5.
 */

public interface ICollectPageInterface {
    void OnGetCollectListSuccess(List<CollectBean> datas);
    void OnGetCollectListFail();
}
