package com.by.android.fishwater.homepage.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.homepage.adapter.HomePageListAdapter;
import com.by.android.fishwater.homepage.adapter.HomePageViewPagerAdapter;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.presenter.HomePagePresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;

/**
 * Created by by.huang on 2016/10/9.
 */

@ContentView(R.layout.page_home)
public class HomePage extends Fragment implements IHomePageInterface, AdapterView.OnItemClickListener {


    private HomePagePresenter mHomePagePresenter;
    private ViewPager mViewPager;
    private LinearLayout mPointLayout;
    private List<ImageView> mImageViews = new ArrayList<>();

    private HomePageListAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<HomeListBean> mCuurentDatas = new ArrayList<>();
    private boolean isRequest = false;

    @ViewInject(R.id.recyclerview)
    LRecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomePagePresenter = new HomePagePresenter(this);
        mHomePagePresenter.getBannerListData();

    }

    private void initView(List<BannerBean> bannerBeans) {

        View headView = View.inflate(getActivity(), R.layout.homepage_header, null);
        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen
                .space_180));
        mViewPager = (ViewPager) headView.findViewById(R.id.viewpager);
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

        mPointLayout = (LinearLayout) headView.findViewById(R.id.layout_point);
        RelativeLayout.LayoutParams pointParams = new RelativeLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen
                .space_30));
        pointParams.topMargin = getResources().getDimensionPixelSize(R.dimen
                .space_150);
        mPointLayout.setLayoutParams(pointParams);

        int size = getResources().getDimensionPixelSize(R.dimen.image_cycle_view_indicator_item_size);
        int gap = getResources().getDimensionPixelSize(R.dimen.image_cycle_view_indicator_item_gap);
        if (bannerBeans != null && bannerBeans.size() > 0) {
            for (int i = 0; i < bannerBeans.size(); i++) {
                ImageView pointImg = new ImageView(getContext());
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

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        mRecyclerView.setPullRefreshEnabled(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mListAdapter = new HomePageListAdapter(getContext());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.addHeaderView(headView);
        mLRecyclerViewAdapter.getFooterView().setVisibility(View.VISIBLE);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                goHomeDetailPage(mCuurentDatas.get(i));
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCuurentDatas.removeAll(mCuurentDatas);
                mHomePagePresenter.getNewHomeListData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isRequest) {
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Loading);
                    mHomePagePresenter.getHomeListData(true);
                }
                isRequest = true;
            }
        });

    }


    @Override
    public void requestListDataSuccess(List<HomeListBean> datas, boolean isLoadMore, boolean theEnd) {
        isRequest = false;
        mCuurentDatas = datas;
        if (theEnd) {
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
        }
        mListAdapter.updateData(datas);
        mRecyclerView.refreshComplete();
        mLRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void requestListDataFail() {

    }

    @Override
    public void requestPagerDataSuccess(List<BannerBean> datas) {

        initView(datas);
        List<View> views = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            for (BannerBean data : datas) {
                SimpleDraweeView showImg = (SimpleDraweeView) LayoutInflater.from(getActivity()).inflate(R.layout.item_banner, null);
                showImg.setImageURI(Uri.parse(data.url));
                views.add(showImg);
            }
            mViewPager.setAdapter(new HomePageViewPagerAdapter(views));
        }

        mHomePagePresenter.getHomeListData(true);
    }

    @Override
    public void requestPagerDataFail() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<HomeListBean> datas = mListAdapter.getDatas();

        if (datas != null && datas.size() > 0) {
            HomeListBean data = datas.get(position - 1);
            goHomeDetailPage(data);
        }
    }

    private void goHomeDetailPage(HomeListBean data)
    {
        Intent intent = new Intent(getActivity(),HomeDetailPage.class);
        intent.putExtra("homelistbean",data);
        startActivity(intent);
    }

}
