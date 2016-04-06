package com.umeng.commm.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.impl.CommunitySDKImpl;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.Log;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.adapters.RecommendTopicAdapter;
import com.umeng.comm.ui.imagepicker.presenter.impl.FocusTopicFgPresenter;
import com.umeng.comm.ui.imagepicker.presenter.impl.RecommendTopicPresenter;
import com.umeng.commm.ui.activities.SearchTopicActivity;
import com.umeng.commm.ui.adapters.TopicAdapter;

/**
 * Created by wangfei on 15/11/27.
 */
public class FocusedTopicFragment extends TopicFragment {
    private RelativeLayout nologinDisplay, okloginDisplay;
    private Button loginBtn;
    private boolean islogin = false;

    @Override
    protected int getFragmentLayout() {
        return ResFinder.getLayout("umeng_commm_focus_topic");
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        nologinDisplay = findViewById(ResFinder.getId("umeng_comm_no_login"));
        okloginDisplay = findViewById(ResFinder.getId("umeng_comm_ok_login"));
        loginBtn = findViewById(ResFinder.getId("umeng_comm_tologin"));
        islogin = CommonUtils.isLogin(getActivity());
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommunitySDKImpl.getInstance().login(getActivity(), new LoginListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(int stCode, CommUser userInfo) {
                        islogin = true;
                        mPresenter.loadDataFromServer();
                        switchViewWithLoginStatus();
                    }
                });
//                });
            }
        });
        switchViewWithLoginStatus();
        initReceiver();
    }

    private void switchViewWithLoginStatus(){
        if(!isAdded()){
            return;
        }
        if (CommonUtils.isLogin(getActivity())) {
            if(okloginDisplay.getVisibility() == View.GONE){
                okloginDisplay.setVisibility(View.VISIBLE);
                nologinDisplay.setVisibility(View.GONE);
            }
        } else {
            if(nologinDisplay.getVisibility() == View.GONE){
                okloginDisplay.setVisibility(View.GONE);
                nologinDisplay.setVisibility(View.VISIBLE);
            }
        }
    }

//    protected void initSearchView(View rootView) {
//        View headerView = LayoutInflater.from(getActivity()).inflate(
//                ResFinder.getLayout("umeng_comm_search_header_view"), null);
//        headerView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (CommonUtils.isLogin(getActivity())) {
//                    Intent intent = new Intent(getActivity(), SearchTopicActivity.class);
//                    getActivity().startActivity(intent);
//                }
//            }
//        });
//        TextView searchtv = (TextView) headerView.findViewById(ResFinder.getId("umeng_comm_comment_send_button"));
//        searchtv.setText(ResFinder.getString("umeng_comm_search_topic"));
//        mSearchLayout = findViewById(ResFinder.getId("umeng_comm_topic_search_title_layout"));
//        mSearchLayout.setVisibility(View.GONE);
//        mTopicListView.addHeaderView(headerView);
//
//    }

    @Override
    protected RecommendTopicPresenter createPresenters() {
        return new FocusTopicFgPresenter(this);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.CANCEL_FOLLOWED_TOPIC);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("boradcast", "getting messag");
                mAdapter.getDataSource().remove(intent.getParcelableExtra("topic"));
                mAdapter.notifyDataSetChanged();
            }
        }, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (islogin) {
            okloginDisplay.setVisibility(View.VISIBLE);
            nologinDisplay.setVisibility(View.GONE);
        } else {
            okloginDisplay.setVisibility(View.GONE);
            nologinDisplay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initAdapter() {
        mAdapter = new TopicAdapter(getActivity());
        ((TopicAdapter) mAdapter).setFollowListener(new RecommendTopicAdapter.FollowListener<Topic>() {

            @Override
            public void onFollowOrUnFollow(Topic topic, ToggleButton toggleButton,
                                           boolean isFollow) {
                if (isFollow) {
                    mPresenter.followTopic(topic, toggleButton);
                } else {
                    mPresenter.cancelFollowTopic(topic, toggleButton);
                    getBindDataSource().remove(topic);
                }
            }
        });
        mTopicListView.setAdapter(mAdapter);
        setAdapterGotoDetail();
    }

    @Override
    public void onRefreshEnd() {
        super.onRefreshEnd();
        switchViewWithLoginStatus();
    }
}
