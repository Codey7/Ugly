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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.beans.ImageItem;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.constants.HttpProtocol;
import com.umeng.comm.core.utils.Log;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.core.utils.TimeUtils;
import com.umeng.comm.core.utils.ToastMsg;
import com.umeng.commm.ui.activities.FeedDetailActivity;
import com.umeng.commm.ui.adapters.viewholders.CommentMessageViewHolder;
import com.umeng.commm.ui.adapters.viewholders.CommentMsgViewHolder;
import com.umeng.commm.ui.adapters.viewholders.FeedItemViewHolder;

import java.util.Date;

/**
 * 评论我的ListView Adapter
 */
public class CommentMeAdapter extends CommentMessageAdapter {

    public boolean showReplyBtn = false;

    public CommentMeAdapter(Context context) {
        super(context);
    }

    public CommentMeAdapter(Context context, boolean showReply) {
        super(context);
        showReplyBtn = showReply;
    }

    @Override
    protected void setItemData(final int position, FeedItemViewHolder view, View rootView) {
        CommentMsgViewHolder holder = (CommentMsgViewHolder)view;
        super.setItemData(position, holder, rootView);
        if (holder.mImageGv != null) {
            holder.mImageGv.setVisibility(View.GONE);
        }
        holder.mButtomLayout.setVisibility(View.GONE);
        // 更新视图布局参数
        updateShareTextViewParams(holder.mShareBtn);
        // 更新时间
        Date date = new Date(Long.parseLong(getItem(position).publishTime));
        holder.mShareBtn.setText(TimeUtils.format(date));
        holder.mFeedTextTv.setOnClickListener(null);
        if (!showReplyBtn) {
            return;
        }
        holder.mShareBtn.setCompoundDrawablePadding(10);
        holder.mShareBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                ResFinder.getDrawable("umeng_comm_reply"));
        holder.mShareBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FeedItem feedItem = restoreFeedItem(getItem(position).sourceFeed);
                if (feedItem.status >= FeedItem.STATUS_SPAM&&feedItem.status != FeedItem.STATUS_LOCK) {
                    ToastMsg.showShortMsgByResName("umeng_comm_discuss_invalid_feed");
                    return;
                }
                Intent intent = new Intent(mContext, FeedDetailActivity.class);
                intent.setExtrasClassLoader(ImageItem.class.getClassLoader());
                intent.putExtra(Constants.FEED, feedItem);
                String commentId = feedItem.extraData.getString(HttpProtocol.COMMENT_ID_KEY);
                // 传递评论的id
                intent.putExtra(HttpProtocol.COMMENT_ID_KEY, commentId);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 获取原始的Feed内容,被@的消息中含有creator的用户名
     * 
     * @param originFeedItem
     * @return
     */
    private FeedItem restoreFeedItem(FeedItem originFeedItem) {
        FeedItem feedItem = originFeedItem.clone();
        feedItem.text = feedItem.text.split(":")[1];
        return feedItem;
    }


    @SuppressWarnings("deprecation")
    private void updateShareTextViewParams(TextView textView) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        params.width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        textView.setLayoutParams(params);
        textView.setBackgroundDrawable(null);
    }

    @Override
    protected CommentMessageViewHolder createViewHolder() {
        Log.d("viewholder","created view holder");
        return new CommentMsgViewHolder();
    }
}
