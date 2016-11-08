package com.by.android.fishwater.mine.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.FWPresenter;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.AccountManage;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.buycar.view.BuycarPage;
import com.by.android.fishwater.mine.presenter.MinePresenter;
import com.by.android.fishwater.order.bean.AddressBean;
import com.by.android.fishwater.order.view.AddressPage;
import com.by.android.fishwater.order.view.OrderDetailPage;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by by.huang on 2016/10/13.
 */

@ContentView(R.layout.page_mine)
public class MinePage extends Fragment implements View.OnClickListener, IMinePageInterface {

    @ViewInject(R.id.account_head_image)
    SimpleDraweeView mHeadImage;

    @ViewInject(R.id.account_no_login)
    View mNoLoginLayout;

    @ViewInject(R.id.account_login)
    View mLoginLayout;

    @ViewInject(R.id.account_nickname)
    TextView mNicknameTxt;

    @ViewInject(R.id.account_level)
    TextView mLevelTxt;

    @ViewInject(R.id.account_fans_number)
    TextView mFansNumTxt;

    @ViewInject(R.id.account_follow_number)
    TextView mFollowNumTxt;

    @ViewInject(R.id.account_follow_rl)
    View mFollowLayout;

    @ViewInject(R.id.account_fans_rl)
    View mFansLayout;

    @ViewInject(R.id.account_collect)
    View mCollectLayout;

    @ViewInject(R.id.account_all_order)
    View mAllOrderLayout;

    @ViewInject(R.id.account_buycar)
    View mBuycarLayout;

    @ViewInject(R.id.account_address)
    View mAddressLayout;


    private MinePresenter mMinePresenter;
    private AccountManage mAccountManage;
    private UserBean mUserBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMinePresenter = new MinePresenter(this);
        mAccountManage = AccountManage.getInstance();
        mUserBean = mAccountManage.getUserBean();
        initView();
    }

    private void initView() {

        if (mAccountManage.isLogin()) {
            mLoginLayout.setVisibility(View.VISIBLE);
            mNoLoginLayout.setVisibility(View.GONE);

            mHeadImage.setImageURI(Uri.parse(mUserBean.avatar));
            mNicknameTxt.setText(mUserBean.nickname);
            mLevelTxt.setText(mUserBean.level);
            if (mUserBean.gender == UserBean.SEX_MAN) {
                mLevelTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.account_level_man));
                mLevelTxt.setTextColor(ResourceHelper.getColor(R.color.account_level_color_man));

            } else {
                mLevelTxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.account_level_woman));
                mLevelTxt.setTextColor(ResourceHelper.getColor(R.color.account_level_color_woman));
            }
            mFollowNumTxt.setText(mUserBean.concernedNum + "");
            mFansNumTxt.setText(mUserBean.fansNum + "");

            mFansLayout.setOnClickListener(this);
            mFollowLayout.setOnClickListener(this);
            mAllOrderLayout.setOnClickListener(this);
            mBuycarLayout.setOnClickListener(this);
            mAddressLayout.setOnClickListener(this);

        } else {
            mLoginLayout.setVisibility(View.GONE);
            mNoLoginLayout.setVisibility(View.VISIBLE);
            mNoLoginLayout.setOnClickListener(this);

        }
    }

    @Override
    public void onClick(View v) {
        if (v == mNoLoginLayout) {

        } else if (v == mFansLayout) {
            startActivity(new Intent(getActivity(), FansPage.class));
        } else if (v == mFollowLayout) {
            startActivity(new Intent(getActivity(), FollowPage.class));
        } else if (v == mCollectLayout) {
            startActivity(new Intent(getActivity(), CollectPage.class));
        } else if (v == mAllOrderLayout) {
            OrderDetailPage.show((FWActivity) getActivity());
        } else if (v == mBuycarLayout) {
            startActivity(new Intent(getActivity(), BuycarPage.class));
        } else if (v == mAddressLayout) {
            startActivity(new Intent(getActivity(), AddressPage.class));
        }

    }
}
