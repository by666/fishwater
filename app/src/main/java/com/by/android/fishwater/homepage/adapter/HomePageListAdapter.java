package com.by.android.fishwater.homepage.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import static android.R.id.home;

/**
 * Created by by.huang on 2016/10/11.
 */

public class HomePageListAdapter extends BaseAdapter{

    private List<HomeListBean> mDatas;
    private Activity mCaller;

    public HomePageListAdapter(Activity caller,List<HomeListBean> datas)
    {
        this.mCaller = caller;
        this.mDatas = datas;
    }

    public void updateDatas(List<HomeListBean> datas)
    {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<HomeListBean> getDatas()
    {
        return mDatas;
    }

    @Override
    public int getCount() {
        if(mDatas == null || mDatas.size() == 0) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mCaller).inflate(R.layout.homepage_item_image_text,null);

            holder.mTitleTxt =(TextView)convertView.findViewById(R.id.image_text_title);
            holder.mContentTxt =(TextView)convertView.findViewById(R.id.image_text_content);
            holder.mShowImg =(SimpleDraweeView) convertView.findViewById(R.id.image_text_image);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeListBean data = mDatas.get(position);

        holder.mTitleTxt.setText(data.title);

        if (StringUtils.isNotEmpty(data.url)) {
            Uri uri = Uri.parse(data.url);
            holder.mShowImg.setAspectRatio(1 / 0.58f);
            holder.mShowImg.setImageURI(uri);

        }

        return convertView;
    }


    class ViewHolder
    {

        TextView mTitleTxt;
        TextView mContentTxt;
        SimpleDraweeView mShowImg;

    }
}
