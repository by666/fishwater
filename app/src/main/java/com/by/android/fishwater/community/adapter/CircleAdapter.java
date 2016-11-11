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
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by by.huang on 2016/10/27.
 */

public class CircleAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<CircleBean> mDatas= new ArrayList<>();
    private int size = 0;

    public CircleAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<CircleBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_circle, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final CircleBean data = mDatas.get(position);
            itemViewHolder.mTitleTxt.setText(data.title);
            itemViewHolder.mSubTitleTxt.setText(data.subTitle);

            if (StringUtils.isNotEmpty(data.url)) {
                Uri uri = Uri.parse(data.url);
                itemViewHolder.mBgImg.setAspectRatio(1.33f);
                itemViewHolder.mBgImg.setImageURI(uri);
            }


        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<CircleBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mBgImg;
        TextView mTitleTxt;
        TextView mSubTitleTxt;



        public ItemViewHolder(View view) {
            super(view);
            mBgImg = (SimpleDraweeView) view.findViewById(R.id.img_bg);
            mTitleTxt = (TextView) view.findViewById(R.id.txt_title);
            mSubTitleTxt = (TextView) view.findViewById(R.id.txt_subtitle);


        }
    }
}