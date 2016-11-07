package com.by.android.fishwater.emoji;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Copyright 2015 Jacks Blog(blog.dreamtobe.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Jacksgong on 15/6/29.
 * <p/>
 * Detail: http://blog.dreamtobe.cn/2015/06/29/keybord-panel-switch/
 */
public class CustomRootLayout extends LinearLayout {


    public CustomRootLayout(Context context) {
        super(context);
    }

    public CustomRootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private int mOldHeight = -1;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 由当前布局被键盘挤压，获知，由于键盘的活动，导致布局将要发生变化。
        do {
            final int width = MeasureSpec.getSize(widthMeasureSpec);
            final int height = MeasureSpec.getSize(heightMeasureSpec);

            if (height < 0) {
                break;
            }

            if (mOldHeight < 0) {
                mOldHeight = height;
                break;
            }

            final int offset = mOldHeight - height;
            mOldHeight = height;

            if (offset >= 0) {
                //键盘弹起 (offset > 0，高度变小)
                break;
            }

            final PanelLayout bottom = getPanelLayout(this);

            if (bottom == null) {
                break;
            }

            // 检测到真正的 由于键盘收起触发了本次的布局变化
            bottom.setIsNeedHeight(true);

        } while (false);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private PanelLayout mPanelLayout;

    private PanelLayout getPanelLayout(final View view) {
        if (mPanelLayout != null) {
            return mPanelLayout;
        }

        if (view instanceof PanelLayout) {
            mPanelLayout = (PanelLayout) view;
            return mPanelLayout;
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                PanelLayout v = getPanelLayout(((ViewGroup) view).getChildAt(i));
                if (v != null) {
                    mPanelLayout = v;
                    return mPanelLayout;
                }
            }
        }

        return null;

    }

    private int maxBottom = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (b >= maxBottom && maxBottom != 0) {
            // 在底部，键盘隐藏状态
            getPanelLayout(this).setIsKeybordShowing(false);
        } else if (maxBottom != 0) {
            getPanelLayout(this).setIsKeybordShowing(true);
        }

        if (maxBottom < b) {
            maxBottom = b;
        }

    }
}

