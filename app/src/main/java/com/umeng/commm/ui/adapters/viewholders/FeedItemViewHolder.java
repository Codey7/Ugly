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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.beans.ImageItem;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.listeners.Listeners.OnResultListener;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.core.utils.TimeUtils;
import com.umeng.comm.core.utils.ToastMsg;
import com.umeng.comm.ui.imagepicker.emoji.EmojiTextView;
import com.umeng.comm.ui.imagepicker.mvpview.MvpLikeView;
import com.umeng.comm.ui.imagepicker.presenter.impl.LikePresenter;
import com.umeng.comm.ui.imagepicker.util.ViewFinder;
import com.umeng.comm.ui.imagepicker.widgets.WrapperGridView;
import com.umeng.commm.ui.activities.FeedDetailActivity;
import com.umeng.commm.ui.adapters.FeedImageAdapter;


import com.umeng.commm.ui.presenter.impl.FeedContentPresenter;


/**
 * zuhshi
 * ListView的Feed Item View解析器. ( 将试图的显示和解析解耦, 便于测试, 也便于复用. )
 */
public class FeedItemViewHolder extends ViewHolder implements MvpLikeView {

    public ImageView mFeedTypeImgView;
    public ImageView mFeedTopImgView;
    public EmojiTextView mFeedTextTv;
    public TextView mUserNameTv;

    public TextView mLocationTv;
    public LinearLayout mButtomLayout;
    public TextView mLikeCountTextView;
    public TextView mCommentCountTextView;

    public TextView mTimeTv;
    public ViewStub mImageGvViewStub;
    public WrapperGridView mImageGv;

    protected FeedItem mFeedItem;

    protected FeedContentPresenter mPresenter;
    private LikePresenter mLikePresenter;

    public View mFeedItemAnnounce;

    /**
     *
     *
     */
    public FeedItemViewHolder() {
    }

    public FeedItemViewHolder(Context context, View rootView) {
        mContext = context;
        itemView = rootView;
        mViewFinder = new ViewFinder(rootView);
        initWidgets();
    }

    @Override
    protected int getItemLayout() {
        return ResFinder.getLayout("umeng_commm_feed_lv_item");
    }

    @Override
    protected void initWidgets() {
        inflateFromXML();
        initPresenters();
    }

    protected void initPresenters() {
        mPresenter = new FeedContentPresenter();
        mPresenter.attach(mContext);
        mLikePresenter = new LikePresenter(this);
        mLikePresenter.attach(mContext);
        mLikePresenter.setFeedItem(mFeedItem);
    }

    public boolean setupImageGridView() {
        if (mFeedItem.getImages() != null && mFeedItem.getImages().size() > 0) {
            showImageGridView();
            return true;
        } else {
            hideImageGridView();
            return false;
        }
    }

