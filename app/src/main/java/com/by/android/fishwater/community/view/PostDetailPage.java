package com.by.android.fishwater.community.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.by.android.fishwater.FWActivity;
import com.by.android.fishwater.R;
import com.by.android.fishwater.account.login.bean.UserBean;
import com.by.android.fishwater.community.bean.PostDetailBean;
import com.by.android.fishwater.community.presenter.PostDetailPresenter;
import com.by.android.fishwater.emoji.EmotionKeyBoard;
import com.by.android.fishwater.util.HardwareUtil;
import com.by.android.fishwater.util.ResourceHelper;
import com.by.android.fishwater.util.ToastUtil;
import com.by.android.fishwater.view.AlphaImageView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by by.huang on 2016/11/12.
 */

public class PostDetailPage extends FWActivity implements IPostDetailInterface, View.OnClickListener {

    @ViewInject(R.id.txt_title)
    TextView mTitletxt;

    @ViewInject(R.id.btn_back)
    AlphaImageView mBackBtn;

    @ViewInject(R.id.layout_head)
    View mHeadLayout;

    @ViewInject(R.id.img_head)
    SimpleDraweeView mHeadImg;

    @ViewInject(R.id.txt_name)
    TextView mNameTxt;

    @ViewInject(R.id.img_sex)
    ImageView mSexImg;

    @ViewInject(R.id.txt_time)
    TextView mTimeTxt;

    @ViewInject(R.id.txt_post_title)
    TextView mPostTitle;

    @ViewInject(R.id.txt_note)
    TextView mNoteTxt;

    @ViewInject(R.id.layout_images)
    LinearLayout mImagesLayout;

    @ViewInject(R.id.emoji_keyboard)
    EmotionKeyBoard mEmotionKeyBoard;

    private final static String Extra_ID = "extra_id";
    private final static int DefaultID = -1;
    private int id;
    private PostDetailPresenter mPostDetailPresenter;

    public static void show(FWActivity activity, int id) {
        Intent intent = new Intent(activity, PostDetailPage.class);
        intent.putExtra(Extra_ID, id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_postdetail);
        x.view().inject(this);
        id = getIntent().getIntExtra(Extra_ID, DefaultID);
        if (id == DefaultID) {
            ToastUtil.show("帖子不存在");
            return;
        }
        mPostDetailPresenter = new PostDetailPresenter(this);
        initView();
    }

    private void initView() {
        initNavigationBar();
        mHeadLayout.setOnClickListener(this);
        mPostDetailPresenter.getPostDetail(id);
    }

    private void initNavigationBar() {
        mTitletxt.setText("帖子详情");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionKeyBoard.isShowEmojiKeyBord()) {
                    mEmotionKeyBoard.closeSoftware();
                    return;
                }
                finish();
            }
        });
    }

    private void updateData(PostDetailBean data) {

        mHeadImg.setImageURI(Uri.parse(data.avatar));
        mNameTxt.setText(data.nickname);
        if (data.gender == UserBean.SEX_MAN) {
            mSexImg.setImageResource(R.drawable.comment_man_icon);
        } else {
            mSexImg.setImageResource(R.drawable.comment_woman_icon);
        }

        mPostTitle.setText(data.title);
        mNoteTxt.setText(data.note);
        mTimeTxt.setText(data.intime);

        int width = HardwareUtil.screenWidth - ResourceHelper.getDimen(R.dimen.space_10) * 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        params.topMargin = ResourceHelper.getDimen(R.dimen.space_5);
        List<String> imageUrls = data.imagesUrl;
        if (imageUrls != null && imageUrls.size() > 0) {
            mImagesLayout.removeAllViews();
            for (String imageUrl : imageUrls) {
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(this);
                simpleDraweeView.setLayoutParams(params);
                simpleDraweeView.setImageURI(Uri.parse(imageUrl));
                mImagesLayout.addView(simpleDraweeView);
            }
        }
    }

    @Override
    public void OnRequestPostDetailSuccess(PostDetailBean data) {

        updateData(data);
    }

    @Override
    public void OnRequestPostDetailFail() {

    }

    @Override
    public void OnSendCommentSuccess() {

    }

    @Override
    public void OnSendCommentFail() {

    }

    @Override
    public void onClick(View v) {
        if (v == mHeadLayout) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mEmotionKeyBoard.isShowEmojiKeyBord()) {
                mEmotionKeyBoard.closeSoftware();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
