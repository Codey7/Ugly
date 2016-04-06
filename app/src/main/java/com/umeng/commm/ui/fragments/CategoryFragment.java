package com.umeng.commm.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.comm.core.beans.Category;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.adapters.BackupAdapter;
import com.umeng.comm.ui.imagepicker.adapters.RecommendTopicAdapter;
import com.umeng.comm.ui.imagepicker.fragments.BaseFragment;
import com.umeng.comm.ui.imagepicker.mvpview.MvpCategoryView;
import com.umeng.comm.ui.imagepicker.presenter.impl.CategoryPresenter;
import com.umeng.comm.ui.imagepicker.util.FontUtils;
import com.umeng.comm.ui.imagepicker.widgets.BaseView;
import com.umeng.comm.ui.imagepicker.widgets.RefreshLayout;
import com.umeng.comm.ui.imagepicker.widgets.RefreshLvLayout;
import com.umeng.commm.ui.activities.SearchTopicActivity;
import com.umeng.commm.ui.activities.TopicActivity;
import com.umeng.commm.ui.adapters.CategoryAdapter;
import com.umeng.commm.ui.adapters.TopicAdapter;

import java.util.List;

/**
 * Created by wangfei on 15/11/24.
 */
public class CategoryFragment extends BaseFragment<List<Category>, CategoryPresenter>
        implements MvpCategoryView {
    protected BackupAdapter<Topic, ?> mTopicAdapter;
    private CategoryAdapter mAdapter;
    private BackupAdapter<Topic, ?>  topicAdapter;
    protected ListView mCategoryListView;
    protected RefreshLvLayout mRefreshLvLayout;
    protected BaseView mBaseView;
    protected View mSearchLayout;
//    private EditText mSearchEdit;
    private boolean mIsBackup = false;
    private InputMethodManager mInputMan;

    private boolean isFirstCreate = true;
    public CategoryFragment() {
    }

    public static CategoryFragment newCategoryFragment() {
        return new CategoryFragment();
    }
    @Override
    protected void initWidgets() {
        super.initWidgets();
        FontUtils.changeTypeface(mRootView);
        initRefreshView(mRootView);
        initSearchView(mRootView);
        initTitleView(mRootView);
        initAdapter();
        mInputMan = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
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
//                if (CommonUtils.isLogin(getActivity())) {
                Intent intent = new Intent(getActivity(), SearchTopicActivity.class);
                getActivity().startActivity(intent);
//                }
            }
        });
        TextView  searchtv = (TextView) headerView.findViewById(ResFinder.getId("umeng_comm_comment_send_button"));
        searchtv.setText(ResFinder.getString("umeng_comm_search_topic"));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)searchtv.getLayoutParams();
        params.topMargin = 0;
        params.bottomMargin = CommonUtils.dip2px(getActivity(), 7);
        mSearchLayout = findViewById(ResFinder.getId("umeng_comm_topic_search_title_layout"));
        mSearchLayout.setVisibility(View.GONE);
        mCategoryListView.addHeaderView(headerView);
//        View headerView = LayoutInflater.from(getActivity()).inflate(
//                ResFinder.getLayout("umeng_comm_search_header_view"), null);
//        headerView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SearchTopicBaseActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });
//        mTipView = (TextView) headerView.findViewById(ResFinder.getId("umeng_comm_feeds_tips"));
//        rootView.addv(headerView);


    }

    @Override
    protected CategoryPresenter createPresenters() {
        return new CategoryPresenter(this);
    }

    /**
     * 初始化刷新相关的view跟事件</br>
     *
     * @param rootView
     */

    protected void initRefreshView(View rootView) {
        int refreshResId = ResFinder.getId("umeng_comm_topic_refersh");
        mRefreshLvLayout = (RefreshLvLayout) rootView.findViewById(refreshResId);

            mRefreshLvLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    mPresenter.loadDataFromServer();
                }
            });
            mRefreshLvLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
                @Override
                public void onLoad() {
                    mPresenter.loadMoreData();
                }
            });


        int listViewResId = ResFinder.getId("umeng_comm_topic_listview");
        mCategoryListView = mRefreshLvLayout.findRefreshViewById(listViewResId);

        mRefreshLvLayout.setDefaultFooterView();


        mBaseView = (BaseView) rootView.findViewById(ResFinder.getId("umeng_comm_baseview"));
        mBaseView.setEmptyViewText(ResFinder.getString("umeng_comm_no_topic"));
    }

        protected void initTitleView(View rootView) {
//            int searchButtonResId = ResFinder.getId("umeng_comm_topic_search");
//            rootView.findViewById(searchButtonResId).setOnClickListener(
//                    new Listeners.LoginOnViewClickListener() {
//
//                        @Override
//                        protected void doAfterLogin(View v) {
////                            mInputMan.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
////                            mAdapter.backupData();
////                            ((TopicFgPresenter) mPresenter).executeSearch(mSearchEdit
////                                    .getText().toString().trim());
//                        };
//                    });
//            rootView.findViewById(ResFinder.getId("umeng_comm_back")).setVisibility(View.GONE);
//            int paddingRight = DeviceUtils.dp2px(getActivity(), 10);
//            int paddingLeft = mSearchEdit.getPaddingLeft();
//            int paddingTop = mSearchEdit.getPaddingTop();
//            int paddingBottom = mSearchEdit.getPaddingBottom();
//            mSearchEdit.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }

    protected void initAdapter() {
        CategoryAdapter adapter = new CategoryAdapter(getActivity());

        mAdapter = adapter;

        mCategoryListView.setAdapter(mAdapter);
        mCategoryListView.setDividerHeight(CommonUtils.dip2px(getActivity(),1));
        mCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                mPresenter.loadTopic(getBindDataSource().get(i).id);
//                com.umeng.comm.core.utils.Log.e("xxxxxx", "i = " + i + "   size = " + getBindDataSource().size());
                Intent intent = new Intent(getActivity(),TopicActivity.class);
                intent.putExtra("uid",getBindDataSource().get(i-1).id);
                getActivity().startActivity(intent);
            }
        });
    }
    @Override
    protected int getFragmentLayout() {
        return ResFinder.getLayout("umeng_commm_category_recommend");
    }

    @Override
    public List<Category> getBindDataSource() {
        return mAdapter.getDataSource();
    }

    @Override
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshEndNoOP() {
        mRefreshLvLayout.setRefreshing(false);
        mRefreshLvLayout.setLoading(false);
        mBaseView.hideEmptyView();
    }

    @Override
    public void ChangeAdapter(List<Topic> list) {
        topicAdapter = new TopicAdapter(getActivity());
        ((TopicAdapter) topicAdapter).setFollowListener(new RecommendTopicAdapter.FollowListener<Topic>() {

            @Override
            public void onFollowOrUnFollow(Topic topic, ToggleButton toggleButton,
                                           boolean isFollow) {
                mPresenter.checkLoginAndExecuteOp(topic, toggleButton, isFollow);
            }
        });
        topicAdapter.addData(list);
        mCategoryListView.setAdapter(topicAdapter);
    }

    @Override
    public void onRefreshStart() {
        mRefreshLvLayout.setRefreshing(true);
    }

    @Override
    public void onRefreshEnd() {
        onRefreshEndNoOP();
        if (mAdapter.getCount() == 0) {
            mBaseView.showEmptyView();
        } else {
            mBaseView.hideEmptyView();
        }
    }

    @Override
    public void onPause() {
//        mInputMan.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
        super.onPause();
    }
}
