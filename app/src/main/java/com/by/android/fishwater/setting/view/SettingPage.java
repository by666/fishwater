package com.by.android.fishwater.setting.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.account.login.view.LoginPage;
import com.by.android.fishwater.setting.presenter.SettingPresenter;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.ToastUtil;
import com.by.android.fishwater.view.AlphaImageView;
import com.by.android.fishwater.view.AlphaTextView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/11/12.
 */

public class SettingPage extends FWActivity implements ISettingPageInterface,View.OnClickListener{

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.layout_setting)
    LinearLayout mSettingLayout;

    @ViewInject(R.id.account_logout)
    AlphaTextView mLogoutBtn;

    private UserBean mUserBean;
    private SettingPresenter mSettingPresenter;
    private List<View> mTextLayouts = new ArrayList<>();
    private List<TextView> mTextViews = new ArrayList<>();

    private final static int ClearTag = 6;

    public static void show(FWActivity activity) {
        Intent intent = new Intent(activity, SettingPage.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_setting);
        x.view().inject(this);
        mSettingPresenter = new SettingPresenter(this);
        mUserBean = AccountManage.getInstance().getUserBean();
        initView();
    }

    private void initView() {
        initNavigationBar();
        initBody();
    }

    private void initNavigationBar() {
        mTitletxt.setText("设置");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initBody() {

        View imageSettingView = LayoutInflater.from(this).inflate(R.layout.item_setting_image, null);
        LinearLayout.LayoutParams headParms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ResourceHelper.getDimen(R.dimen.space_48));
        headParms.topMargin = ResourceHelper.getDimen(R.dimen.space_10);
        headParms.bottomMargin = ResourceHelper.getDimen(R.dimen.space_1);
        imageSettingView.setLayoutParams(headParms);
        TextView headTitle = (TextView) imageSettingView.findViewById(R.id.txt_setting_title);
        headTitle.setText("头像");
        SimpleDraweeView headImg = (SimpleDraweeView) imageSettingView.findViewById(R.id.txt_setting_image);
        headImg.setImageURI(Uri.parse(mUserBean.avatar));
        mSettingLayout.addView(imageSettingView);


        String[] titles = {"昵称", "性别", "年龄", "性取向", "恋爱状况", "修改密码", "清除缓存", "关于鱼水"};
        String gender = "";
        if (mUserBean.gender == UserBean.SEX_MAN) {
            gender = "男";
        } else {
            gender = "女";
        }
        String[] datas = {mUserBean.nickname, gender,mUserBean.age+"",UserBean.getSexuality(mUserBean.sexuality),UserBean.getMarital(mUserBean.marital),"",mSettingPresenter.getCacheSize(),""};
        LinearLayout.LayoutParams params;
        for (int i = 0; i < titles.length; i++) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ResourceHelper.getDimen(R.dimen.space_48));
            View textSettingView = LayoutInflater.from(this).inflate(R.layout.item_setting_text, null);
            textSettingView.setTag(i);
            if (i == 4 || i == 6) {
                params.bottomMargin = ResourceHelper.getDimen(R.dimen.space_10);
            } else {
                params.bottomMargin = ResourceHelper.getDimen(R.dimen.space_1);
            }
            textSettingView.setLayoutParams(params);
            textSettingView.requestLayout();
            TextView titleTxt = (TextView) textSettingView.findViewById(R.id.txt_setting_title);
            titleTxt.setTag(i);
            titleTxt.setText(titles[i]);

            TextView contentTxt = (TextView)textSettingView.findViewById(R.id.txt_setting_txt);
            contentTxt.setText(datas[i]);

            mTextViews.add(contentTxt);
            textSettingView.setOnClickListener(this);
            mTextLayouts.add(textSettingView);

            mSettingLayout.addView(textSettingView);

        }

        mLogoutBtn.setOnClickListener(this);
    }

    @Override
    public void OnClearSuccess() {
        ToastUtil.show("清理完成");
        if(mTextViews != null && mTextViews.size() > 0)
        {
            mTextViews.get(ClearTag).setText("0 MB");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mLogoutBtn)
        {
            AccountManage.getInstance().clear();
            LoginPage.show(this);
            finish();
            return;
        }
        int tag = (int)v.getTag();
        if(tag == ClearTag)
        {
            mSettingPresenter.clearCache();
        }
    }
}
