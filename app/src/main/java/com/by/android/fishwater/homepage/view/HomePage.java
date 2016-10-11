package com.by.android.fishwater.homepage.view;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.bean.BannerBean;
import com.by.android.fishwater.homepage.adapter.HomePageListAdapter;
import com.by.android.fishwater.homepage.adapter.HomePageViewPagerAdapter;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.presenter.HomePagePresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/9.
 */

@ContentView(R.layout.page_home)
public class HomePage extends Fragment implements IHomePageInterface,AdapterView.OnItemClickListener{


    private HomePageListAdapter mListAdapter;
    private HomePagePresenter mHomePagePresenter;
    private ViewPager mViewPager;

    @ViewInject(R.id.listview)
    PullToRefreshListView mPullRefreshListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomePagePresenter = new HomePagePresenter(this);
        initView();
        mHomePagePresenter.getBannerListData();
        mHomePagePresenter.getHomeListData();

    }

    private void initView()
    {

        View headView = View.inflate(getActivity(),R.layout.homepage_header,null);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen
                .space_180));
        mViewPager =(ViewPager) headView.findViewById(R.id.viewpager);
        mViewPager.setLayoutParams(layoutParams);
        final ListView listView = mPullRefreshListView.getRefreshableView();
        listView.addHeaderView(mViewPager);


        mListAdapter = new HomePageListAdapter(getActivity(),null);

        mPullRefreshListView.getLoadingLayoutProxy(false, true)
                .setPullLabel("上拉刷新...");
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "放开刷新...");
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载...");
        mPullRefreshListView.getLoadingLayoutProxy(true, false)
                .setPullLabel("下拉刷新...");
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel(
                "放开刷新...");
        mPullRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel(
                "正在加载...");

        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mHomePagePresenter.getNewHomeListData();
                new FinishRefresh().execute();
            }
        });

        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                mHomePagePresenter.getHomeListData();
                new FinishRefresh().execute();
            }
        });

        mPullRefreshListView.setAdapter(mListAdapter);
        mPullRefreshListView.setOnItemClickListener(this);

    }

    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            mPullRefreshListView.onRefreshComplete();
        }
    }

    @Override
    public void requestListDataSuccess(List<HomeListBean> datas) {

        mListAdapter.updateDatas(datas);
    }

    @Override
    public void requestListDataFail() {

    }

    @Override
    public void requestPagerDataSuccess(List<BannerBean> datas) {

        List<View> views = new ArrayList<>();
        if(datas != null && datas.size() > 0){
            for(BannerBean data : datas) {
                SimpleDraweeView showImg = (SimpleDraweeView)LayoutInflater.from(getActivity()).inflate(R.layout.item_banner,null);
                showImg.setImageURI(Uri.parse(data.url));
                views.add(showImg);
            }
            mViewPager.setAdapter(new HomePageViewPagerAdapter(views));
        }
    }

    @Override
    public void requestPagerDataFail() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<HomeListBean> datas = mListAdapter.getDatas();

        if(datas!=null && datas.size() > 0)
        {
            HomeListBean data = datas.get(position-1);
            mHomePagePresenter.goHomeDetailPresenter(data);
        }
    }
}
