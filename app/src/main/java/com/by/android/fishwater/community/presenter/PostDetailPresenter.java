package com.by.android.fishwater.community.presenter;

import com.by.android.fishwater.bean.RespondStrBean;
import com.by.android.fishwater.community.bean.PostDetailBean;
import com.by.android.fishwater.community.bean.respond.PostDetailResondBean;
import com.by.android.fishwater.community.view.IPostDetailInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.ToastUtil;

import java.util.HashMap;

import static com.by.android.fishwater.net.ErrorCode.Code_Success;

/**
 * Created by by.huang on 2016/11/12.
 */

public class PostDetailPresenter {

    private IPostDetailInterface mPostDetailInterface;

    public PostDetailPresenter(IPostDetailInterface postDetailInterface)
    {
        this.mPostDetailInterface = postDetailInterface;
    }

    public void getPostDetail(int id)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("a", "postsDetail");
        map.put("id", id);
        HttpRequest.Post(Constant.ForumUrl, map, new MyCallBack<PostDetailResondBean>() {
            @Override
            public void onSuccess(PostDetailResondBean result) {
                super.onSuccess(result);
                PostDetailBean postBean = result.data;
                mPostDetailInterface.OnRequestPostDetailSuccess(postBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mPostDetailInterface.OnRequestPostDetailFail();
            }
        });
    }

    public void sendComment(PostDetailBean data, String content)
    {
//        typeid	1/2/3/4	是	1-文章、2-商品、3-帖子、4-圈子
//        targetid	Int	是	评论的目标唯一ID
//        parentid	Int	否	回复的父级ID
//        replay	Int	是	回复某的用户ID
//        note	String	是	目标唯一ID

        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","comment");
        map.put("typeid",3);
        map.put("targetid",data.id);
        map.put("note",content);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<RespondStrBean>() {
            @Override
            public void onSuccess(RespondStrBean result) {
                super.onSuccess(result);
                if(result.status == Code_Success)
                {
                    ToastUtil.show("评论成功!");
                    mPostDetailInterface.OnSendCommentSuccess();
                }else {
                    ToastUtil.show("评论失败!");
                    mPostDetailInterface.OnSendCommentFail();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.show("评论失败!");
                mPostDetailInterface.OnSendCommentFail();
            }
        });
    }
}
