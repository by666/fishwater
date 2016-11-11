package com.by.android.fishwater.community.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.user.view.UserInfoPage;
import com.by.android.fishwater.community.bean.ForumBean;
import com.by.android.fishwater.community.bean.PostBean;
import com.by.android.fishwater.community.presenter.PostPresenter;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.ResourceHelper;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by by.huang on 2016/10/24.
 */

public class PostListAdapter extends RecyclerView.Adapter {

    private List<PostBean> mDatas = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private int size = 0;
    private FWActivity mActivity;
    private LinearLayout.LayoutParams params;
    private PostPresenter mPostPresenter;

    public PostListAdapter(FWActivity context, PostPresenter postPresenter) {
        this.mActivity = context;
        this.mPostPresenter = postPresenter;
        mLayoutInflater = LayoutInflater.from(context);
        int width = (HardwareUtil.screenWidth - ResourceHelper.getDimen(R.dimen.space_10) * 4) / 3;
        params = new LinearLayout.LayoutParams(width, width);
    }

    public void updateData(List<PostBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.item_postlist, parent, false);
        return new PostListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostListAdapter.ItemViewHolder itemViewHolder = (PostListAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            itemViewHolder.itemView.setTag(position);
            final PostBean data = mDatas.get(position);

            itemViewHolder.mHeadLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserInfoPage.show(mActivity, data.userid);
                }
            });

            itemViewHolder.mHeadImg.setImageURI(data.avatar);
            itemViewHolder.mNameTxt.setText(data.nickname);
            if (data.gender == UserBean.SEX_MAN) {
                itemViewHolder.mSexImg.setImageResource(R.drawable.comment_man_icon);
            } else {
                itemViewHolder.mSexImg.setImageResource(R.drawable.comment_woman_icon);
            }

            itemViewHolder.mTitleTxt.setText(data.title);
            itemViewHolder.mNoteTxt.setText(data.note);
            itemViewHolder.mTimeTxt.setText(data.intime);

            itemViewHolder.mPraiseLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPostPresenter.praise(data);
                }
            });

            if (data.isPraise) {
                itemViewHolder.mPraiseImg.setImageResource(R.drawable.community_btn_praise_select);

            } else {
                itemViewHolder.mPraiseImg.setImageResource(R.drawable.community_btn_praise);

            }
            itemViewHolder.mPraiseTxt.setText("0");

            itemViewHolder.mMsgLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            itemViewHolder.mMsgImg.setImageResource(R.drawable.community_comment_icon);
            itemViewHolder.mMsgTxt.setText("0");

            List<String> imageUrls = data.imagesUrl;
            if (imageUrls != null && imageUrls.size() > 0) {
                itemViewHolder.mImagesLayout.removeAllViews();
                for (String imageUrl : imageUrls) {
                    SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mActivity);
                    simpleDraweeView.setLayoutParams(params);
                    simpleDraweeView.setImageURI(Uri.parse(imageUrl));
                    itemViewHolder.mImagesLayout.addView(simpleDraweeView);
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return size;
    }

    public List<PostBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mHeadLayout;
        SimpleDraweeView mHeadImg;
        TextView mNameTxt;
        ImageView mSexImg;

        TextView mTitleTxt;
        TextView mNoteTxt;
        TextView mTimeTxt;
        LinearLayout mImagesLayout;

        LinearLayout mPraiseLayout;
        TextView mPraiseTxt;
        ImageView mPraiseImg;

        LinearLayout mMsgLayout;
        TextView mMsgTxt;
        ImageView mMsgImg;

        public ItemViewHolder(View view) {
            super(view);
            mHeadLayout = (RelativeLayout) view.findViewById(R.id.layout_head);
            mHeadImg = (SimpleDraweeView) view.findViewById(R.id.img_head);
            mNameTxt = (TextView) view.findViewById(R.id.txt_name);
            mSexImg = (ImageView) view.findViewById(R.id.img_sex);

            mTitleTxt = (TextView) view.findViewById(R.id.txt_title);
            mNoteTxt = (TextView) view.findViewById(R.id.txt_note);
            mTimeTxt = (TextView) view.findViewById(R.id.txt_time);
            mImagesLayout = (LinearLayout) view.findViewById(R.id.layout_images);

            mPraiseLayout = (LinearLayout) view.findViewById(R.id.layout_praise);
            mPraiseTxt = (TextView) view.findViewById(R.id.txt_praise);
            mPraiseImg = (ImageView) view.findViewById(R.id.img_praise);

            mMsgLayout = (LinearLayout) view.findViewById(R.id.layout_msg);
            mMsgTxt = (TextView) view.findViewById(R.id.txt_msg);
            mMsgImg = (ImageView) view.findViewById(R.id.img_msg);
        }
    }
}
