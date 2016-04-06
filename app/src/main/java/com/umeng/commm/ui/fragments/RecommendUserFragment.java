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

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.listeners.Listeners.OnResultListener;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.presenter.impl.ActiveUserFgPresenter;
import com.umeng.comm.ui.imagepicker.presenter.impl.RecommendUserFgPresenter;
import com.umeng.comm.ui.imagepicker.util.BroadcastUtils;
import com.umeng.comm.ui.imagepicker.widgets.RefreshLayout;

import java.util.List;


/**
 * 用户推荐页面
 */
public class RecommendUserFragment extends ActiveUserFragment implements OnClickListener {

    private boolean mSaveButtonVisiable = true;
    private ViewStub mViewStub;
    private View mEmptyView;
    protected TextView mTitleTextView;

    @Override
    protected int getFragmentLayout() {
        return ResFinder.getLayout("umeng_commm_recommend_user_layout");
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        nextButton = (Button) mRootView.findViewById(ResFinder.getId("umeng_comm_save_bt"));
        nextButton.setOnClickListener(this);
        nextButton.setText(ResFinder.getString("umeng_comm_skip"));
        nextButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        nextButton.setTextColor(ResFinder.getColor("umeng_comm_skip_text_color"));
        if (!mSaveButtonVisiable) {
            nextButton.setVisibility(View.GONE);
            mRootView.findViewById(ResFinder.getId("umeng_comm_setting_back")).setOnClickListener(
                    this);
            mAdapter.setFromFindPage(!mSaveButtonVisiable);
        } else {
            mRootView.findViewById(ResFinder.getId("umeng_comm_setting_back")).setVisibility(
                    View.GONE);
        }
        mTitleTextView = (TextView) mRootView.findViewById(ResFinder
                .getId("umeng_comm_setting_title"));
        mTitleTextView.setText(ResFinder.getString("umeng_comm_recommend_user"));
        mTitleTextView.setTextColor(Color.BLACK);
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        mRootView.findViewById(ResFinder.getId("umeng_comm_title_bar_root"))
                .setBackgroundColor(Color.WHITE);

        mRefreshLvLayout.setEnabled(true);
        mViewStub = (ViewStub) mRootView.findViewById(ResFinder.getId("umeng_comm_empty"));

        mRefreshLvLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {
                mPresenter.loadMoreData();
            }
        });

        mRefreshLvLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                mPresenter.loadDataFromServer();
            }
        });
        BroadcastUtils.registerUserBroadcast(getActivity(), mReceiver);
    }

    @Override
    protected ActiveUserFgPresenter createPresenters() {
        return new RecommendUserFgPresenter(this);
    }
    private BroadcastUtils.DefalutReceiver mReceiver = new BroadcastUtils.DefalutReceiver() {
        public void onReceiveUser(android.content.Intent intent) {
            CommUser newUser = getUser(intent);
            BroadcastUtils.BROADCAST_TYPE type = getType(intent);
            boolean follow = true;
            if (type == BroadcastUtils.BROADCAST_TYPE.TYPE_USER_FOLLOW) {
                follow = true;
            } else if (type == BroadcastUtils.BROADCAST_TYPE.TYPE_USER_CANCEL_FOLLOW) {
                follow = false;
            }
            List<CommUser> users = mAdapter.getDataSource();
            for (CommUser user : users) {
                if (user.id.equals(newUser.id)) {
                    user.extraData.putBoolean(Constants.IS_FOCUSED, follow);
                    break;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    };
    @Override
    public void showEmptyView() {
        if(mEmptyView == null){
            mEmptyView = mViewStub.inflate();
        }
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        if (mEmptyView != null && mEmptyView.getVisibility() == View.VISIBLE) {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == ResFinder.getId("umeng_comm_save_bt")
                || id == ResFinder.getId("umeng_comm_setting_back")) { // 跳过事件
            mResultListener.onResult(0);
        }
    }

    /**
     * 设置跳过按钮不可见。在设置页面显示推荐用户的时候不需要显示。</br>
     */
    public void setSaveButtonInvisiable() {
        mSaveButtonVisiable = false;
    }

    /**
     * 设置点击跳过时得回调</br>
     * 
     * @param listener
     */
    public void setOnResultListener(OnResultListener listener) {
        mResultListener = listener;
    }

    /**
     * 默认逻辑。点击跳过时销毁该Activity
     */
    private OnResultListener mResultListener = new OnResultListener() {

        @Override
        public void onResult(int status) {
            getActivity().finish();
        }
    };

}
