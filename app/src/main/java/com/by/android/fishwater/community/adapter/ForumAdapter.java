package com.by.android.fishwater.community.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.community.bean.ForumBean;
import com.by.android.fishwater.shopping.bean.CategoryBean;
import com.by.android.fishwater.shopping.view.ShoppingSearchPage;
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/24.
 */

public class ForumAdapter extends RecyclerView.Adapter {

    private List<ForumBean> mDatas = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private int size = 0;
    private FWActivity mActivity;

    public ForumAdapter(FWActivity context) {
        this.mActivity = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateDatas(List<ForumBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.shopping_item_category, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
//                CategoryBean data = mDatas.get(tag);
//                Intent intent = new Intent(mActivity, ShoppingSearchPage.class);
//                intent.putExtra("category", data.id);
//                intent.putExtra("title", data.title);
//                mActivity.startActivity(intent);
            }
        });
        return new ForumAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ForumAdapter.ItemViewHolder itemViewHolder = (ForumAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            itemViewHolder.itemView.setTag(position);
            ForumBean data = mDatas.get(position);
            itemViewHolder.mTitleTxt.setText(data.title);

//            if (StringUtils.isNotEmpty(data.url)) {
                Uri uri = Uri.parse("res:///" + R.drawable.record_window_list_items_ms_selected);
                itemViewHolder.mShowImg.setImageURI(uri);
//            }
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<ForumBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTxt;
        SimpleDraweeView mShowImg;

        public ItemViewHolder(View view) {
            super(view);
            mTitleTxt = (TextView) view.findViewById(R.id.txt_show);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.img_show);
        }
    }
}
