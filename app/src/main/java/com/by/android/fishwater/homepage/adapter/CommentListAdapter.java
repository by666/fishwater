package com.by.android.fishwater.homepage.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.homepage.bean.CommentBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/11/6.
 */

public class CommentListAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<CommentBean> mDatas = new ArrayList<>();
    private int size = 0;

    public CommentListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<CommentBean> datas)
    {
        this.mDatas = datas;
        if(mDatas!=null && mDatas.size()> 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentListAdapter.ItemViewHolder(mLayoutInflater.inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        CommentListAdapter.ItemViewHolder itemViewHolder = (CommentListAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            CommentBean data = mDatas.get(position);
            itemViewHolder.mTimeTxt.setText(data.intime);
            itemViewHolder.mNameTxt.setText(data.nickname);
            itemViewHolder.mContentTxt.setText(data.note);
            if (StringUtils.isNotEmpty(data.avatar)) {
                Uri uri = Uri.parse(data.avatar);
                itemViewHolder.mShowImg.setAspectRatio(1 / 0.58f);
                itemViewHolder.mShowImg.setImageURI(uri);
            }
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<CommentBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mNameTxt;
        TextView mContentTxt;
        TextView mTimeTxt;

        SimpleDraweeView mShowImg;

        public ItemViewHolder(View view) {
            super(view);
            mNameTxt = (TextView) view.findViewById(R.id.txt_name);
            mContentTxt = (TextView) view.findViewById(R.id.txt_content);
            mTimeTxt = (TextView) view.findViewById(R.id.txt_time);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.img_show);
        }
    }
}
