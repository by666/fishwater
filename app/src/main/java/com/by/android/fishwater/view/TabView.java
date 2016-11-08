package com.by.android.fishwater.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.by.android.fishwater.R;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.SystemUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by by.huang on 2016/11/8.
 */

public class TabView extends FrameLayout implements View.OnClickListener {

    private List<TextView> mTextViews = new ArrayList<>();
    private int mCurrent = 0;
    private TabViewListener mListener;
    private int Select_Color;
    private int Normal_Color;
    private View mLineView;

    public TabView(Context context) {
        super(context);
        initView();
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void setListener(TabViewListener listener) {
        this.mListener = listener;
    }

    public void setDatas(List<String> datas) {

        Context context = getContext();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                TextView textView = new TextView(context);
                textView.setLayoutParams(params);
                textView.setGravity(Gravity.CENTER);
                textView.setText(datas.get(i));
                textView.setOnClickListener(this);
                textView.setTag(i);
                if (i == 0) {
                    textView.setTextColor(Select_Color);
                } else {
                    textView.setTextColor(Normal_Color);
                }
                mTextViews.add(textView);
                linearLayout.addView(textView);
            }
        }

        mLineView = new View(context);
        mLineView.setBackgroundColor(Select_Color);
        int width = HardwareUtil.screenWidth/datas.size() - ResourceHelper.getDimen(R.dimen.space_20);
        int height =  ResourceHelper.getDimen(R.dimen.space_2);
        FrameLayout.LayoutParams viewLayoutParam = new FrameLayout.LayoutParams(width,height);
        viewLayoutParam.gravity = Gravity.BOTTOM;
        viewLayoutParam.leftMargin = ResourceHelper.getDimen(R.dimen.space_10);
        mLineView.setLayoutParams(viewLayoutParam);
        addView(mLineView);
    }

    private void initView() {
        Select_Color = getResources().getColor(R.color.home_text_selected_color);
        Normal_Color = getResources().getColor(R.color.black);
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (mListener != null) {
            if (tag == mCurrent) {
                return;
            }
            mListener.OnTabClicker(tag);

            TextView lastTextView = mTextViews.get(mCurrent);
            lastTextView.setTextColor(Normal_Color);

            TextView clickTextView = mTextViews.get(tag);
            clickTextView.setTextColor(Select_Color);

            startAnim(tag);

            mCurrent = tag;
        }

    }

    private void startAnim(int tag)
    {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mLineView, "translationX",mCurrent * (HardwareUtil.screenWidth / 4), tag  * (HardwareUtil.screenWidth / 4)));
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }
}
