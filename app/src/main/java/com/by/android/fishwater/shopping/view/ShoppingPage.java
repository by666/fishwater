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
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.buycar.view.BuycarPage;
import com.by.android.fishwater.homepage.adapter.HomePageViewPagerAdapter;
import com.by.android.fishwater.shopping.adapter.ShoppingCategoryAdapter;
import com.by.android.fishwater.shopping.adapter.ShoppingGoodsAdapter;
import com.by.android.fishwater.shopping.bean.CategoryBean;
import com.by.android.fishwater.shopping.bean.GoodsBean;
import com.by.android.fishwater.shopping.presenter.ShoppingPresenter;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.view.GridLayoutDecoration;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.HeaderSpanSizeLookup;
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

import static android.R.attr.id;

/**
 * Created by by.huang on 2016/10/13.
 */


@ContentView(R.layout.page_shopping)
public class ShoppingPage extends Fragment implements IShoppingPageInterface {

    private ShoppingPresenter mShoppingPresenter;
    private ViewPager mViewPager;
    private LinearLayout mPointLayout;
    private List<ImageView> mImageViews = new ArrayList<>();

    private ShoppingGoodsAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<GoodsBean> mCuurentDatas = new ArrayList<>();
    private boolean isRequest = false;
    private List<BannerBean> mBannerDatas = new ArrayList<>();
    private List<CategoryBean> mCategoryDatas = new ArrayList<>();

    @ViewInject(R.id.recyclerview)
    LRecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShoppingPresenter = new ShoppingPresenter(this);
        mShoppingPresenter.getBannerListData();
    }

    private void initView() {
        View headView = View.inflate(getActivity(), R.layout.shopping_header, null);
        initHeader(headView);
        initBody(headView);
        mShoppingPresenter.getGoodListData(true,-1,-1);
    }

    private void initHeader(View headView)
    {
        initViewPager(headView);
        initCategotyView(headView);
    }

    /**
     * 初始化viewpager
     * @param headView
     */
    private void initViewPager(View headView)
    {
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
        if (mBannerDatas != null && mBannerDatas.size() > 0) {
            for (int i = 0; i < mBannerDatas.size(); i++) {
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

        List<View> views = new ArrayList<>();
        if (mBannerDatas != null && mBannerDatas.size() > 0) {
            for (BannerBean data : mBannerDatas) {
                SimpleDraweeView showImg = (SimpleDraweeView) LayoutInflater.from(getActivity()).inflate(R.layout.item_banner, null);
                showImg.setImageURI(Uri.parse(data.url));
                views.add(showImg);
            }
            mViewPager.setAdapter(new HomePageViewPagerAdapter(views));
        }
    }

    /**
     * 初始化分类视图
     * @param headView
     */
    private void initCategotyView(View headView)
    {

        RecyclerView mCategoryRecyclerView = (RecyclerView)headView.findViewById(R.id.recyclerview_category);
        mCategoryRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),4);
        mCategoryRecyclerView.setLayoutManager(layoutManager);

        ShoppingCategoryAdapter mAdapter = new ShoppingCategoryAdapter((FWActivity) getActivity());
        mCategoryRecyclerView.setAdapter(mAdapter);
        mAdapter.updateDatas(mCategoryDatas);


    }


    /**
     * 初始化列表
     */
    private void initBody(View headView)
    {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        GridLayoutDecoration decoration = new GridLayoutDecoration(ResourceHelper.getDimen(R.dimen.space_5),2);
        mRecyclerView.addItemDecoration(decoration);

        mListAdapter = new ShoppingGoodsAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.addHeaderView(headView);
        mLRecyclerViewAdapter.getFooterView().setVisibility(View.VISIBLE);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                GoodsBean data = mCuurentDatas.get(i);
                Intent intent= new Intent(getActivity(),GoodsDetailPage.class);
                intent.putExtra("id",data.id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCuurentDatas.removeAll(mCuurentDatas);
                mShoppingPresenter.getNewGoodsListData(-1,-1);
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(!isRequest)
                {
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Loading);
                    mShoppingPresenter.getGoodListData(true,-1,-1);
                }
                isRequest = true;
            }
        });

    }
    @Override
    public void OnBannerSuccess(List<BannerBean> datas) {
        mBannerDatas = datas;
        mShoppingPresenter.getGoodsCategoryList();
    }

    @Override
    public void OnBannerFail() {

    }

    @Override
    public void OnCategorySuccess(List<CategoryBean> datas) {
        mCategoryDatas = datas;
        initView();
    }

    @Override
    public void OnCategoryFail() {

    }

    @Override
    public void OnGoodsSuccess(List<GoodsBean> datas, boolean isLoadMore, boolean theEnd) {
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
    public void OnGoodsFail() {

    }
}
