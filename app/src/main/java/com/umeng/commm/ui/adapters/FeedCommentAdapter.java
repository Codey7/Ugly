/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.umeng.commm.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Comment;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.imageloader.ImgDisplayOption;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners.FetchListener;
import com.umeng.comm.core.listeners.Listeners.LoginOnViewClickListener;
import com.umeng.comm.core.nets.responses.SimpleResponse;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.Log;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.core.utils.TimeUtils;
import com.umeng.comm.core.utils.ToastMsg;
import com.umeng.comm.ui.imagepicker.adapters.CommonAdapter;
import com.umeng.comm.ui.imagepicker.listener.FrinendClickSpanListener;
import com.umeng.comm.ui.imagepicker.util.FeedViewRender;
import com.umeng.comm.ui.imagepicker.widgets.NetworkImageView;
import com.umeng.commm.ui.activities.UserInfoActivity;
import com.umeng.commm.ui.adapters.viewholders.FeedCommentViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Feed评论Adapter
 */
public class FeedCommentAdapter extends CommonAdapter<Comment, FeedCommentViewHolder> {

    private String mColon;
    private String mReplyText;
    private Context mContext;

    private String mPostOwnerId;
    private OnReplyCommentClickListener mReplyCommentListener;

    private boolean mIsInProgress;

    private ImgDisplayOption mDisplayOption = new ImgDisplayOption();

    public FeedCommentAdapter(Context context) {
        super(context);
        mColon = ResFinder.getString("umeng_comm_colon");
        mReplyText = ResFinder.getString("umeng_comm_reply");
        mDisplayOption.mLoadingResId = ResFinder.getResourceId(ResFinder.ResType.DRAWABLE,
                "umeng_comm_not_found");
        mDisplayOption.mLoadFailedResId = ResFinder.getResourceId(ResFinder.ResType.DRAWABLE,
                "umeng_comm_not_found");
        this.mContext = context;
    }

    @Override
    protected FeedCommentViewHolder createViewHolder() {
        return new FeedCommentViewHolder();
    }

    public void setPostOwnerId(String id) {
        this.mPostOwnerId = id;
    }

    public void setReplyCommentClickListener(OnReplyCommentClickListener l) {
        mReplyCommentListener = l;
    }

    private void renderCommentText(TextView textView, Comment comment) {
        // 设置评论昵称,普通形式为AA:评论内容,当回复回复某条评论时则是“AA回复BB”的形式
        String prefix = prepareCommentPrefix(comment);
        // 设置评论的内容
        textView.setText(prefix + comment.text);
        FeedViewRender.renderFriendText(mContext, textView, prepareRelativeUsers(comment),new FrinendClickSpanListener() {
            @Override
            public void onClick(CommUser user) {
                Intent intent = new Intent(mContext,
                        UserInfoActivity.class);
                intent.putExtra(Constants.TAG_USER, user);
                mContext.startActivity(intent);
            }
        });
    }
//// TODO: 16/1/14 会报空指针
    private void setCommentCreator(NetworkImageView imageView, Comment comment) {
        Log.e("xxxxxx","comment.creator="+comment.creator);
        ImgDisplayOption option = ImgDisplayOption.getOptionByGender(comment.creator.gender);
        imageView.setImageUrl(comment.creator.iconUrl, option);
        // 设置头像的点击事件,跳转到用户个人主页
        setClickFriendIconListener(imageView, comment.creator);
    }

    /**
     * 评论或者回复评论时涉及到的用户,如果是普通评论则只涉及评论的创建者,或者是回复评论,那么还涉及到被回复的对象
     *
     * @param comment
     * @return
     */
    private List<CommUser> prepareRelativeUsers(Comment comment) {
        List<CommUser> users = new ArrayList<CommUser>();
        users.add(comment.creator);
        if (isReplyCommemt(comment)) {
            users.add(comment.replyUser);
        }
        return users;
    }

    private boolean isReplyCommemt(Comment comment) {
        return false;
//        return comment.replyUser != null
//                && !TextUtils.isEmpty(comment.replyUser.name);
    }

    private String prepareCommentPrefix(Comment comment) {
        String text = "";
        // 如果有回复用户且该用户不是自己
        if (isReplyCommemt(comment)) {
            text += mReplyText + comment.replyUser.name + mColon;
        }
        return text;
    }

