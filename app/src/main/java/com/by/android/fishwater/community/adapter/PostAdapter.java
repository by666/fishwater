package com.by.android.fishwater.community.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.community.bean.CircleBean;
import com.by.android.fishwater.community.bean.RecommendPostBean;
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by by.huang on 2016/11/11.
 */

public class PostAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<RecommendPostBean> mDatas= new ArrayList<>();
    private int size = 0;

    public PostAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<RecommendPostBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostAdapter.ItemViewHolder(mLayoutInflater.inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final PostAdapter.ItemViewHolder itemViewHolder = (PostAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final RecommendPostBean data = mDatas.get(position);
            itemViewHolder.mTitleTxt.setText(data.title);
            itemViewHolder.mSubTitleTxt.setText(data.subTitle);

            if (StringUtils.isNotEmpty(data.url)) {
                Uri uri = Uri.parse(data.url);
                itemViewHolder.mShowImg.setAspectRatio(1.33f);
                itemViewHolder.mShowImg.setImageURI(uri);
            }


        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<RecommendPostBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mShowImg;
        TextView mTitleTxt;
        TextView mSubTitleTxt;



        public ItemViewHolder(View view) {
            super(view);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.img_show);
            mTitleTxt = (TextView) view.findViewById(R.id.txt_title);
            mSubTitleTxt = (TextView) view.findViewById(R.id.txt_subtitle);


        }
    }
}