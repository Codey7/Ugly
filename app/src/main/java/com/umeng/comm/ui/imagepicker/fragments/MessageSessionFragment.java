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

package com.umeng.comm.ui.imagepicker.fragments;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.MessageCount;
import com.umeng.comm.core.beans.MessageSession;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.activities.MessageChatActivity;
import com.umeng.comm.ui.imagepicker.adapters.MessageSessionAdapter;
import com.umeng.comm.ui.imagepicker.mvpview.MvpMessageSessionView;
import com.umeng.comm.ui.imagepicker.presenter.impl.MessageSessionPresenter;
import com.umeng.comm.ui.imagepicker.widgets.RefreshLayout;
import com.umeng.comm.ui.imagepicker.widgets.RefreshLvLayout;


import java.util.List;

/**
 * 消息通知Fragment
 */
public class MessageSessionFragment extends BaseFragment<List<MessageSession>, MessageSessionPresenter>
        implements MvpMessageSessionView {

    ListView mListView;
    MessageSessionAdapter mAdapter;
    RefreshLvLayout mRefreshLayout;

    @Override
    protected int getFragmentLayout() {
        return ResFinder.getLayout("umeng_commm_notify_fragment");
    }

    @Override
    protected MessageSessionPresenter createPresenters() {
        return new MessageSessionPresenter(this);
    }

    @Override
    protected void initWidgets() {

        mRefreshLayout = findViewById(ResFinder.getId("umeng_comm_swipe_layout"));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mPresenter.loadDataFromServer();
            }
        });
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                mPresenter.loadMoreData();
            }
        });
        // 添加footer
        mRefreshLayout.setDefaultFooterView();
        mListView = findViewById(ResFinder.getId("umeng_comm_notify_listview"));

        mAdapter = new MessageSessionAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new Listeners.OnItemClickLoginListener() {
            @Override
            protected void doAfterLogin(View v, int position) {
                MessageSession session = mAdapter.getDataSource().get(position);
                jumpToChatPage(session);
            }
        });
    }

    private void jumpToChatPage(MessageSession session){
        Intent i = new Intent(getActivity(),MessageChatActivity.class);
        i.putExtra("uid", session.user.id);
        i.putExtra("userName", session.user.name);
        getActivity().startActivity(i);

        updateUnReadSeesionCount(session);
    }

    @Override
    public void onRefreshEnd() {
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setLoading(false);
    }

    @Override
    public List<MessageSession> getBindDataSource() {
        return mAdapter.getDataSource();
    }

    @Override
    public void notifyDataSetChange() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshStart() {
    }

    private void updateUnReadSeesionCount(MessageSession session){
        int count = Integer.parseInt(session.unReadConut);
        session.unReadConut = "0";
        mAdapter.notifyDataSetChanged();
        MessageCount mUnreadMsg = CommConfig.getConfig().mMessageCount;
        mUnreadMsg.unReadSessionsCount -= count;
        if(mUnreadMsg.unReadSessionsCount < 0){
            mUnreadMsg.unReadSessionsCount = 0;
        }
        mUnreadMsg.unReadTotal = mUnreadMsg.unReadAtCount + mUnreadMsg.unReadCommentsCount
                + mUnreadMsg.unReadLikesCount + mUnreadMsg.unReadNotice +
                mUnreadMsg.newFansCount + mUnreadMsg.unReadSessionsCount;
    }
}
