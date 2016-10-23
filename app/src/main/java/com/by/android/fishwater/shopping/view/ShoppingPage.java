package com.by.android.fishwater.shopping.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.by.android.fishwater.R;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.shopping.bean.CategoryBean;
import com.by.android.fishwater.shopping.bean.GoodsBean;
import com.by.android.fishwater.shopping.presenter.ShoppingPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/13.
 */


@ContentView(R.layout.page_shopping)
public class ShoppingPage extends Fragment implements IShoppingPageInterface {

    private ShoppingPresenter mShoppingPresenter;

    private View mHeadView;
    private ViewPager mViewPager;
    private LinearLayout mPointLayout;
    private List<ImageView> mImageViews = new ArrayList<>();

//    @ViewInject(R.id.scrollview_shopping)
//    PullToRefreshScrollView mShoppingScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShoppingPresenter = new ShoppingPresenter(this);
        initView();
    }

    private void initView() {
        mShoppingPresenter.getBannerListData();
    }

    private void initHeader(List<BannerBean> datas) {

        mHeadView = getView();
//        mHeadView = View.inflate(getActivity(), R.layout.shoppingpage_head, null);
        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen
                .space_180));
        mViewPager = (ViewPager) mHeadView.findViewById(R.id.viewpager);
        mViewPager.setLayoutParams(headParams);
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

        mPointLayout = (LinearLayout) mHeadView.findViewById(R.id.layout_point);
        RelativeLayout.LayoutParams pointParams = new RelativeLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen
                .space_30));
        pointParams.topMargin = getResources().getDimensionPixelSize(R.dimen
                .space_150);
        mPointLayout.setLayoutParams(pointParams);

        int size = getResources().getDimensionPixelSize(R.dimen.image_cycle_view_indicator_item_size);
        int gap = getResources().getDimensionPixelSize(R.dimen.image_cycle_view_indicator_item_gap);
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                ImageView pointImg = new ImageView(getActivity());
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

//        final ScrollView scrollView = mShoppingScrollView.getRefreshableView();


//        mListAdapter = new HomePageListAdapter(getActivity(), null);
//
//        mPullRefreshListView.getLoadingLayoutProxy(false, true)
//                .setPullLabel("上拉刷新...");
//        mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(
//                "放开刷新...");
//        mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
//                "正在加载...");
//        mPullRefreshListView.getLoadingLayoutProxy(true, false)
//                .setPullLabel("下拉刷新...");
//        mPullRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel(
//                "放开刷新...");
//        mPullRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel(
//                "正在加载...");
//
//        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
//        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
//                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//
//                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//                mHomePagePresenter.getNewHomeListData();
//                new HomePage.FinishRefresh().execute();
//            }
//        });
//
//        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//
//            @Override
//            public void onLastItemVisible() {
//                mHomePagePresenter.getHomeListData();
//                new HomePage.FinishRefresh().execute();
//            }
//        });
//
//        mPullRefreshListView.setAdapter(mListAdapter);
//        mPullRefreshListView.setOnItemClickListener(this);
    }


    private void initBody() {

//        mShoppingScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//
//            }
//        });
    }

    @Override
    public void OnBannerSuccess(List<BannerBean> datas) {
       initHeader(datas);
    }

    @Override
    public void OnBannerFail() {

    }

    @Override
    public void OnCategorySuccess(List<CategoryBean> datas) {

    }

    @Override
    public void OnCategoryFail() {

    }

    @Override
    public void OnGoodsSuccess(List<GoodsBean> datas) {

    }

    @Override
    public void OnGoodsFail() {

    }
}
