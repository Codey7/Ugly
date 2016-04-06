package com.umeng.commm.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.presenter.impl.HottestFeedPresenter;

/**
 * Created by wangfei on 15/11/26.
 */
public class TopicMainFragment extends Fragment  {
//    private Button mFocusBtn,mRecommentBtn,mAllBtn;
    private ViewPager viewPager;
    /**
     * 布局加载LayoutInflater
     */
    protected LayoutInflater mLayoutInflater;
    /**
     * 根视图
     */
    protected View mRootView;
    private TopicFragment RecommentFragment;
    private FocusedTopicFragment myFocusFragment;
    private CategoryFragment categoryFragment;
    private Fragment mCurrentFragment;
    private FragmentTransaction transaction;
    public static  int loadType = 0;//0 我关注的 1 所有话题 2加载分类下话题
    private TextView button1,button3,button4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CommonUtils.saveComponentImpl(getActivity());// 注意此处必须保存登录组件的信息
        mLayoutInflater = inflater;
        mRootView = mLayoutInflater.inflate(ResFinder.getResourceId(ResFinder.ResType.LAYOUT,"umeng_comm_main_topic"), container, false);
        RecommentFragment = new TopicFragment();
        myFocusFragment = new FocusedTopicFragment();
        categoryFragment = new CategoryFragment();
//        mFocusBtn = (Button)mRootView.findViewById(ResFinder.getResourceId(ResFinder.ResType.ID,"umeng_comm_focus_button"));
//        mRecommentBtn = (Button)mRootView.findViewById(ResFinder.getResourceId(ResFinder.ResType.ID,"umeng_comm_recommend_button"));
//        mAllBtn = (Button)mRootView.findViewById(ResFinder.getResourceId(ResFinder.ResType.ID,"umeng_comm_all_button"));
//        viewPager = (ViewPager)mRootView.findViewById(ResFinder.getResourceId(ResFinder.ResType.ID,"umeng_comm_topic_viewpager"));
        getFragmentManager().beginTransaction().replace(ResFinder.getResourceId(ResFinder.ResType.ID,"id_content"),categoryFragment).commit();
        initSwitchView();
        return mRootView;
    }
    public void initSwitchView(){
        button1 = (TextView)mRootView.findViewById(ResFinder.getId("umeng_switch_button_one"));
        button3 = (TextView)mRootView.findViewById(ResFinder.getId("umeng_switch_button_three"));
        button4 = (TextView)mRootView.findViewById(ResFinder.getId("umeng_switch_button_four"));
        button4.setSelected(true);
        button1.setOnClickListener(switchListener);
        button3.setOnClickListener(switchListener);
        button4.setOnClickListener(switchListener);
    }
    private View.OnClickListener switchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == ResFinder.getId("umeng_switch_button_one")){
                button1.setSelected(true);
                button3.setSelected(false);
                button4.setSelected(false);
                ChangeFragment(0);
            }else if(view.getId() == ResFinder.getId("umeng_switch_button_three")){
                button1.setSelected(false);

                button3.setSelected(true);
                button4.setSelected(false);
                ChangeFragment(1);
            }else if(view.getId() == ResFinder.getId("umeng_switch_button_four")){
                button1.setSelected(false);
                button3.setSelected(false);
                button4.setSelected(true);
                ChangeFragment(2);
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
//        if (transaction == null){
//            transaction = getFragmentManager().beginTransaction();
//        }
//
//                transaction.replace(ResFinder.getResourceId(ResFinder.ResType.ID,"id_content"),categoryFragment).commit();



    }
    public void ChangeFragment(int num){
        switch (num){
            case 0:
                getFragmentManager().beginTransaction().replace(ResFinder.getResourceId(ResFinder.ResType.ID, "id_content"), myFocusFragment).commit();
                break;
            case 1:
                getFragmentManager().beginTransaction().replace(ResFinder.getResourceId(ResFinder.ResType.ID, "id_content"), RecommentFragment).commit();
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(ResFinder.getResourceId(ResFinder.ResType.ID,"id_content"),categoryFragment).commit();
                break;
        }
//        getFragmentManager().beginTransaction().commit();
    }
}
