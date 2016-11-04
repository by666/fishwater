package com.by.android.fishwater.shopping.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.buycar.view.BuycarPage;
import com.by.android.fishwater.database.FWDatabaseManager;
import com.by.android.fishwater.homepage.adapter.HomePageViewPagerAdapter;
import com.by.android.fishwater.order.view.GoodPage;
import com.by.android.fishwater.shopping.adapter.GoodsTagsAdapter;
import com.by.android.fishwater.shopping.bean.GoodsDetailBean;
import com.by.android.fishwater.shopping.presenter.GoodsDetailPresenter;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.ToastUtil;
import com.by.android.fishwater.widget.PriceView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.by.android.fishwater.R.drawable.buy_panel_buy_car_text;


/**
 * Created by by.huang on 2016/10/24.
 */

public class GoodsDetailPage extends FWActivity implements IGoodsDetailInterface {

    private GoodsDetailPresenter mGoodsDetailPresenter;
    private Button mConfirmBtn;
    private boolean mAddOrBuy;

    @ViewInject(R.id.webview)
    WebView mWebView;

    @ViewInject(R.id.img_back)
    ImageView mBackImg;

    @ViewInject(R.id.img_buycar)
    ImageView mBuycarImg;

    @ViewInject(R.id.img_share)
    ImageView mShareImg;

    @ViewInject(R.id.viewpager)
    ViewPager mViewPager;

    @ViewInject(R.id.layout_point)
    LinearLayout mPointLayout;

    @ViewInject(R.id.txt_title)
    TextView mTitleTxt;

    @ViewInject(R.id.txt_price)
    PriceView mPriceTxt;

    @ViewInject(R.id.txt_oprice)
    PriceView mOpriceTxt;

    @ViewInject(R.id.txt_salecount)
    TextView mSaleCountTxt;

    @ViewInject(R.id.recyclerview_tag)
    RecyclerView mTagRecyclerView;

    @ViewInject(R.id.btn_buy)
    Button mBuyBtn;

    @ViewInject(R.id.btn_add)
    Button mAddBtn;

    @ViewInject(R.id.btn_collect)
    Button mCollectBtn;

    @ViewInject(R.id.countview)
    View mCountView;

    @ViewInject(R.id.txt_buycar_count)
    Button mBuycarCount;

    private List<ImageView> mImageViews = new ArrayList<>();

    private GoodsDetailBean mData;

    private List<BannerBean> mBannerDatas = new ArrayList<>();


