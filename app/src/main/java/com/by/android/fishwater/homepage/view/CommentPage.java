package com.by.android.fishwater.homepage.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.emoji.EmoticonsUtils;
import com.by.android.fishwater.emoji.EmotionKeyBoard;
import com.by.android.fishwater.homepage.adapter.CommentListAdapter;
import com.by.android.fishwater.homepage.adapter.HomePageListAdapter;
import com.by.android.fishwater.homepage.bean.CommentBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.presenter.AnimotionDao;
import com.by.android.fishwater.homepage.presenter.CommentPresenter;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.StringUtils;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.by.android.fishwater.R.id.tip_comment_layout;

/**
 * Created by by.huang on 2016/11/6.
 */

public class CommentPage extends FWActivity implements View.OnClickListener,ICommentInterface{

    @ViewInject(R.id.txt_title)
    TextView mTitleTxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.recyclerview_comment)
    LRecyclerView mRecyclerView;

    @ViewInject(R.id.tip_comment_layout)
    LinearLayout mTipCommentLayout;

    @ViewInject(R.id.tip_comment_head_icon)
    SimpleDraweeView mTipCommentHeadIcon;

    @ViewInject(R.id.comment_list_content_bg)
    LinearLayout mContentBackground;

    @ViewInject(R.id.emoji_keyboard)
    EmotionKeyBoard mEmotionKeyBoard;

    private HomeListBean data;
    private CommentPresenter mCommentPresenter;
    private CommentListAdapter mListAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<CommentBean> mCuurentDatas = new ArrayList<>();
    private boolean isRequest = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_comment);
        x.view().inject(this);
        mCommentPresenter = new CommentPresenter(this);
        initView();
        data = (HomeListBean) getIntent().getSerializableExtra("homelistbean");
        mCommentPresenter.getNewHomeListData(data);
    }

    private void initView() {
        initNavigationBar();
        initRecyclerView();
        initEmotionKeyBoard();
    }

    private void initNavigationBar() {
        mTitleTxt.setText("评论列表");
        mBackBtn.setOnClickListener(this);
    }

    private void initRecyclerView()
    {
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mRecyclerView.setPullRefreshEnabled(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new LinearLayoutDecoration.Builder(this).setHeight(R.dimen.space_1).setColor(getResources().getColor(R.color.gray_bg)).build());

        mListAdapter = new CommentListAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mLRecyclerViewAdapter.getFooterView().setVisibility(VISIBLE);
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
//                goHomeDetailPage(mCuurentDatas.get(i));
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCuurentDatas.removeAll(mCuurentDatas);
                mCommentPresenter.getNewHomeListData(data);
            }
        });

        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isRequest) {
                    RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Loading);
                    mCommentPresenter.getHomeListData(data,true);
                }
                isRequest = true;
            }
        });

    }


    private void handlerOnItemClick(CommentBean commentBean) {
        mTipCommentLayout.startAnimation(AnimotionDao.getAlphaAnimationShow(500));
        mTipCommentLayout.setVisibility(VISIBLE);
//        if (StringUtils.isNotEmpty(commentBean.getContent())) {
//            EmoticonsUtils.setContent(getContext(), mTipCommentContent, commentBean.getContent());
//        }
//        if (StringUtils.isNotEmpty(commentBean.getHeadimageUrl())) {
//            mTipCommentHeadIcon.setImageURI(Uri.parse(commentBean.getHeadimageUrl()));
//        }
//        if (mEmotionKeyBoard == null) {
//            return;
//        }
//        mEmotionKeyBoard.getmEmoticonsEditText().requestFocus();
//        mEmotionKeyBoard.setEmotionEditHit("回复" + commentBean.getNickname());
//        mEmotionKeyBoard.tryOpenSoftBorad();
    }

    private void handlerOnContentBgClick() {
//        mTipComment = null;
        mContentBackground.setVisibility(GONE);
        mTipCommentLayout.setVisibility(GONE);
        if (mEmotionKeyBoard == null) {
            return;
        }
        mEmotionKeyBoard.setEmotionEditHit(ResourceHelper.getString(R.string.community_post_comment_default_hit));
        mEmotionKeyBoard.setEmotionEditContent("");
        mEmotionKeyBoard.closeSoftware();
    }

    private void initEmotionKeyBoard() {
        mEmotionKeyBoard.setEmotionButtonVisible(true);
        mEmotionKeyBoard.setOnPostButtonOnClickListener(new EmotionKeyBoard.OnPostButtonOnClickListener() {
            @Override
            public void onPostButtonClick() {
//                handlerPostCommentButtonOnClick();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mBackBtn) {
            finish();
        }
    }



    @Override
    public void requestListDataSuccess(List<CommentBean> datas, boolean isLoadMore, boolean theEnd) {
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
}
