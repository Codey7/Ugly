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

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.constants.HttpProtocol;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.core.utils.ToastMsg;
import com.umeng.comm.ui.imagepicker.mvpview.MvpSearchFgView;
import com.umeng.comm.ui.imagepicker.presenter.impl.SearchPresenter;
import com.umeng.comm.ui.imagepicker.util.BroadcastUtils;
import com.umeng.comm.ui.imagepicker.widgets.EmptyView;
import com.umeng.commm.ui.activities.RelativeUserActivity;
import com.umeng.commm.ui.activities.SearchActivity;
import com.umeng.commm.ui.activities.SearchTopicActivity;
import com.umeng.commm.ui.adapters.SearchUsersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * æœç´¢Fragment
 */
public class SearchFragment extends FeedListFragment<SearchPresenter> implements
        OnClickListener, MvpSearchFgView {

    private RecyclerView mRecyclerView;
    private View mMoreView;
    private SearchUsersAdapter mAdapter;
    private EmptyView mUserEmptyView;
    private EmptyView mFeedEmptyView;
    private EditText mSearchEditText;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getFragmentLayout() {
        return ResFinder.getLayout("umeng_commm_search");
    }

    @Override
    protected SearchPresenter createPresenters() {
        return new SearchPresenter(this);
    }
    public void executeSearch(){
        String keyword = mSearchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            ToastMsg.showShortMsgByResName("umeng_comm_topic_search_no_keyword");
            return;
        }
        ((SearchPresenter) mPresenter).executeSearch(mSearchEditText
                .getText().toString());
    }

    @Override
    protected void initViews() {
        super.initViews();
        mRecyclerView = (RecyclerView) mRootView.findViewById(ResFinder
                .getId("umeng_comm_relative_user_recyclerView"));
        mAdapter = new SearchUsersAdapter(getActivity());
        mAdapter.getDataSource().clear();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mMoreView = mRootView.findViewById(ResFinder.getId("search_more_img_view"));
        mMoreView.setOnClickListener(this);
        // éšè—å‘é€æŒ‰é’®
        mPostBtn.setVisibility(View.GONE);
        showRelativeUserView(null);

        mRootView.findViewById(ResFinder.getId("umeng_comm_back")).setOnClickListener(this);

        mRootView.findViewById(ResFinder.getId("umeng_comm_topic_search")).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                                String keyword = mSearchEditText.getText().toString().trim();
                        if (TextUtils.isEmpty(keyword)) {
                            ToastMsg.showShortMsgByResName("umeng_comm_topic_search_no_keyword");
                            return;
                        }
                        ((SearchPresenter) mPresenter).executeSearch(mSearchEditText
                                .getText().toString());
                    }

                }
//                new Listeners.LoginOnViewClickListener() {
//
//                    @Override
//                    protected void doAfterLogin(View v) {
//                        String keyword = mSearchEditText.getText().toString().trim();
//                        if (TextUtils.isEmpty(keyword)) {
//                            ToastMsg.showShortMsgByResName("umeng_comm_topic_search_no_keyword");
//                            return;
//                        }
//                        ((SearchPresenter) mPresenter).executeSearch(mSearchEditText
//                                .getText().toString());
//                    }
//                }
        );

        mSearchEditText = (EditText) mRootView.findViewById(ResFinder
                .getId("umeng_comm_topic_edittext"));
        mSearchEditText.setHint(ResFinder.getString("umeng_comm_search_content"));
        mSearchEditText.requestFocus();
        // showInputMethod();

        mUserEmptyView = (EmptyView) mRootView.findViewById(ResFinder
                .getId("umeng_comm_user_empty"));
        mUserEmptyView.setShowText("umeng_comm_no_search_user");
        mFeedEmptyView = (EmptyView) mRootView.findViewById(ResFinder
                .getId("umeng_comm_feed_empty"));
        mFeedEmptyView.setShowText("umeng_comm_no_search_feed");