    private int mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_goodsdetail);
        x.view().inject(this);
        mId = getIntent().getIntExtra("id", 0);
        mGoodsDetailPresenter = new GoodsDetailPresenter(this, this);
        initView();
    }

    private void initView() {
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBuycarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsDetailPage.this, BuycarPage.class);
                startActivity(intent);
            }
        });
        mShareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsDetailPresenter.showSelectCount(mCountView);
                mAddOrBuy = true;
                mConfirmBtn.setBackgroundResource(R.drawable.buy_panel_buy_car_text);
                mConfirmBtn.setText("加入购物车");
            }
        });
        mBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsDetailPresenter.showSelectCount(mCountView);
                mAddOrBuy = false;
                mConfirmBtn.setBackgroundResource(R.drawable.buy_panel_buy_text);
                mConfirmBtn.setText("立即购买");
            }
        });
        initWebView();
        mGoodsDetailPresenter.getGoodsDetail(mId);
    }

    private void initWebView() {
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

        String url = Constant.GoodsUrl + "?" + "a=goodsDetail&id=" + mId;
        mWebView.loadUrl(url);
    }


    private void initViewPager() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mImageViews != null && mImageViews.size() > 0) {
                    for (ImageView imageView : mImageViews) {
                        imageView.setImageResource(R.drawable.ic_dot_don);
                    }
                    mImageViews.get(position).setImageResource(R.drawable.ic_dot_on);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        RelativeLayout.LayoutParams pointParams = new RelativeLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen
                .space_30));
        pointParams.topMargin = getResources().getDimensionPixelSize(R.dimen
                .space_150);
        mPointLayout.setLayoutParams(pointParams);

        int size = getResources().getDimensionPixelSize(R.dimen.image_cycle_view_indicator_item_size);
        int gap = getResources().getDimensionPixelSize(R.dimen.image_cycle_view_indicator_item_gap);
        if (mBannerDatas != null && mBannerDatas.size() > 0) {
            for (int i = 0; i < mBannerDatas.size(); i++) {
                ImageView pointImg = new ImageView(this);
                pointImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams imgParam = new LinearLayout.LayoutParams(size, size);
                imgParam.leftMargin = gap;
                pointImg.setTag(i);
                if (i == 0) {
                    pointImg.setImageResource(R.drawable.ic_dot_on);
                } else {
                    pointImg.setImageResource(R.drawable.ic_dot_don);
                }
                pointImg.setLayoutParams(imgParam);
                mPointLayout.addView(pointImg);
                mImageViews.add(pointImg);
            }
        } else {
            mPointLayout.setVisibility(View.GONE);
        }

        List<View> views = new ArrayList<>();
        if (mBannerDatas != null && mBannerDatas.size() > 0) {
            for (BannerBean data : mBannerDatas) {
                SimpleDraweeView showImg = (SimpleDraweeView) LayoutInflater.from(this).inflate(R.layout.item_banner, null);
                showImg.setImageURI(Uri.parse(data.url));
                views.add(showImg);
            }
            mViewPager.setAdapter(new HomePageViewPagerAdapter(views));
        }
    }

    private void initGoodDetail() {
        mTitleTxt.setText(mData.name);
        mPriceTxt.setText(mData.price + "");
        mOpriceTxt.setText(mData.oprice + "");
        mSaleCountTxt.setText("已有" + mData.sales + "人购买");
        updateBuycarCount();
    }

    private void initBuycarDetail() {
        SimpleDraweeView showImg = (SimpleDraweeView) mCountView.findViewById(R.id.img_show);
        showImg.setImageURI(Uri.parse(mData.thumbnails));

        PriceView priceTxt = (PriceView) mCountView.findViewById(R.id.txt_price);
        priceTxt.setText(mData.price + "");

        TextView stockTxt = (TextView) mCountView.findViewById(R.id.txt_stock);
        stockTxt.setText("库存：9999");

        TextView selectTxt = (TextView) mCountView.findViewById(R.id.txt_select);
        selectTxt.setText("已选：\"黑色\"");

        Button closeBtn = (Button) mCountView.findViewById(R.id.btn_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsDetailPresenter.hideSelectCount(mCountView);
            }
        });

        final TextView numTxt = (TextView) mCountView.findViewById(R.id.txt_num);
        numTxt.setText("1");

        Button addBtn = (Button) mCountView.findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(numTxt.getText().toString());
                num++;
                numTxt.setText(num + "");
            }
        });

        Button reduceBtn = (Button) mCountView.findViewById(R.id.btn_reduce);
        reduceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(numTxt.getText().toString());
                if (num == 1) {
                    return;
                }
                num--;
                numTxt.setText(num + "");
            }
        });

        mConfirmBtn = (Button) mCountView.findViewById(R.id.btn_buycar);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(numTxt.getText().toString());
                BuycarBean data = new BuycarBean();
                data.id = mData.id;
                data.price = mData.price;
                data.name = mData.name;
                data.count = num;
                data.isSelect = true;
                if (mData.bigimg != null && mData.bigimg.size() > 0) {
                    data.imgUrl = mData.bigimg.get(0).url;
                }

                if (mAddOrBuy) {
                    List<BuycarBean> datas = FWDatabaseManager.getInstance().findById(data.id);
                    if (datas == null || datas.size() == 0) {
                        FWDatabaseManager.getInstance().add(data);
                    } else {
                        BuycarBean temp = datas.get(0);
                        int count = temp.count + data.count;
                        FWDatabaseManager.getInstance().update(temp.id, count);
                    }
                    updateBuycarCount();
                    ToastUtil.show("加入购物车成功!");
                } else {
                    startActivity(new Intent(GoodsDetailPage.this, GoodPage.class));

                }

                mGoodsDetailPresenter.hideSelectCount(mCountView);


            }
        });
    }

    private void initTags() {
        List<String> datas = new ArrayList<>();
        datas.add("正品保障");
        datas.add("七天包退");
        datas.add("货到付款");
        datas.add("隐私保护");
        mTagRecyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mTagRecyclerView.setLayoutManager(layoutManager);

        GoodsTagsAdapter mAdapter = new GoodsTagsAdapter(this);
        mTagRecyclerView.setAdapter(mAdapter);
        mAdapter.updateDatas(datas);
    }


    private void updateBuycarCount() {
        List<BuycarBean> datas = FWDatabaseManager.getInstance().findAll();
        if (datas == null || datas.size() == 0) {
            mBuycarCount.setVisibility(View.GONE);
        } else {

            mBuycarCount.setVisibility(View.VISIBLE);
            mBuycarCount.setText(datas.size() + "");

        }
    }

    @Override
    public void OnRequstDetailSuccess(GoodsDetailBean data) {
        mData = data;
        mBannerDatas = data.bigimg;
        initViewPager();
        initGoodDetail();
        initBuycarDetail();
        initTags();
    }

    @Override
    public void OnRequestDetailFail() {

    }
}
