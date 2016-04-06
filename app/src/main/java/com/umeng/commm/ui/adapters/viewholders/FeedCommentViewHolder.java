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

package com.umeng.commm.ui.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import com.umeng.comm.core.beans.ImageItem;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.dialogs.ImageBrowser;
import com.umeng.comm.ui.imagepicker.widgets.RoundImageView;
import com.umeng.comm.ui.imagepicker.widgets.SquareImageView;

import java.util.List;

/**
 * 我的消息页面的Feed 评论解析器
 */
public class FeedCommentViewHolder extends ViewHolder {

    public RoundImageView userHeaderImageView;
    public TextView userNameTextView;
    public TextView publishTimeTextView;
    public TextView contentTextView;
    public TextView likeCountTextView;
    public View viewStubCommentView;
    public TextView mFloorView;
    public View mFeedOwnerView;
    public View mCommentView;
    public SquareImageView mImg;

    @Override
    protected int getItemLayout() {
        return ResFinder.getLayout("umeng_commm_msg_comment_item");
    }

    @Override
    protected void initWidgets() {
        contentTextView = findViewById(ResFinder
                .getId("umeng_comm_msg_comment_content"));
        userHeaderImageView = findViewById(ResFinder
                .getId("umeng_comm_msg_comment_header"));
        userNameTextView = findViewById(ResFinder
                .getId("umeng_comm_comment_name"));
        publishTimeTextView = findViewById(ResFinder

                .getId("umeng_comm_comment_time_tv"));

        likeCountTextView = findViewById(ResFinder
                .getId("umeng_comm_msg_comment_like_tv"));

        mFloorView = findViewById(ResFinder
                .getId("umeng_comm_comment_floor"));
        mFeedOwnerView = findViewById(ResFinder.getId("" +
                "umeng_comm_msg_post_owner"));

        mCommentView = findViewById(ResFinder
                .getId("umeng_comm_msg_comment_comment_tv"));

        viewStubCommentView = findViewById(ResFinder
                .getId("umeng_comm_comment_reply_vs"));

        mImg = findViewById(ResFinder.getId("uemng_comm_comment_img"));
    }
    public void setPicOnClick(final List<ImageItem> images) {

        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageBrowser mImageBrowser = new ImageBrowser(mContext);
                mImageBrowser.setImageList(images, 0);
                mImageBrowser.show();
            }
        });
    }
}