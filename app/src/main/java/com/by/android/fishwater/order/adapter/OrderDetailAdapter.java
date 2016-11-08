package com.by.android.fishwater.order.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.order.bean.OrderDetailBean;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/10/31.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<OrderDetailBean> mDatas = new ArrayList<>();
    private int size = 0;
    private Context mContext;

    public OrderDetailAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<OrderDetailBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderDetailAdapter.ItemViewHolder(mLayoutInflater.inflate(R.layout.item_order_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final OrderDetailAdapter.ItemViewHolder itemViewHolder = (OrderDetailAdapter.ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final OrderDetailBean data = mDatas.get(position);

            itemViewHolder.mOrderNumTxt.setText("订单号：" + data.orderid);
            String statu = "";
            switch (data.status) {
                case 1:
                    statu = "待付款";
                    break;
                case 2:
                    statu = "待发货";
                    break;
                case 3:
                    statu = "待收货";
                    break;
                case 4:
                    statu = "待评价";
                    break;
                case 5:
                    statu = "交易成功";
                    break;
                case 6:
                    statu = "交易关闭";
                    break;
                default:
                    break;
            }
            itemViewHolder.mOrderStatuTxt.setText(statu);
            itemViewHolder.mItemRecyclerView.setPullRefreshEnabled(false);
            itemViewHolder.mItemRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            OrderDetailItemAdapter mListAdapter = new OrderDetailItemAdapter(mContext);
            LRecyclerViewAdapter mLRecyclerViewAdapter = new LRecyclerViewAdapter(mListAdapter);
            itemViewHolder.mItemRecyclerView.setAdapter(mLRecyclerViewAdapter);
            mLRecyclerViewAdapter.removeFooterView(mLRecyclerViewAdapter.getFooterView());
            mListAdapter.updateData(data);
            mLRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public List<OrderDetailBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mOrderNumTxt;
        TextView mOrderStatuTxt;
        LRecyclerView mItemRecyclerView;

        public ItemViewHolder(View view) {
            super(view);
            mOrderNumTxt = (TextView) view.findViewById(R.id.txt_ordernum);
            mOrderStatuTxt = (TextView) view.findViewById(R.id.txt_orderstatu);
            mItemRecyclerView = (LRecyclerView) view.findViewById(R.id.recyclerview_item);

        }
    }
}