//        mSearchEditText.setOnEditorActionListener(new OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId != EditorInfo.IME_ACTION_GO
//                        && actionId != EditorInfo.IME_ACTION_SEARCH) {
//                    return false;
//                }
//                final String text = mSearchEditText.getText().toString();
//                if (TextUtils.isEmpty(text)) {
//                    ToastMsg.showShortMsgByResName("umeng_comm_search_content");
//                    return false;
//                }
//                CommonUtils.checkLoginAndFireCallback(getActivity(),
//                        new SimpleFetchListener<LoginResponse>() {
//
//                            @Override
//                            public void onComplete(LoginResponse response) {
//                                if (response.errCode == ErrorCode.NO_ERROR) {
//                                    ((SearchPresenter) mPresenter).executeSearch(text);
//                                } else {
//                                    Log.d("", "### login failed...");
//                                }
//                            }
//                        });
//                return false;
//            }
//        });
        showInputMethod();
        mRefreshLayout.setEnabled(false);
        mLinearLayout.setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onBaseResumeDeal() {

    }

    @Override
    protected void registerBroadcast() {
        BroadcastUtils.registerUserBroadcast(getActivity(), mReceiver);
        BroadcastUtils.registerFeedBroadcast(getActivity(), mReceiver);
        BroadcastUtils.registerFeedUpdateBroadcast(getActivity(), mReceiver);
    }

    /**
     * æ·»åŠ ç›¸å…³ç”¨æˆ·æ˜¾ç¤º</br>
     *
     * @param users
     */
    @Override
    public void showRelativeUserView(List<CommUser> users) {
        mAdapter.getDataSource().clear();
        if (users == null || users.size() == 0) {
            mMoreView.setVisibility(View.GONE);
            // invalidate
            mAdapter.notifyDataSetChanged();
            findViewById(ResFinder.getId("umeng_comm_relative_user")).setBackgroundColor(ResFinder.getColor("umeng_comm_feed_list_bg"));
            return;
        }
        int itemWidth = mAdapter.computeWidth();
        LayoutParams params = mRecyclerView.getLayoutParams();
        if (users.size() > 4) {
            mMoreView.setVisibility(View.VISIBLE);
            params.width = (SearchUsersAdapter.MAX_SHOW_NUM - 1) * itemWidth;
        } else {
            params.width = users.size() * itemWidth;
            mMoreView.setVisibility(View.GONE);
        }
        mRecyclerView.setLayoutParams(params);
        mRecyclerView.invalidate();
        mAdapter.getDataSource().addAll(users);
        mAdapter.notifyDataSetChanged();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(400);
        alphaAnimation.setFillAfter(true);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(
                alphaAnimation, 0.4f);
        mRecyclerView.setLayoutAnimation(layoutAnimationController);
        findViewById(ResFinder.getId("umeng_comm_relative_user")).setBackgroundColor(ResFinder.getColor("umeng_comm_feed_list_bg"));

        mRecyclerView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    ((SearchPresenter) mPresenter).loadMoreUser();
                }
            }
        });
    }

    @Override
    public void onPause() {
        hideInputMethod();
        super.onPause();
    }

    /**
     * éšè—è¾“å…¥æ³•</br>
     */
    @Override
    public void hideInputMethod() {
        if(getActivity() instanceof SearchActivity){
            ((SearchActivity) getActivity()).hideInputMethod(mSearchEditText);
        }else if(getActivity() instanceof SearchTopicActivity){
            ((SearchTopicActivity) getActivity()).hideInputMethod(mSearchEditText);
        }
    }

    /**
     * æ˜¾ç¤ºè¾“å…¥æ³•</br>
     */
    private void showInputMethod() {
        if(getActivity() instanceof SearchActivity){
            ((SearchActivity) getActivity()).showInputMethod(mSearchEditText);
        }else if (getActivity() instanceof  SearchTopicActivity){
            ((SearchTopicActivity) getActivity()).showInputMethod(mSearchEditText);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == ResFinder.getId("umeng_comm_back")) {// è¿”å›žäº‹ä»¶
            hideInputMethod();
            getActivity().finish();
        } else if (id == mMoreView.getId()) {// ç‚¹å‡»æ›´å¤šæŒ‰é’®
            // è·³è½¬åˆ°ç›¸å…³ç”¨æˆ·é¡µé¢
            Intent intent = new Intent(getActivity(), RelativeUserActivity.class);
            Bundle bundle = new Bundle();
            ArrayList<CommUser> users = new ArrayList<CommUser>(mAdapter.getDataSource());
            bundle.putParcelableArrayList(Constants.TAG_USERS, users);
            String nextUrl = ((SearchPresenter) mPresenter).getUserNextUrl();
            bundle.putString(HttpProtocol.NAVIGATOR_KEY, nextUrl);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onRefreshStart() {
        mRefreshLayout.setLoading(true);
    }

    @Override
    public void onRefreshEnd() {
        mRefreshLayout.setLoading(false);

    }

    @Override
    public List<CommUser> getUserDataSource() {
        return mAdapter.getDataSource();
    }

    @Override
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
        mFeedLvAdapter.notifyDataSetChanged();
        mLinearLayout.setBackgroundColor(ResFinder.getColor("umeng_comm_feed_list_bg"));
    }

    @Override
    public void showFeedEmptyView() {

        mFeedEmptyView.show();
    }

    @Override
    public void hideFeedEmptyView() {

        mFeedEmptyView.hide();
    }

    @Override
    public void showUserEmptyView() {

        mUserEmptyView.show();
    }

    @Override
    public void hideUserEmptyView() {
        mUserEmptyView.hide();
    }

    @Override
    public void clearListView() {
        super.clearListView();
        mAdapter.getDataSource().clear();
        mAdapter.notifyDataSetChanged();
    }

}