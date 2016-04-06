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

package com.umeng.commm.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.adapters.RecommendTopicAdapter;
import com.umeng.comm.ui.imagepicker.presenter.impl.RecommendTopicPresenter;
import com.umeng.commm.ui.activities.SearchTopicActivity;
import com.umeng.commm.ui.adapters.TopicAdapter;

/**
 * 主页的三个tab中的话题页面
 */
public class TopicFragment extends RecommendTopicFragment {

    protected View mSearchLayout;
    private EditText mSearchEdit;
    private boolean mIsBackup = false;
    private InputMethodManager mInputMan;
    private int whereFrom;// 1 我关注的  2. all
    private boolean isFirstCreate = true;
    public TopicFragment() {
        super();
    }

    public static TopicFragment newTopicFragment() {
        return new TopicFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return ResFinder.getLayout("umeng_commm_topic_search");
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
//        initRefreshView(mRootView);
        initSearchView(mRootView);
//        initTitleView(mRootView);
        mInputMan = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected RecommendTopicPresenter createPresenters() {
        return new RecommendTopicPresenter(this);
    }

    @Override
    protected void initTitleView(View rootView) {
//        int searchButtonResId = ResFinder.getId("umeng_comm_topic_search");
//        rootView.findViewById(searchButtonResId).setOnClickListener(
//                new Listeners.LoginOnViewClickListener() {
//
//                    @Override
//                    protected void doAfterLogin(View v) {
//                        mInputMan.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
//                        mAdapter.backupData();
//                        ((TopicFgPresenter) mPresenter).executeSearch(mSearchEdit
//                                .getText().toString().trim());
//                    };
//                });
//        rootView.findViewById(ResFinder.getId("umeng_comm_back")).setVisibility(View.GONE);
//        int paddingRight = DeviceUtils.dp2px(getActivity(), 10);
//        int paddingLeft = mSearchEdit.getPaddingLeft();
//        int paddingTop = mSearchEdit.getPaddingTop();
//        int paddingBottom = mSearchEdit.getPaddingBottom();
//        mSearchEdit.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new TopicAdapter(getActivity());
        ((TopicAdapter) mAdapter).setFollowListener(new RecommendTopicAdapter.FollowListener<Topic>() {

            @Override
            public void onFollowOrUnFollow(Topic topic, ToggleButton toggleButton,
                    boolean isFollow) {
                mPresenter.checkLoginAndExecuteOp(topic, toggleButton, isFollow);
            }
        });
        mTopicListView.setAdapter(mAdapter);
        setAdapterGotoDetail();
    }

    /**
     * 初始化搜索话题View跟事件处理</br>
     * 
     * @param rootView
     */
    protected void initSearchView(View rootView) {
        View headerView = LayoutInflater.from(getActivity()).inflate(
                ResFinder.getLayout("umeng_comm_search_header_view"), null);
        headerView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), SearchTopicActivity.class);
                    getActivity().startActivity(intent);

            }
        });
        TextView searchtv = (TextView) headerView.findViewById(ResFinder.getId("umeng_comm_comment_send_button"));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)searchtv.getLayoutParams();
        params.topMargin = 0;
        params.bottomMargin = CommonUtils.dip2px(getActivity(), 7);
        searchtv.setText(ResFinder.getString("umeng_comm_search_topic"));
        mSearchLayout = findViewById(ResFinder.getId("umeng_comm_topic_search_title_layout"));
        mSearchLayout.setVisibility(View.GONE);
        mTopicListView.addHeaderView(headerView);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (isFirstCreate) {
                isFirstCreate = false; // 隐藏且为第一次创建Fragment（初始化时isVisibleToUser为不可见）
            } else {
                if(mInputMan!=null&&mSearchEdit!=null) {
                    mInputMan.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0); // 隐藏且是非第一次创建，则隐藏软键盘
                }
                }
        }
    }

    @Override
    protected void initRefreshView(View rootView) {
        super.initRefreshView(rootView);
        mRefreshLvLayout.setProgressViewOffset(false, 60,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mRefreshLvLayout.setRefreshing(true);
        mBaseView.setEmptyViewText(ResFinder.getString("umeng_comm_no_topic"));
    }

    @Override
    public void onPause() {
        if(mInputMan!=null&&mSearchEdit!=null) {
            mInputMan.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
        }
        super.onPause();
    }
}
