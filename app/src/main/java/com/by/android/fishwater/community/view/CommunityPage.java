package com.by.android.fishwater.community.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.community.adapter.CircleAdapter;
import com.by.android.fishwater.community.adapter.ForumAdapter;
import com.by.android.fishwater.community.adapter.PostAdapter;
import com.by.android.fishwater.community.bean.CircleBean;
import com.by.android.fishwater.community.bean.CommunityBean;
import com.by.android.fishwater.community.bean.ForumBean;
import com.by.android.fishwater.community.presenter.CommunityPresenter;
import com.by.android.fishwater.homepage.adapter.HomePageViewPagerAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by by.huang on 2016/10/13.
 */


@ContentView(R.layout.page_community)
public class CommunityPage extends Fragment implements ICommunityInterface {


    private CommunityPresenter mCommunityPresenter;
    private List<ImageView> mImageViews = new ArrayList<>();

    @ViewInject(R.id.viewpager)
    ViewPager mViewPager;

    @ViewInject(R.id.layout_point)
    LinearLayout mPointLayout;

    //
    @ViewInject(R.id.recyclerview_category)
    LRecyclerView mForumRecyclerView;

    private ForumAdapter mForumAdapter;
    private LRecyclerViewAdapter mForumLRecyclerViewAdapter;


    //
    @ViewInject(R.id.recyclerview_mycircle)
    LRecyclerView mMyCircleRecyclerView;

    private CircleAdapter mMyCircleAdapter;
    private LRecyclerViewAdapter mMyCircleLRecyclerViewAdapter;

    //
    @ViewInject(R.id.recyclerview_recommend_circle)
    LRecyclerView mRecommendCircleRecyclerView;

    private CircleAdapter mRecommendCircleAdapter;
    private LRecyclerViewAdapter mRecommendCircleLRecyclerViewAdapter;

    //
    @ViewInject(R.id.recyclerview_recommend_post)
    LRecyclerView mRecommendPostRecyclerView;

    private PostAdapter mRecommendPostAdapter;
    private LRecyclerViewAdapter mRecommendPostLRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCommunityPresenter = new CommunityPresenter(this);
        initView();
    }


    private void initView() {
        initForumRecyclerView();
        initMyCircleRecyclerView();
        initRecommendCircleRecyclerView();
        initRecommendPostRecyclerView();
        mCommunityPresenter.getBannerListData();
        mCommunityPresenter.getForum();
        mCommunityPresenter.getCircle();
    }

    private void initForumRecyclerView() {
        mForumRecyclerView.setPullRefreshEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mForumRecyclerView.setLayoutManager(layoutManager);

        mForumAdapter = new ForumAdapter((FWActivity) getActivity());
        mForumLRecyclerViewAdapter = new LRecyclerViewAdapter(mForumAdapter);
        mForumRecyclerView.setAdapter(mForumLRecyclerViewAdapter);

        mForumLRecyclerViewAdapter.removeFooterView(mForumLRecyclerViewAdapter.getFooterView());
        mForumLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                List<ForumBean> datas = mForumAdapter.getDatas();
                ForumBean data = datas.get(i);
                CircleListPage.show((FWActivity) getActivity(),data.sub,data.title);
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });
    }

    private void initMyCircleRecyclerView() {
        mMyCircleRecyclerView.setPullRefreshEnabled(false);
        mMyCircleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mMyCircleAdapter = new CircleAdapter(getContext());
        mMyCircleLRecyclerViewAdapter = new LRecyclerViewAdapter(mMyCircleAdapter);
        mMyCircleRecyclerView.setAdapter(mMyCircleLRecyclerViewAdapter);

        mMyCircleLRecyclerViewAdapter.removeFooterView(mMyCircleLRecyclerViewAdapter.getFooterView());
        mMyCircleLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                List<CircleBean> datas = mMyCircleAdapter.getDatas();
                CircleBean data = datas.get(i);
                PostListPage.show((FWActivity) getActivity(),data);
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

    }

    private void initRecommendCircleRecyclerView() {
        mRecommendCircleRecyclerView.setPullRefreshEnabled(false);
        mRecommendCircleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecommendCircleAdapter = new CircleAdapter(getContext());
        mRecommendCircleLRecyclerViewAdapter = new LRecyclerViewAdapter(mRecommendCircleAdapter);
        mRecommendCircleRecyclerView.setAdapter(mRecommendCircleLRecyclerViewAdapter);

        mRecommendCircleLRecyclerViewAdapter.removeFooterView(mRecommendCircleLRecyclerViewAdapter.getFooterView());
        mRecommendCircleLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

    }

    private void initRecommendPostRecyclerView() {
        mRecommendPostRecyclerView.setPullRefreshEnabled(false);
        mRecommendPostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecommendPostAdapter = new PostAdapter(getContext());
        mRecommendPostLRecyclerViewAdapter = new LRecyclerViewAdapter(mRecommendPostAdapter);
        mRecommendPostRecyclerView.setAdapter(mRecommendPostLRecyclerViewAdapter);

        mRecommendPostLRecyclerViewAdapter.removeFooterView(mRecommendPostLRecyclerViewAdapter.getFooterView());
        mRecommendPostLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });
    }

    private void initViewPager(List<BannerBean> bannerBeans) {
        RelativeLayout.LayoutParams headParams = new RelativeLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen
                .space_180));
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
        RelativeLayout.LayoutParams pointParams = new RelativeLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen
                .space_30));
        pointParams.topMargin = getResources().getDimensionPixelSize(R.dimen
                .space_150);
        mPointLayout.setLayoutParams(pointParams);

        int size = getResources().getDimensionPixelSize(R.dimen.image_cycle_view_indicator_item_size);
        int gap = getResources().getDimensionPixelSize(R.dimen.image_cycle_view_indicator_item_gap);
        if (bannerBeans != null && bannerBeans.size() > 0) {
            for (int i = 0; i < bannerBeans.size(); i++) {
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
        if (bannerBeans != null && bannerBeans.size() > 0) {
            for (BannerBean data : bannerBeans) {
                SimpleDraweeView showImg = (SimpleDraweeView) LayoutInflater.from(getActivity()).inflate(R.layout.item_banner, null);
                showImg.setImageURI(Uri.parse(data.url));
                views.add(showImg);
            }
            mViewPager.setAdapter(new HomePageViewPagerAdapter(views));
        }

    }

    @Override
    public void requestPagerDataSuccess(List<BannerBean> datas) {
        initViewPager(datas);
    }

    @Override
    public void requestPagerDataFail() {

    }

    @Override
    public void requestCircleSuccess(CommunityBean data) {
        mMyCircleAdapter.updateData(data.follow);
        mMyCircleRecyclerView.refreshComplete();
        mMyCircleLRecyclerViewAdapter.notifyDataSetChanged();

        mRecommendCircleAdapter.updateData(data.circle);
        mRecommendCircleRecyclerView.refreshComplete();
        mRecommendCircleLRecyclerViewAdapter.notifyDataSetChanged();


        mRecommendPostAdapter.updateData(data.posts);
        mRecommendPostRecyclerView.refreshComplete();
        mRecommendPostLRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void requestCircleFail() {

    }

    @Override
    public void requestForumSuccess(List<ForumBean> datas) {
        mForumAdapter.updateDatas(datas);
        mForumRecyclerView.refreshComplete();
        mForumLRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestForumFail() {

    }
}