    private void showImageGridView() {
        if (mImageGvViewStub.getVisibility() == View.GONE) {
            mImageGvViewStub.setVisibility(View.VISIBLE);
            int imageGvResId = ResFinder.getId("umeng_comm_msg_gridview");
            mImageGv = findViewById(imageGvResId);
            mImageGv.hasScrollBar = true;
            mImageGv.setHorizontalSpacing(CommonUtils.dip2px(mContext, 5));
            mImageGv.setVerticalSpacing(CommonUtils.dip2px(mContext, 5));
        }

        mImageGv.setBackgroundColor(Color.TRANSPARENT);
        mImageGv.setVisibility(View.VISIBLE);
        // adapter
        FeedImageAdapter gridviewAdapter = new FeedImageAdapter(mContext);
        List<ImageItem> mImgs = new ArrayList<ImageItem>();
        int imgSize = mFeedItem.getImages().size();
        for (int i = 0; i < 3; i++) {
            if (i < imgSize) {
                mImgs.add(mFeedItem.getImages().get(i));
            }else{
                mImgs.add(new ImageItem());
            }
        }
        gridviewAdapter.addDatasOnly(mImgs);
        mImageGv.setAdapter(gridviewAdapter);
        mImageGv.setNumColumns(3);
        // 图片GridView
        mImageGv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if(pos < mFeedItem.getImages().size()){
                    mPresenter.jumpToImageBrowser(mFeedItem.getImages(), pos);
                }else{
                    if (mFeedItem != null && (mFeedItem.status >= FeedItem.STATUS_SPAM
                            && mFeedItem.status!=FeedItem.STATUS_LOCK)
                            && mFeedItem.category == FeedItem.CATEGORY.FAVORITES) {
                        ToastMsg.showShortMsgByResName("umeng_comm_feed_spam_deleted");
                        return;
                    }
                    Intent intent = new Intent(mContext, FeedDetailActivity.class);
                    intent.putExtra(Constants.TAG_FEED, mFeedItem);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void hideImageGridView() {
        if (mImageGv != null) {
            mImageGv.setAdapter(new FeedImageAdapter(mContext));
            mImageGv.setVisibility(View.GONE);
        }
    }

    protected void inflateFromXML() {
        int feedTypeResId = ResFinder.getId("feed_type_img_btn");
        int textResId = ResFinder.getId("umeng_comm_feed_title");
        int userNameResId = ResFinder.getId("umeng_comm_msg_user_name");
        int timeResId = ResFinder.getId("umeng_comm_msg_time_tv");
        int locTextResId = ResFinder.getId("umeng_comm_msg_location_text");
        int gvStubResId = ResFinder.getId("umeng_comm_msg_images_gv_viewstub");
        // 公告或者好友feed的图标
        mFeedTypeImgView = findViewById(feedTypeResId);
        // 标题
        mFeedTextTv = findViewById(textResId);
        // 发布该消息的昵称
        mUserNameTv = findViewById(userNameResId);

        // feed底部的time、赞、转发、评论的父视图
        mButtomLayout = findViewById(ResFinder.getId("umeng_comm_feed_action_layout"));
        // 更新时间
        mTimeTv = findViewById(timeResId);
        // 地理位置
        mLocationTv = findViewById(locTextResId);

        mFeedTopImgView = findViewById(ResFinder.getId("feed_top_img_btn"));

        /**
         * 九宫格图片的View Stub
         */
        mImageGvViewStub = findViewById(gvStubResId);
        mLikeCountTextView = findViewById(ResFinder.getId("umeng_comm_like_tv"));
        mCommentCountTextView = findViewById(ResFinder.getId("umeng_comm_comment_tv"));

        mFeedItemAnnounce = findViewById(ResFinder.getId("umeng_comm_feed_item_announce"));
    }

    /**
     * 填充消息流ListView每项的数据
     */
    protected void bindFeedItemData() {
        if (TextUtils.isEmpty(mFeedItem.id)) {
            return;
        }
        // 设置基础信息
        setBaseFeeditemInfo();
        // 设置图片
        boolean hasImg = setupImageGridView();
        showActionBar(hasImg);
    }

    /**
     * 设置feedItem的基本信息（头像，昵称，内容、位置）</br>
     */
    protected void setBaseFeeditemInfo() {
        // 设置feed类型图标
        boolean hasTypeIcon = setTypeIcon();
        // 昵称
        mUserNameTv.setText(mFeedItem.creator.name);
        // 更新时间
        Date date = new Date(Long.parseLong(mFeedItem.publishTime));
        mTimeTv.setText(TimeUtils.format(date));
        // feed的文本内容
//        FeedViewRender.parseTopicsAndFriends(mFeedTextTv, mFeedItem);
        if(mFeedItem.title.equals("null") ||TextUtils.isEmpty(mFeedItem.title)){
            mFeedTextTv.setText(ResFinder.getString("umeng_comm_content_detail"));
        }else {

            mFeedTextTv.setText(mFeedItem.title);
        }
        RelativeLayout.LayoutParams feedItemParams = (RelativeLayout.LayoutParams) mFeedTextTv.getLayoutParams();
        feedItemParams.leftMargin = CommonUtils.dip2px(mContext, hasTypeIcon ? 5 : 10);

        // 内容为空时Text隐藏布局,这种情况出现在转发时没有文本的情况
//        if (TextUtils.isEmpty(mFeedItem.title)) {
//            mFeedTextTv.setVisibility(View.GONE);
//        } else {
            mFeedTextTv.setVisibility(View.VISIBLE);
//        }
//        mFeedTextTv.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mPresenter.clickFeedItem();
//            }
//        });
        if(mFeedItemAnnounce != null){
            if(mFeedItem.type == FeedItem.ANNOUNCEMENT_FEED){
                mFeedItemAnnounce.setVisibility(View.VISIBLE);
            }else{
                mFeedItemAnnounce.setVisibility(View.GONE);
            }
        }
    }

    private void showActionBar(boolean hasImg) {
        if (mButtomLayout == null || mLocationTv == null) {
            return;
        }
        RelativeLayout.LayoutParams buttomViewParams = (RelativeLayout.LayoutParams) mButtomLayout.getLayoutParams();
        RelativeLayout.LayoutParams locationParams = (RelativeLayout.LayoutParams) mLocationTv.getLayoutParams();
        if (hasImg) {
            buttomViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            locationParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mImageGv.setPadding(0, 0, 0, CommonUtils.dip2px(mContext, 22));
        } else {
            buttomViewParams.addRule(RelativeLayout.BELOW, ResFinder.getId("umeng_comm_msg_user_name"));
            locationParams.addRule(RelativeLayout.BELOW, ResFinder.getId("umeng_comm_msg_user_name"));
        }
        mLikeCountTextView.setText(String.valueOf(CommonUtils.getLimitedCount(mFeedItem.likeCount)));
        mCommentCountTextView.setText(String.valueOf(CommonUtils.getLimitedCount(mFeedItem.commentCount)));
        like(mFeedItem.id, mFeedItem.isLiked);

        // 地理位置信息
        if (TextUtils.isEmpty(mFeedItem.locationAddr)) {
            mLocationTv.setVisibility(View.GONE);
        } else {
            mLocationTv.setVisibility(View.VISIBLE);
            mLocationTv.setText(mFeedItem.locationAddr);
        }
    }

    /**
     * 设置feed 类型的icon</br>
     */
    protected boolean setTypeIcon() {
        Drawable drawable;
        if((mFeedItem.isTop == 1) && (mFeedItem.tag == 1)){
            drawable = ResFinder.getDrawable("umeng_comm_feed_item_top");
            mFeedTopImgView.setImageDrawable(drawable);
            mFeedTopImgView.setVisibility(View.VISIBLE);

            drawable = ResFinder.getDrawable("umeng_comm_feed_item_essential");
            mFeedTypeImgView.setImageDrawable(drawable);
            mFeedTypeImgView.setVisibility(View.VISIBLE);
        }else if (mFeedItem.isTop == 1) {
            // feed 置顶图标
            drawable = ResFinder.getDrawable("umeng_comm_feed_item_top");
            mFeedTopImgView.setImageDrawable(drawable);
            mFeedTopImgView.setVisibility(View.VISIBLE);
            mFeedTypeImgView.setVisibility(View.GONE);
        } else if (mFeedItem.tag == 1) {
            // feed 精华
            drawable = ResFinder.getDrawable("umeng_comm_feed_item_essential");
            mFeedTopImgView.setImageDrawable(drawable);
            mFeedTopImgView.setVisibility(View.VISIBLE);
            mFeedTypeImgView.setVisibility(View.GONE);
        } else {
            // 设置feed类型图标 [ 目前只标识公告类型 ]
            mFeedTopImgView.setVisibility(View.GONE);
            mFeedTypeImgView.setVisibility(View.GONE);
            return false;
        }
        return true;
    }

    public void setFeedItem(FeedItem feedItem) {
        mFeedItem = feedItem;
        mPresenter.setFeedItem(mFeedItem);
        bindFeedItemData();
    }

    public FeedItem getFeedItem() {
        return mFeedItem;
    }

    public void setOnUpdateListener(OnResultListener listener) {
        mLikePresenter.setResultListener(listener);
    }

    @Override
    public void like(String id, boolean isLiked) {
        mFeedItem.isLiked = isLiked;
    }

    @Override
    public void updateLikeView(String nextUrl) {
        mLikeCountTextView.setText(String.valueOf(CommonUtils.getLimitedCount(mFeedItem.likeCount)));
    }

}
