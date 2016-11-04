package com.by.android.fishwater.homepage.view;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.util.DeviceManager;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.MarketChannelManager;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.SettingFlags;
import com.by.android.fishwater.util.SystemHelper;

import org.xutils.x;


public class HomePageActivity extends FWActivity implements View.OnClickListener{

    private FWPresenter mPresenter;
    private LinearLayout mTabLayout;
    private final String[] titles = {"首页","社区","商城","我的"};
    private int[] resId = {R.drawable.tab_homepage,R.drawable.tab_community,R.drawable.tab_shopping,R.drawable.tab_account};
    private LinearLayout mCuurentTabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fishwater);
        x.view().inject(this);
        init();
        mPresenter = FWPresenter.getInstance();
        mPresenter.init(this);
    }

    private void init()
    {
        initTabLayout();
    }


    private void initTabLayout()
    {
        mTabLayout = (LinearLayout) findViewById(R.id.layout_tab);

        for(int i = 0 ;i<titles.length ; i++)
        {
            LinearLayout tabView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_tab,null);
            tabView.setTag(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(HardwareUtil.screenWidth / titles.length, ResourceHelper.getDimen(R.dimen.space_48));
            tabView.setLayoutParams(params);

            ImageView tabImg = (ImageView)tabView.findViewById(R.id.img_tab);
            tabImg.setImageResource(resId[i]);

            TextView tabTxt = (TextView) tabView.findViewById(R.id.txt_tab);
            tabTxt.setText(titles[i]);
            ColorStateList list = getResources().getColorStateList(R.color.tab_text_color);
            tabTxt.setTextColor(list);

            tabView.setOnClickListener(this);
            mTabLayout.addView(tabView);

            if(i == 0) {
                mCuurentTabView = tabView;
                tabView.setSelected(true);
            }
        }
    }

    public void showTab(int visiable)
    {
        mTabLayout.setVisibility(visiable);
    }


    @Override
    public void onClick(View v) {
        //待优化
        mCuurentTabView.setSelected(false);
        mCuurentTabView = (LinearLayout) v;
        mCuurentTabView.setSelected(true);
        int tag = (int)v.getTag();
        switch (tag)
        {
            case 0:
                mPresenter.goHomePage();
                break;
            case 1:
                mPresenter.goCommunityPage();
                break;
            case 2:
                mPresenter.goShoppingPage();
                break;
            case 3:
                mPresenter.goMinePage();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.release();
        mPresenter = null;
    }
}
