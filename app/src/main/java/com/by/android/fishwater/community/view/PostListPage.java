package com.by.android.fishwater.community.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.community.adapter.PostListAdapter;
import com.by.android.fishwater.community.bean.CircleBean;
import com.by.android.fishwater.community.bean.PostBean;
import com.by.android.fishwater.community.presenter.PostPresenter;
import com.by.android.fishwater.util.ToastUtil;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.LinearLayoutDecoration;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.id;
import static com.by.android.fishwater.R.drawable.account_add_follower;

/**
 * Created by by.huang on 2016/11/11.
 */

public class PostListPage extends FWActivity implements IPostInterface {

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.recyclerview_postlist)
    LRecyclerView mPostRecyclerView;

    private PostListAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<PostBean> mCuurentDatas = new ArrayList<>();
    private boolean isRequest = false;

    private static final String Extra_Data = "extra_data";

    private CircleBean mData;

    private PostPresenter mPostPresenter;

    public static void show(FWActivity activity, CircleBean data) {
        Intent intent = new Intent(activity, PostListPage.class);
        intent.putExtra(Extra_Data, data);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_postlist);
        x.view().inject(this);

        mData = (CircleBean) getIntent().getSerializableExtra(Extra_Data);
        mPostPresenter = new PostPresenter(this);
        initView();
        mPostPresenter.getNewPostList(mData.id);
    }

    private void initView() {
        initNavigationBar();
        initRecyclerView();
    }

    private void initNavigationBar() {
        mTitletxt.setText(mData.title);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {

        View headView = LayoutInflater.from(this).inflate(R.layout.header_post, null);
        TextView titleTxt = (TextView) headView.findViewById(R.id.txt_title);
        titleTxt.setText(mData.title);

        TextView subTitleTxt = (TextView) headView.findViewById(R.id.txt_subtitle);
        subTitleTxt.setText(mData.subTitle);

        SimpleDraweeView showImg = (SimpleDraweeView) headView.findViewById(R.id.img_show);
        showImg.setImageURI(Uri.parse(mData.url));

        SimpleDraweeView followImg = (SimpleDraweeView) headView.findViewById(R.id.img_follow);
        followImg.setAspectRatio(206 / 84);
        if (mData.status == 1) {
            followImg.setImageURI(Uri.parse("res:///" + R.drawable.account_cancel_follower));
        } else {
            followImg.setImageURI(Uri.parse("res:///" + R.drawable.account_add_follower));
        }
        followImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtil.show("暂无接口");
            }
        });


        mPostRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mPostRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        mPostRecyclerView.setPullRefreshEnabled(true);


        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPostRecyclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_4).setColor(getResources().getColor(R.color.gray_bg)).build());

        mListAdapter = new PostListAdapter(this,mPostPresenter);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mPostRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.addHeaderView(headView);
        mLRecyclerViewAdapter.getFooterView().setVisibility(View.VISIBLE);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {

            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mPostRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCuurentDatas.removeAll(mCuurentDatas);
                mPostPresenter.getNewPostList(id);
            }
        });

        mPostRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isRequest) {
                    RecyclerViewStateUtils.setFooterViewState(mPostRecyclerView, LoadingFooter.State.Loading);
                    mPostPresenter.getPostList(id, true);
                }
                isRequest = true;
            }
        });
    }

    @Override
    public void OnRequestPostListSuccess(List<PostBean> datas, boolean isLoadMore, boolean theEnd) {
        isRequest = false;
        mCuurentDatas = datas;
        if (theEnd) {
            RecyclerViewStateUtils.setFooterViewState(mPostRecyclerView, LoadingFooter.State.TheEnd);
        }
        mListAdapter.updateData(datas);
        mPostRecyclerView.refreshComplete();
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnRequestPostListFail() {

    }

    @Override
    public void OnPraiseSuccess(PostBean data,int praise) {
        List<PostBean> datas = mListAdapter.getDatas();
        for (PostBean postBean : datas) {
            if (postBean.id == data.id) {
                if(praise == 1)
                {
                    postBean.isPraise = true;

                }
                else {
                    postBean.isPraise = false;
                }
                mListAdapter.updateData(datas);
                mPostRecyclerView.refreshComplete();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void OnPraiseFail() {

    }
}
