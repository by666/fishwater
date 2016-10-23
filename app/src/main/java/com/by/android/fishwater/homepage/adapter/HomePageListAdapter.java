package com.by.android.fishwater.homepage.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/19.
 */

public class HomePageListAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<HomeListBean> mDatas = new ArrayList<>();
    private int size = 0;

    public HomePageListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<HomeListBean> datas)
    {
        this.mDatas = datas;
        if(mDatas!=null && mDatas.size()> 0) {
           size = mDatas.size();
        }
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.homepage_item_image_text, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            HomeListBean data = mDatas.get(position);
            itemViewHolder.mTitleTxt.setText(data.title);

            if (StringUtils.isNotEmpty(data.url)) {
                Uri uri = Uri.parse(data.url);
                itemViewHolder.mShowImg.setAspectRatio(1 / 0.58f);
                itemViewHolder.mShowImg.setImageURI(uri);

            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<HomeListBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTxt;
        TextView mContentTxt;
        SimpleDraweeView mShowImg;

        public ItemViewHolder(View view) {
            super(view);
            mTitleTxt = (TextView) view.findViewById(R.id.image_text_title);
            mContentTxt = (TextView) view.findViewById(R.id.image_text_content);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.image_text_image);
        }
    }
}