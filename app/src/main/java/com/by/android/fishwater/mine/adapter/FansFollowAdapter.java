package com.by.android.fishwater.mine.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWApplication;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.user.view.UserInfoPage;
import com.by.android.fishwater.buycar.bean.BuycarBean;
import com.by.android.fishwater.buycar.presenter.BuycarPresenter;
import com.by.android.fishwater.mine.bean.FansFollowerBean;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.StringUtils;
import com.by.android.fishwater.widget.PriceView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import static com.by.android.fishwater.util.ResourceHelper.getDrawable;

/**
 * Created by by.huang on 2016/10/27.
 */

public class FansFollowAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private List<FansFollowerBean> mDatas = new ArrayList<>();
    private int size = 0;
    private Context mContext;

    public FansFollowAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateData(List<FansFollowerBean> datas) {
        this.mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            size = mDatas.size();
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_fans_follower, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (mDatas != null && mDatas.size() > 0) {
            final FansFollowerBean data = mDatas.get(position);
            itemViewHolder.mNameTxt.setText(data.nickname);
            itemViewHolder.mLevelTxt.setText(data.level+"");
            if (data.gender == UserBean.SEX_MAN) {
                itemViewHolder.mLevelTxt.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.account_level_man));
                itemViewHolder.mLevelTxt.setTextColor(ResourceHelper.getColor(R.color.account_level_color_man));

            } else {
                itemViewHolder.mLevelTxt.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.account_level_woman));
                itemViewHolder.mLevelTxt.setTextColor(ResourceHelper.getColor(R.color.account_level_color_woman));
            }
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

    public List<FansFollowerBean> getDatas() {
        return mDatas;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mShowImg;
        TextView mNameTxt;
        TextView mLevelTxt;



        public ItemViewHolder(View view) {
            super(view);
            mShowImg = (SimpleDraweeView) view.findViewById(R.id.account_fans_pic_image);
            mNameTxt = (TextView) view.findViewById(R.id.account_fans_name);
            mLevelTxt = (TextView) view.findViewById(R.id.account_fans_level);

        }
    }
}