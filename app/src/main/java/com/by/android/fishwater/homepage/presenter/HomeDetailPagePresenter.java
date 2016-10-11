package com.by.android.fishwater.homepage.presenter;

import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.view.IHomeDetailPageInterface;
import com.by.android.fishwater.homepage.view.IHomePageInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/12.
 */

public class HomeDetailPagePresenter {

    private IHomeDetailPageInterface mHomeDetailPageInterface;

    public HomeDetailPagePresenter(IHomeDetailPageInterface homeDetailPageInterface)
    {
        this.mHomeDetailPageInterface = homeDetailPageInterface;
    }
}
