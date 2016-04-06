package com.umeng.commm.ui.fragments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.nets.uitls.NetworkUtils;
import com.umeng.comm.core.utils.Log;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.presenter.impl.HottestFeedPresenter;
import com.umeng.comm.ui.imagepicker.util.BroadcastUtils;
import com.umeng.commm.ui.activities.SearchActivity;

import java.util.List;

/**
 * Created by umeng on 12/1/15.
 */
public class HotFeedsFragment extends FeedListFragment<HottestFeedPresenter> {

    HottestFeedPresenter mHottestFeedPresenter;
    private TextView button1,button2,button3,button4;
    @Override
    protected void initWidgets() {
        super.initWidgets();
        mPostBtn.setVisibility(View.GONE);
    }

    @Override
    protected void initEventHandlers() {
        super.initEventHandlers();
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        View headerView = LayoutInflater.from(getActivity()).inflate(
                ResFinder.getLayout("umeng_comm_search_header_view"), null);
        headerView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
        mFeedsListView.addHeaderView(headerView);
        initSwitchView();
    }
    public void initSwitchView(){
        View headerView = LayoutInflater.from(getActivity()).inflate(
                ResFinder.getLayout("umeng_comm_switch_button"), null);

        button1 = (TextView)headerView.findViewById(ResFinder.getId("umeng_switch_button_one"));
        button2 = (TextView)headerView.findViewById(ResFinder.getId("umeng_switch_button_two"));
        button3 = (TextView)headerView.findViewById(ResFinder.getId("umeng_switch_button_three"));
        button4 = (TextView)headerView.findViewById(ResFinder.getId("umeng_switch_button_four"));
        button4.setSelected(true);
        button1.setOnClickListener(switchListener);
        button2.setOnClickListener(switchListener);
        button3.setOnClickListener(switchListener);
        button4.setOnClickListener(switchListener);
        mLinearLayout.addView(headerView, 0);
    }
    private View.OnClickListener switchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == ResFinder.getId("umeng_switch_button_one")){
                button1.setSelected(true);
                button2.setSelected(false);
                button3.setSelected(false);
                button4.setSelected(false);
                if(NetworkUtils.isConnectedToNetwork(getActivity())){
                    ((HottestFeedPresenter)mPresenter).loadDataFromServer(1);
                }else{
                    ((HottestFeedPresenter)mPresenter).loadDataFromDB(1);
                }
            }else if(view.getId() == ResFinder.getId("umeng_switch_button_two")){
                button1.setSelected(false);
                button2.setSelected(true);
                button3.setSelected(false);
                button4.setSelected(false);
                if(NetworkUtils.isConnectedToNetwork(getActivity())){
                    ((HottestFeedPresenter)mPresenter).loadDataFromServer(3);
                }else{
                    ((HottestFeedPresenter)mPresenter).loadDataFromDB(3);
                }
            }else if(view.getId() == ResFinder.getId("umeng_switch_button_three")){
                button1.setSelected(false);
                button2.setSelected(false);
                button3.setSelected(true);
                button4.setSelected(false);
                if(NetworkUtils.isConnectedToNetwork(getActivity())){
                    ((HottestFeedPresenter)mPresenter).loadDataFromServer(7);
                }else{
                    ((HottestFeedPresenter)mPresenter).loadDataFromDB(7);
                }
            }else if(view.getId() == ResFinder.getId("umeng_switch_button_four")){
                button1.setSelected(false);
                button2.setSelected(false);
                button3.setSelected(false);
                button4.setSelected(true);
                if(NetworkUtils.isConnectedToNetwork(getActivity())){
                    ((HottestFeedPresenter)mPresenter).loadDataFromServer(30);
                }else{
                    ((HottestFeedPresenter)mPresenter).loadDataFromDB(30);
                }
            }
        }
    };
    @Override
    protected HottestFeedPresenter createPresenters() {
        mHottestFeedPresenter = new HottestFeedPresenter(this);
        return mHottestFeedPresenter;
    }
    @Override
    protected void registerBroadcast() {
        // 注册广播接收器
        BroadcastUtils.registerUserBroadcast(getActivity(), mReceiver);
       BroadcastUtils.registerFeedBroadcast(getActivity(), mReceiver);
        BroadcastUtils.registerFeedUpdateBroadcast(getActivity(), mReceiver);
    }
    @Override
    public List<FeedItem> getBindDataSource() {
        return super.getBindDataSource();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    @Override
    protected void postFeedComplete(FeedItem feedItem) {

    }
    @Override
    protected void deleteFeedComplete(FeedItem feedItem) {
        mFeedLvAdapter.getDataSource().remove(feedItem);
        mFeedLvAdapter.notifyDataSetChanged();
        updateForwardCount(feedItem, -1);
        Log.d(getTag(), "### 删除feed");
    }
    @Override
    public void clearListView() {
        super.clearListView();
    }

}
