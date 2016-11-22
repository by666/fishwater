package com.by.android.fishwater.homepage.presenter;

import com.by.android.fishwater.bean.RespondObjBean;
import com.by.android.fishwater.bean.RespondStrBean;
import com.by.android.fishwater.homepage.bean.CommentBean;
import com.by.android.fishwater.homepage.bean.HomeListBean;
import com.by.android.fishwater.homepage.bean.respond.CommentRespondBean;
import com.by.android.fishwater.homepage.view.ICommentInterface;
import com.by.android.fishwater.net.HttpRequest;
import com.by.android.fishwater.net.MyCallBack;
import com.by.android.fishwater.util.Constant;
import com.by.android.fishwater.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.by.android.fishwater.net.ErrorCode.Code_Success;

/**
 * Created by by.huang on 2016/11/6.
 */

public class CommentPresenter {

    private ICommentInterface mCommentInterface;
    private int currentPosition = 0;
    private List<CommentBean> currentDatas = new ArrayList<>();
    private final static int DEFAULTID = -1;

    public CommentPresenter(ICommentInterface commentInterface)
    {
        this.mCommentInterface = commentInterface;
    }


    public void getNewHomeListData(HomeListBean data)
    {
        currentPosition = 0;
        currentDatas.removeAll(currentDatas);
        getHomeListData(data,false);
    }

    public void getHomeListData(HomeListBean data,final boolean isLoadMore)
    {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","commentList");
        map.put("typeid",data.typeID);
        map.put("targetid",data.targetID);
        map.put("begin",currentPosition);

        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<CommentRespondBean>() {
            @Override
            public void onSuccess(CommentRespondBean result) {
                super.onSuccess(result);
                List<CommentBean> datas = result.data;
                currentPosition += datas.size();
                if(datas.size() == 0) {
                    mCommentInterface.requestListDataSuccess(currentDatas,isLoadMore,true);
                }
                else {
                    currentDatas.addAll(datas);
                    mCommentInterface.requestListDataSuccess(currentDatas,isLoadMore,false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mCommentInterface.requestListDataFail();
            }
        });
    }

    public void sendComment(HomeListBean data,String content)
    {
//        typeid	1/2/3/4	是	1-文章、2-商品、3-帖子、4-圈子
//        targetid	Int	是	评论的目标唯一ID
//        parentid	Int	否	回复的父级ID
//        replay	Int	是	回复某的用户ID
//        note	String	是	目标唯一ID

        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","comment");
        map.put("typeid",data.typeID);
        map.put("targetid",data.targetID);
        map.put("note",content);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<RespondStrBean>() {
            @Override
            public void onSuccess(RespondStrBean result) {
                super.onSuccess(result);
                if(result.status == Code_Success)
                {
                    ToastUtil.show("评论成功!");
                    mCommentInterface.sendCommentSuccess();
                }else {
                    ToastUtil.show("评论失败!");
                    mCommentInterface.sendCommentFail();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.show("评论失败!");
                mCommentInterface.sendCommentFail();
            }
        });
    }


    public void sendReplayComment(HomeListBean data,String content,int parentid,String replay)
    {
//        typeid	1/2/3/4	是	1-文章、2-商品、3-帖子、4-圈子
//        targetid	Int	是	评论的目标唯一ID
//        parentid	Int	否	回复的父级ID
//        replay	Int	是	回复某的用户ID
//        note	String	是	目标唯一ID

        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a","comment");
        map.put("typeid",data.typeID);
        map.put("targetid",data.targetID);
        map.put("parentid",parentid);
        map.put("replay",replay);
        map.put("note",content);
        HttpRequest.Post(Constant.UserUrl, map, new MyCallBack<RespondObjBean>() {
            @Override
            public void onSuccess(RespondObjBean result) {
                super.onSuccess(result);
                if(result.status == Code_Success)
                {
                    ToastUtil.show("回复成功!");
                }else {
                    ToastUtil.show("回复失败!");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                ToastUtil.show("回复失败!");
            }
        });
    }

}
