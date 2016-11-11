package com.by.android.fishwater;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.by.android.fishwater.community.view.CommunityPage;
import com.by.android.fishwater.homepage.view.HomePage;
import com.by.android.fishwater.homepage.view.HomePageActivity;
import com.by.android.fishwater.mine.view.MinePage;
import com.by.android.fishwater.shopping.view.ShoppingPage;

/**
 * Created by by.huang on 2016/10/10.
 */
public class FWPresenter {
    private HomePageActivity mActivity;
    private static FWPresenter mInstance;
    private Fragment mHomePage;
    private Fragment mCommunityPage;
    private Fragment mShoppingPage;
    private Fragment mMinePage;

    public static FWPresenter getInstance() {
        if (mInstance == null) {
            synchronized (FWPresenter.class) {
                if (mInstance == null) {
                    mInstance = new FWPresenter();
                }
            }
        }
        return mInstance;
    }

    public void init(Activity activity) {
        this.mActivity = (HomePageActivity) activity;
        HomePage page = new HomePage();
        mHomePage = page;
        replaceFragment(page);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layout_content, fragment);
        transaction.commit();
    }

    public void addFragment(Fragment showFragment) {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
        transaction.add(R.id.layout_content, showFragment);
        transaction.show(showFragment);
        transaction.commit();
    }

    public void addFragment(int index) {
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragments(ft);
//        transaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
        switch (index) {
            case 1:
                if (mHomePage != null)
                    ft.show(mHomePage);
                else {
                    mHomePage = new HomePage();
                    ft.add(R.id.layout_content, mHomePage);
                }
                break;
            case 2:
                if (mCommunityPage != null)
                    ft.show(mCommunityPage);
                else {
                    mCommunityPage = new CommunityPage();
                    ft.add(R.id.layout_content, mCommunityPage);
                }
                break;
            case 3:
                if (mShoppingPage != null)
                    ft.show(mShoppingPage);
                else {
                    mShoppingPage = new ShoppingPage();
                    ft.add(R.id.layout_content, mShoppingPage);
                }
                break;
            case 4:
                if (mMinePage != null)
                    ft.show(mMinePage);
                else {
                    mMinePage = new MinePage();
                    ft.add(R.id.layout_content, mMinePage);
                }
                break;
        }

        ft.commit();
    }

    public void hideFragments(FragmentTransaction ft) {
        if (mHomePage != null) {
            ft.hide(mHomePage);
        }
        if (mCommunityPage != null) {
            ft.hide(mCommunityPage);
        }
        if (mShoppingPage != null) {
            ft.hide(mShoppingPage);
        }
        if (mMinePage != null) {
            ft.hide(mMinePage);
        }
    }


    public void backLastFragment(FWActivity activity) {
        if (activity != null) {
            activity.finish();
        }
    }


    /**
     * 跳转到主页
     */
    public void goHomePage() {
        addFragment(1);
    }

    /**
     * 跳转到社区
     */
    public void goCommunityPage() {
        addFragment(2);
    }

    /**
     * 跳转到商城
     */
    public void goShoppingPage() {
        addFragment(3);
    }


    /**
     * 跳转到个人
     */
    public void goMinePage() {
        addFragment(4);
    }

    public void release() {
        mHomePage = null;
        mShoppingPage = null;
        mCommunityPage = null;
        mMinePage = null;
    }
}
