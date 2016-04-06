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

package com.umeng.comm.ui.imagepicker.presenter.impl;

import android.text.TextUtils;

import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.nets.responses.FeedsResponse;
import com.umeng.comm.core.nets.uitls.NetworkUtils;
import com.umeng.comm.ui.imagepicker.mvpview.MvpFeedView;

import java.util.List;

/**
 * 用于获取微社区内的Feed的Presenter,用户没有关注的人、话题的Feed也会一并返回
 */
public class RealTimeFeedPresenter extends FriendFeedPresenter {
    /**
     * @param feedViewInterface
     */
    public RealTimeFeedPresenter(MvpFeedView feedViewInterface) {
        super(feedViewInterface);
    }

    @Override
    public void loadDataFromServer() {
        mCommunitySDK.fetchRealTimeFeed(mRefreshListener);
    }

    @Override
    public void loadDataFromDB() {
    }

    public void loadMoreData() {
        if (TextUtils.isEmpty(mNextPageUrl)) {
            mFeedView.onRefreshEnd();
            return;
        }
        mCommunitySDK.fetchNextPageData(mNextPageUrl, FeedsResponse.class, mRefreshListener);
    }

    @Override
    protected void beforeDeliveryFeeds(FeedsResponse response) {
        response.result = response.resultWithoutTop;
    }
    protected Listeners.FetchListener<FeedsResponse> mRefreshListener = new Listeners.SimpleFetchListener<FeedsResponse>() {

        @Override
        public void onStart() {
            mFeedView.onRefreshStart();
        }

        // [注意]：mFeedView.onRefreshEnd方法不可提前统一调用，该方法会被判断是否显示空视图的逻辑
        @Override
        public void onComplete(FeedsResponse response) {
            // 根据response进行Toast
            if (NetworkUtils.handleResponseAll(response)) {
                mFeedView.onRefreshEnd();
                return;
            }


            // 对于下拉刷新，仅在下一个地址为空（首次刷新）时设置下一页地址
            if (TextUtils.isEmpty(mNextPageUrl) && isNeedRemoveOldFeeds.get()) {
                mNextPageUrl = response.nextPageUrl;
            }
            beforeDeliveryFeeds(response);
            final List<FeedItem> newFeedItems = response.result;
            for (FeedItem item:newFeedItems){
                item.isTop = 0;
            }
            // 更新数据
            int news = appendFeedItemsToHeader(newFeedItems);
            if (mOnResultListener != null) {
                mOnResultListener.onResult(news);
            }
            // 保存加载的数据。如果该数据存在于DB中，则替换成最新的，否则Insert一条新纪录
            saveDataToDB(newFeedItems);


            mFeedView.onRefreshEnd();
        }
    };
}
