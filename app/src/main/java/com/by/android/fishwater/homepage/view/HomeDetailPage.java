package com.by.android.fishwater.homepage.view;

import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.presenter.HomeDetailPagePresenter;
import com.by.android.fishwater.util.Constant;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by by.huang on 2016/10/12.
 */

@ContentView(R.layout.page_homedetail)
public class HomeDetailPage extends Fragment implements IHomeDetailPageInterface{

    private HomeDetailPagePresenter mHomeDetailPagePresenter;
    public HomeListBean data;

    @ViewInject(R.id.webview)
    WebView mWebView;

    @ViewInject(R.id.img_back)
    ImageView mBackImg;

    @ViewInject(R.id.img_pride)
    ImageView mPrideImg;

    @ViewInject(R.id.img_collect)
    ImageView mCollectImg;

    @ViewInject(R.id.img_msg)
    ImageView mMsgImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomeDetailPagePresenter = new HomeDetailPagePresenter(this);
        initView();
        FWPresenter.getInstance().showTabLayout(View.GONE);
    }

    private void initView() {

        mWebView.requestFocusFromTouch();

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(false);


        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAllowFileAccess(true);
        webSettings.setNeedInitialFocus(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        String url = Constant.HomeUrl + "?" + "a=contentDetail&id="+data.targetID;
        mWebView.loadUrl(url);


        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FWPresenter.getInstance().backLastFragment();
            }
        });

        mPrideImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCollectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mMsgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
