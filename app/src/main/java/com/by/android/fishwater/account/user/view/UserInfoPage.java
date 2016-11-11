package com.by.android.fishwater.account.user.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.user.presenter.UserPresenter;
import com.by.android.fishwater.mine.adapter.FansFollowAdapter;
import com.by.android.fishwater.mine.bean.FansFollowerBean;
import com.by.android.fishwater.mine.presenter.FansFollowerPresenter;
import com.by.android.fishwater.mine.view.FollowPage;
import com.by.android.fishwater.mine.view.IFansFollwerInterface;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.ToastUtil;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.AlphaTextView;
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

import static u.aly.av.S;

/**
 * Created by by.huang on 2016/11/8.
 */

public class UserInfoPage extends FWActivity implements IUserPageInterface, View.OnClickListener, IFansFollwerInterface {

    private int mUserId;
    private final static String EXTRA_ID = "id";
    private final int DefaultID = -1;
    private UserPresenter mUserPresenter;
    private FansFollowerPresenter mFansFollowerPresenter;
    private final int Post = 0;
    private final int Article = 1;
    private final int Fans = 2;
    private final int Follows = 3;

    @ViewInject(R.id.community_person_bg)
    SimpleDraweeView mBgImg;

    @ViewInject(R.id.btn_community_person_back)
    AlphaImageView mBackImg;

    @ViewInject(R.id.community_user_icon)
    SimpleDraweeView mHeadImg;

    @ViewInject(R.id.community_user_name)
    TextView mNicknameTxt;

    @ViewInject(R.id.user_level)
    TextView mLevelTxt;

    @ViewInject(R.id.btn_letter)
    AlphaTextView mLetterBtn;

    @ViewInject(R.id.btn_attention_in_userinfo)
    AlphaTextView mAttentionBtn;

    @ViewInject(R.id.user_post_count)
    TextView mPostCountTxt;

    @ViewInject(R.id.user_post_title)
    TextView mPostCountTitle;

    @ViewInject(R.id.user_post)
    LinearLayout mPostLayout;

    @ViewInject(R.id.user_article_count)
    TextView mArticleCountTxt;

    @ViewInject(R.id.user_article_title)
    TextView mArticleCountTitle;

    @ViewInject(R.id.user_article)
    LinearLayout mArticleLayout;

    @ViewInject(R.id.user_followers_count)
    TextView mFansCountTxt;

    @ViewInject(R.id.user_followers_title)
    TextView mFansCountTitle;

    @ViewInject(R.id.user_followers)
    LinearLayout mFansLayout;

    @ViewInject(R.id.user_following_count)
    TextView mFollowCountTxt;

    @ViewInject(R.id.user_following_title)
    TextView mFollowCountTitle;

    @ViewInject(R.id.user_following)
    LinearLayout mFollowLayout;

    @ViewInject(R.id.layout_nodata)
    View mNoDataLayout;

    @ViewInject(R.id.recyclerview)
    LRecyclerView mRecyclerView;

    private FansFollowAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<FansFollowerBean> mCuurentDatas = new ArrayList<>();
    private boolean isRequest = false;

    private TextView mCurrentTitle;
    private TextView mCurrentTxt;

    public static void show(FWActivity activity, int id) {
        Intent intent = new Intent(activity, UserInfoPage.class);
        intent.putExtra(EXTRA_ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_userinfo);
        x.view().inject(this);
        mUserId = getIntent().getIntExtra(EXTRA_ID, DefaultID);
        if (mUserId == DefaultID) {
            ToastUtil.show("用户不存在!");
            finish();
            return;
        }
        mUserPresenter = new UserPresenter(this);
        initView();
        mUserPresenter.getOtherUserInfo(mUserId);
    }

    private void initView() {
        mBackImg.setOnClickListener(this);
        mLetterBtn.setOnClickListener(this);
        mAttentionBtn.setOnClickListener(this);
        mPostLayout.setOnClickListener(this);
        mArticleLayout.setOnClickListener(this);
        mFansLayout.setOnClickListener(this);
        mFollowLayout.setOnClickListener(this);
        initRecyclerView();
        OnTabClick(Post, mPostCountTxt, mPostCountTitle);
    }

