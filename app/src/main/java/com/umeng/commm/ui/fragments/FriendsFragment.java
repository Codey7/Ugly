
package com.umeng.commm.ui.fragments;

import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.listeners.Listeners.OnResultListener;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.presenter.impl.FriendFeedPresenter;
import com.umeng.comm.ui.imagepicker.widgets.BaseView;

import java.util.Iterator;
import java.util.List;

/**
 * 我的关注页面
 */
public class FriendsFragment extends FeedListFragment<FriendFeedPresenter> {

    protected OnResultListener mListener;
    protected BaseView mBaseView = null;

    @Override
    protected int getFragmentLayout() {
        return ResFinder.getLayout("umeng_commm_friends_frag");
    }

    @Override
    protected FriendFeedPresenter createPresenters() {
        return new FriendFeedPresenter(this);
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        mFeedsListView.setFooterDividersEnabled(false);
        // 隐藏发送跟设置按钮
        mPostBtn.setVisibility(View.GONE);
        mRootView.findViewById(ResFinder.getId("umeng_comm_title_setting_btn")).setVisibility(
                View.GONE);
        // 将标题改为朋友圈
        TextView titleTextView = (TextView) mRootView.findViewById(ResFinder
                .getId("umeng_comm_title_tv"));
        titleTextView.setText(ResFinder.getString("umeng_comm_recommend_friends"));
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        // 处理返回事件，显示发现页面
        mRootView.findViewById(ResFinder.getId("umeng_comm_title_back_btn")).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mListener.onResult(0);
                    }
                });

        mBaseView = (BaseView) mRootView.findViewById(ResFinder.getId("umeng_comm_baseview"));
        mFeedsListView.setClipToPadding(false);
        mFeedsListView.setPadding(0, CommonUtils.dip2px(getActivity(), 6), 0, 0);
    }

    public void setOnResultListener(OnResultListener listener) {
        this.mListener = listener;
    }

    protected void checkListViewData() {
        if (mFeedLvAdapter.getCount() == 0) {
            mBaseView.showEmptyView();
        } else {
            mBaseView.hideEmptyView();
        }
    }

    @Override
    public void onRefreshEnd() {
        super.onRefreshEnd();
        checkListViewData();
    }

    @Override
    protected void onCancelFollowUser(CommUser user) {
        super.onCancelFollowUser(user);
        List<FeedItem> items = mFeedLvAdapter.getDataSource();
        Iterator<FeedItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            FeedItem item = iterator.next();
            if (item.creator.id.equals(user.id)) {
                iterator.remove();
            }
        }
        mFeedLvAdapter.notifyDataSetChanged();
    }

    public static FriendsFragment newFriendsFragment() {
        return new FriendsFragment();
    }

    @Override
    protected void postFeedComplete(FeedItem feedItem) {
        updateForwardCount(feedItem, 1);
    }
}
