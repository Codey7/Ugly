package com.umeng.commm.ui.fragments;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.adapters.RecommendTopicAdapter;
import com.umeng.commm.ui.adapters.TopicAdapter;
import com.umeng.commm.ui.presenter.impl.CategoryTopicPresenter;

/**
 * Created by wangfei on 15/12/22.
 */
public class CategoryTopicFragment extends RecommendTopicFragment{
    protected View mSearchLayout;
    private EditText mSearchEdit;
    private boolean mIsBackup = false;
    private InputMethodManager mInputMan;
    private int whereFrom;// 1 我关注的  2. all
    private boolean isFirstCreate = true;
    public CategoryTopicFragment() {
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
    protected CategoryTopicPresenter createPresenters() {
        return new CategoryTopicPresenter(this);
    }

    @Override
    protected void initTitleView(View rootView) {

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
        mSearchLayout = findViewById(ResFinder.getId("umeng_comm_topic_search_title_layout"));
        mSearchLayout.setVisibility(View.GONE);
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