    private void initRecyclerView() {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        mRecyclerView.setPullRefreshEnabled(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());

        mListAdapter = new FansFollowAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.getFooterView().setVisibility(View.VISIBLE);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                FansFollowerBean data = mCuurentDatas.get(i);
                UserInfoPage.show(UserInfoPage.this, data.userid);
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCuurentDatas.removeAll(mCuurentDatas);
                mFansFollowerPresenter.getNewListData();
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isRequest) {
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Loading);
                    mFansFollowerPresenter.getListData(true);
                }
                isRequest = true;
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == mBackImg) {
            finish();
        } else if (v == mLetterBtn) {
            ToastUtil.show("开发中");
        } else if (v == mAttentionBtn) {
            mUserPresenter.attendUser(mUserId);
        } else if (v == mPostLayout) {
            OnTabClick(Post, mPostCountTxt, mPostCountTitle);
        } else if (v == mArticleLayout) {
            OnTabClick(Article, mArticleCountTxt, mArticleCountTitle);

        } else if (v == mFansLayout) {
            OnTabClick(Fans, mFansCountTxt, mFansCountTitle);

        } else if (v == mFollowLayout) {
            OnTabClick(Follows, mFollowCountTxt, mFollowCountTitle);
        }
    }


    private void OnTabClick(int tab, TextView countTxt, TextView titleTxt) {
        int Select_Color = getResources().getColor(R.color.home_text_selected_color);
        int Normal_Color = getResources().getColor(R.color.account_press);
        countTxt.setTextColor(Select_Color);
        titleTxt.setTextColor(Select_Color);
        if (mCurrentTxt != null && mCurrentTitle != null) {
            mCurrentTxt.setTextColor(Normal_Color);
            mCurrentTitle.setTextColor(Normal_Color);
        }

        if (tab == Post) {
            mRecyclerView.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);

        } else if (tab == Article) {
            mRecyclerView.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);

        } else if (tab == Fans) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoDataLayout.setVisibility(View.GONE);
            mFansFollowerPresenter = new FansFollowerPresenter(this, true);
            mFansFollowerPresenter.getNewListData();

        } else if (tab == Follows) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoDataLayout.setVisibility(View.GONE);
            mFansFollowerPresenter = new FansFollowerPresenter(this, false);
            mFansFollowerPresenter.getNewListData();
        }

        mCurrentTxt = countTxt;
        mCurrentTitle = titleTxt;
    }

    @Override
    public void OnRequestUserinfoSuccess(UserBean data) {

        if (data != null) {
            mBgImg.setImageURI(Uri.parse(data.avatar));
            mHeadImg.setImageURI(Uri.parse(data.avatar));
            mNicknameTxt.setText(data.nickname);

            mLevelTxt.setText(data.level);
            if (data.gender == UserBean.SEX_MAN) {
                mLevelTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.account_level_man));
                mLevelTxt.setTextColor(ResourceHelper.getColor(R.color.account_level_color_man));

            } else {
                mLevelTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.account_level_woman));
                mLevelTxt.setTextColor(ResourceHelper.getColor(R.color.account_level_color_woman));
            }

            mPostCountTxt.setText("?");
            mArticleCountTxt.setText("?");
            mFansCountTxt.setText(data.fansNum + "");
            mFollowCountTxt.setText(data.concernedNum + "");
        }

    }

    @Override
    public void OnRequestUserinfoFail() {
        ToastUtil.show("用户不存在!");
        finish();
    }

    @Override
    public void OnAttendUserSuccess(boolean isAttend) {

        if(isAttend)
        {
            mAttentionBtn.setText("取消关注");
        }else{
            mAttentionBtn.setText("关注TA");

        }
    }

    @Override
    public void OnAttendUserFail() {

    }

    @Override
    public void OnGetFansFollwerSuccess(List<FansFollowerBean> datas, boolean isLoadMore, boolean theEnd) {
        isRequest = false;
        mCuurentDatas = datas;
        if (mCuurentDatas == null || mCuurentDatas.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mNoDataLayout.setVisibility(View.VISIBLE);
        }
        if (theEnd) {
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
        }
        mListAdapter.updateData(datas);
        mRecyclerView.refreshComplete();
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnGetFansFollwerFail() {

    }
}
