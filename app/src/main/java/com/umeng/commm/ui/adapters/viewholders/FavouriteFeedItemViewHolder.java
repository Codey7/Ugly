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

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.db.ctrl.impl.DatabaseAPI;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.nets.responses.SimpleResponse;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.core.utils.ToastMsg;
import com.umeng.comm.ui.imagepicker.util.BroadcastUtils;

/**
 *
 */
public class FavouriteFeedItemViewHolder extends FeedItemViewHolder {

    private ImageView mFavouriteBtn;
    private CommunitySDK mCommunitySDK;

    public FavouriteFeedItemViewHolder() {
    }

    public FavouriteFeedItemViewHolder(Context context, View rootView) {
        super(context, rootView);
    }

    @Override
    protected void inflateFromXML() {
        super.inflateFromXML();
        mFavouriteBtn = findViewById(ResFinder.getId("umeng_comm_feed_favorite_btn"));
        mFavouriteBtn.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFeedTextTv.getLayoutParams();
        params.rightMargin = CommonUtils.dip2px(mContext, 5);
        mFavouriteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFeedItem.isCollected) {
                    cancelFavoritesFeed();
                } else {
                    favoritesFeed();
                }
            }
        });

        mFeedTextTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mFeedItem.status >= FeedItem.STATUS_SPAM && mFeedItem.status != FeedItem.STATUS_LOCK) {
                    ToastMsg.showShortMsgByResName("umeng_comm_discuss_feed_spam_deleted");
                    return;
                }
                mPresenter.clickFeedItem();
            }
        });
    }

    @Override
    protected void bindFeedItemData() {
        super.bindFeedItemData();
        if (mFeedItem.isCollected) {
            mFavouriteBtn.setSelected(true);
        } else {
            mFavouriteBtn.setSelected(false);
        }
    }

    @Override
    protected void initPresenters() {
        super.initPresenters();
        mCommunitySDK = CommunityFactory.getCommSDK(mContext);
    }

    /**
     * 取消收藏feed</br>
     */
    private void cancelFavoritesFeed() {
        mCommunitySDK.cancelFavoriteFeed(mFeedItem.id,
                new Listeners.SimpleFetchListener<SimpleResponse>() {

                    @Override
                    public void onComplete(SimpleResponse response) {
                        if (response.errCode != ErrorCode.NO_ERROR) {
                            if (response.errCode == ErrorCode.ERR_CODE_FEED_NOT_FAVOURITED) {
                                mFavouriteBtn.setSelected(false);
                                ToastMsg.showShortMsgByResName("umeng_comm_not_favorited");
                                mFeedItem.category = FeedItem.CATEGORY.NORMAL;
                                mFeedItem.isCollected = false;
                                BroadcastUtils.sendFeedUpdateBroadcast(mContext, mFeedItem);
                            } else if (response.errCode == ErrorCode.USER_FORBIDDEN_ERR_CODE) {
                                ToastMsg.showShortMsgByResName("umeng_comm_user_unusable");
                            } else {
                                ToastMsg.showShortMsgByResName("umeng_comm_cancel_favorites_failed");
                            }
                        } else {
                            mFavouriteBtn.setSelected(false);
                            ToastMsg.showShortMsgByResName("umeng_comm_cancel_favorites_success");
                            mFeedItem.category = FeedItem.CATEGORY.NORMAL;
                            mFeedItem.isCollected = false;
                            DatabaseAPI.getInstance().getFeedDBAPI().saveFeedToDB(mFeedItem);
                            // 数据同步
                            BroadcastUtils.sendFeedUpdateBroadcast(mContext, mFeedItem);
                        }
                    }
                });
    }

    /**
     * 收藏Feed</br>
     */
    private void favoritesFeed() {
        mCommunitySDK.favoriteFeed(mFeedItem.id, new Listeners.SimpleFetchListener<SimpleResponse>() {

            @Override
            public void onComplete(SimpleResponse response) {
                if (response.errCode != ErrorCode.NO_ERROR) {
                    if (response.errCode == ErrorCode.ERR_CODE_FEED_FAVOURITED) {
                        mFavouriteBtn.setSelected(true);
                        ToastMsg.showShortMsgByResName("umeng_comm_has_favorited");
                        mFeedItem.category = FeedItem.CATEGORY.FAVORITES;
                        mFeedItem.isCollected = true;
                        BroadcastUtils.sendFeedUpdateBroadcast(mContext, mFeedItem);
                    } else if (response.errCode == ErrorCode.ERR_CODE_FAVOURITED_OVER_FLOW) {
                        ToastMsg.showShortMsgByResName("umeng_comm_favorites_overflow");
                    } else if (response.errCode == ErrorCode.USER_FORBIDDEN_ERR_CODE) {
                        ToastMsg.showShortMsgByResName("umeng_comm_user_unusable");
                    } else {
                        ToastMsg.showShortMsgByResName("umeng_comm_favorites_failed");
                    }
                } else {
                    mFavouriteBtn.setSelected(true);
                    ToastMsg.showShortMsgByResName("umeng_comm_favorites_success");
                    mFeedItem.category = FeedItem.CATEGORY.FAVORITES;
                    mFeedItem.isCollected = true;
                    mFeedItem.addTime = String.valueOf(System.currentTimeMillis());
                    DatabaseAPI.getInstance().getFeedDBAPI().saveFeedToDB(mFeedItem);
                    // 数据同步
                    BroadcastUtils.sendFeedUpdateBroadcast(mContext, mFeedItem);
                    BroadcastUtils.sendFeedFavouritesBroadcast(mContext, mFeedItem);
                }
            }
        });
    }
}
