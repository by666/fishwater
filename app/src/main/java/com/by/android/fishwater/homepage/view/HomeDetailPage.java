package com.by.android.fishwater.homepage.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.presenter.HomeDetailPagePresenter;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by by.huang on 2016/10/12.
 */

public class HomeDetailPage extends FWActivity implements IHomeDetailInterface{

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

    private HomeDetailPagePresenter mHomeDetailPagePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_homedetail);
        x.view().inject(this);
        data = (HomeListBean) getIntent().getSerializableExtra("homelistbean");
        mHomeDetailPagePresenter = new HomeDetailPagePresenter(this);
        initView();

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
                finish();
            }
        });

        mPrideImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHomeDetailPagePresenter.requsetPride(data);
            }
        });

        mCollectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeDetailPagePresenter.requsetCollect(data);

            }
        });

        mMsgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeDetailPage.this,CommentPage.class);
                intent.putExtra("homelistbean",data);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnRequestPrideSuccess() {
        ToastUtil.show("点赞成功！");
        mPrideImg.setImageDrawable(getResources().getDrawable(R.drawable.home_wenzhang_zan));
    }

    @Override
    public void OnRequestPrideFail() {
        ToastUtil.show("点赞失败！");
    }

    @Override
    public void OnRequestCollectSuccess() {
        ToastUtil.show("收藏成功！");
        mCollectImg.setImageDrawable(getResources().getDrawable(R.drawable.home_wenzhang_collect));

    }

    @Override
    public void OnRequestCollectFail() {
        ToastUtil.show("收藏失败！");
    }
}