    /**
     * 设置点击评论中好友icon的逻辑，跳转至相关好友的个人中心
     *
     * @param iconImageView 显示头像的ImageView
     * @param user          创建该评论的用户
     */
    private void setClickFriendIconListener(final ImageView iconImageView, final CommUser user) {
        iconImageView.setOnClickListener(
//                new LoginOnViewClickListener() {
//            @Override
//            protected void doAfterLogin(View v) {
//                Intent intent = new Intent(mContext, UserInfoActivity.class);
//                intent.putExtra(Constants.TAG_USER, user);
//                mContext.startActivity(intent);
//            }
//        }
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra(Constants.TAG_USER, user);
                mContext.startActivity(intent);
                    }
                }
        );
    }

    private void setLikeIconOnClick(final TextView likeIconTv, final Comment comment) {

        likeIconTv.setOnClickListener(new LoginOnViewClickListener() {

            @Override
            protected void doAfterLogin(final View v) {
                if (mIsInProgress) {
                    return;
                }
                mIsInProgress = true;
                CommunityFactory.getCommSDK(mContext).likeComment(comment, new FetchListener<SimpleResponse>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(SimpleResponse response) {
                        mIsInProgress = false;
                        if (response != null) {
                            String tipStr = null;
                            switch (response.errCode) {
                                case ErrorCode.NO_ERROR:
                                    if (comment.liked) {
                                        comment.liked = false;
                                        comment.likeCount--;
                                        likeIconTv.setSelected(false);
                                        tipStr = "umeng_comm_discuss_unlike_success";
                                    } else {
                                        comment.liked = true;
                                        comment.likeCount++;
                                        likeIconTv.setSelected(true);
                                        tipStr = "umeng_comm_discuss_like_success";
                                    }
                                    likeIconTv.setText(String.valueOf(comment.likeCount));
                                    comment.saveEntity();
                                    break;

                                case ErrorCode.ERROR_COMMENT_LIKED:
                                    comment.liked = true;
//                                    comment.likeCount++;
                                    comment.saveEntity();
                                    likeIconTv.setText(String.valueOf(comment.likeCount));
                                    likeIconTv.setSelected(true);
                                    tipStr = "umeng_comm_discuss_like_success";
                                    break;

                                case ErrorCode.ERROR_COMMENT_LIKE_CANCELED:
                                    comment.liked = false;
//                                    comment.likeCount--;
                                    comment.saveEntity();
                                    likeIconTv.setText(String.valueOf(comment.likeCount));
                                    likeIconTv.setSelected(false);
                                    tipStr = "umeng_comm_discuss_unlike_success";
                                    break;

                                case ErrorCode.ERR_CODE_FEED_UNAVAILABLE:
                                    if (!comment.liked) {
                                        tipStr = "umeng_comm_discuss_like_failed_deleted";
                                    } else {
                                        tipStr = "umeng_comm_discuss_unlike_failed_deleted";
                                    }
                                    break;

                                case ErrorCode.ERR_CODE_FEED_LOCKED:
                                    if (!comment.liked) {
                                        tipStr = "umeng_comm_discuss_like_failed_locked";
                                    } else {
                                        tipStr = "umeng_comm_discuss_unlike_failed_locked";
                                    }
                                    break;

                                case ErrorCode.ERR_CODE_USER_FORBIDDEN:
                                    if (!comment.liked) {
                                        tipStr = "umeng_comm_discuss_like_failed_forbid";
                                    } else {
                                        tipStr = "umeng_comm_discuss_unlike_failed_forbid";
                                    }
                                    break;

                                case ErrorCode.USER_FORBIDDEN_ERR_CODE:
                                    tipStr = "umeng_comm_user_unusable";
                                    break;

                                case ErrorCode.ERROR_COMMENT_DELETE:
                                    if (!comment.liked) {
                                        tipStr = "umeng_comm_discuss_like_failed_deleted";
                                    } else {
                                        tipStr = "umeng_comm_discuss_unlike_failed_deleted";
                                    }
                                    break;

                                default:
                                    if (!comment.liked) {
                                        tipStr = "umeng_comm_discuss_like_failed";
                                    } else {
                                        tipStr = "umeng_comm_discuss_unlike_failed";
                                    }
                                    break;
                            }
                            if (!TextUtils.isEmpty(tipStr)) {
                                ToastMsg.showShortMsgByResName(tipStr);
                            }
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void setItemData(final int position, FeedCommentViewHolder holder, View rootView) {
        final Comment comment = getItem(position);
        // 渲染评论文本
        renderCommentText(holder.contentTextView, comment);
        // 设置评论创建者的头像和头像图标的点击事件
        setCommentCreator(holder.userHeaderImageView, comment);
        setLikeIconOnClick(holder.likeCountTextView, comment);

        if (comment.creator.id.equals(mPostOwnerId)) {
            holder.mFeedOwnerView.setVisibility(View.VISIBLE);
        } else {
            holder.mFeedOwnerView.setVisibility(View.GONE);
        }

        holder.userNameTextView.setText(comment.creator.name);
        holder.publishTimeTextView.setText(TimeUtils.format(comment.createTime));
        holder.likeCountTextView.setText(String.valueOf(comment.likeCount));

        if(comment.imageUrls.size() > 0 && !TextUtils.isEmpty(comment.imageUrls.get(0).thumbnail)){
            holder.mImg.setImageUrl(comment.imageUrls.get(0).thumbnail);
            holder.mImg.setVisibility(View.VISIBLE);
            holder.setPicOnClick(comment.imageUrls);
        }else{
            holder.mImg.setVisibility(View.GONE);
        }

        if (CommonUtils.isLogin(mContext) && comment.liked) {
            holder.likeCountTextView.setSelected(true);
        } else {
            holder.likeCountTextView.setSelected(false);
        }

        holder.mFloorView.setText(comment.floor + " 楼");

        holder.mCommentView.setOnClickListener(new LoginOnViewClickListener() {
            @Override
            protected void doAfterLogin(View v) {
                if (mReplyCommentListener != null) {
                    mReplyCommentListener.onReplyCommentClick(position);
                }
            }
        });

        Log.d("reply", "" + comment.replyUser + " id " + comment.replyCommentId);
        if (!TextUtils.isEmpty(comment.replyCommentId)) {
            View replyView = holder.viewStubCommentView;
            replyView.setVisibility(View.VISIBLE);

            final TextView replyName = (TextView) replyView.findViewById(ResFinder.getId("umeng_comm_comment_name"));

            final TextView replyDate = (TextView) replyView.findViewById(ResFinder.getId("umeng_comm_comment_time_reply"));

            final TextView replyFloor = (TextView) replyView.findViewById(ResFinder.getId("umeng_comm_comment_floor"));
            final TextView replyContent = (TextView) replyView.findViewById(ResFinder.getId("umeng_comm_msg_comment_content"));
            if(comment.childComment != null){
                replyName.setText(comment.replyUser.name);
                replyDate.setText(TimeUtils.format(comment.childComment.createTime));
                showReplyContent(comment.childComment, replyContent);
                replyFloor.setText(String.valueOf(comment.childComment.floor));
            }
        } else {
            holder.viewStubCommentView.setVisibility(View.GONE);
        }
    }

    public interface OnReplyCommentClickListener {
        public void onReplyCommentClick(int position);
    }

    private void showReplyContent(Comment comment, TextView replyContent){
        if(TextUtils.isEmpty(comment.text)){
            if(comment.imageUrls.size() > 0){
                replyContent.setText("["+ResFinder.getString("umeng_comm_pic")+"]");
            }else{
                replyContent.setText("");
            }
        }else{
            String content;
            if(comment.text.length() > 30){
                InputFilter[] filters = {new InputFilter.LengthFilter(30)};
                replyContent.setFilters(filters);
                replyContent.setText(comment.text);
                content = replyContent.getText().toString();
                content += "...";
            }else{
                content = comment.text;
            }
            if(comment.imageUrls.size() > 0){
                String temp = "["+ResFinder.getString("umeng_comm_pic")+"]";
                content += temp;
            }
            InputFilter[] filters = {new InputFilter.LengthFilter(100)};
            replyContent.setFilters(filters);
            replyContent.setText(content);
        }
        if(comment.status <= 5 && comment.status >= 2){
            replyContent.setText(ResFinder.getString("umeng_comm_context_delete"));
        }
    }
}
