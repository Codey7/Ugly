package com.codey.ugly.view.navmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.codey.ugly.R;
import com.codey.ugly.view.adapter.OtherRateAdapter;
import com.codey.ugly.bean.RateBean;
import com.codey.ugly.core.BaseFragment;
import com.umeng.comm.core.utils.CommonUtils;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Mr.Codey on 2016/4/2.
 */
public class Rate extends BaseFragment
{
    private ListView mListView;
    private ArrayList<RateBean> mList;
    private int REQUEST_IMAGE=1;
    private ArrayList<String> mSelectPath;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public int getLayout()
    {
        return R.layout.rate;
    }

    @Override
    public void initView(View view,Bundle savedInstanceState)
    {
        mListView= (ListView) view.findViewById(R.id.lv_other_rate);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                /*Intent intent=new Intent(getActivity(), PostFeedActivity.class);
                Topic mTopic=new Topic();
                intent.putExtra(Constants.TAG_TOPIC, mTopic);
                startActivity(intent);*/
                Intent intent = new Intent(getActivity(), MultiImageSelectorActivity.class);
// whether show camera
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                if(mSelectPath != null && mSelectPath.size()>0){
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                }
                startActivityForResult(intent, REQUEST_IMAGE);

            }
        });
        setAdapter();
    }

    private void setAdapter()
    {

        addData();
        Log.i("list_num",mList.size()+"");
        OtherRateAdapter adapter= new OtherRateAdapter(getActivity(),mList, CommonUtils.getLoginUser(getActivity().getApplicationContext()));

        mListView.setAdapter(adapter);
    }
    private void addData()
    {
        mList=new ArrayList<>();
        RateBean rb1=new RateBean(0,"刚刚","晚上陪女朋友逛街，这样穿怎么样？","http://t.388g.com/uploads/allimg/140510/22135V1Z-13.jpg");
        RateBean rb2=new RateBean(4.5f,"1小时前","Ktv聚会，这样穿怎么样？","http://img5.duitang.com/uploads/item/201509/29/20150929144719_PXs8K.thumb.224_0.jpeg");
        RateBean rb3=new RateBean(3f,"1小时前","今晚邀请男神过生日，这样穿怎么样？","http://img4.duitang.com/uploads/item/201406/14/20140614192216_aZTBC.thumb.224_0.jpeg");
        RateBean rb4=new RateBean(4.9f,"2小时前","晚上陪女朋友逛街，这样穿怎么样？","http://t.388g.com/uploads/allimg/140510/22135V1Z-13.jpg");
        RateBean rb5=new RateBean(3.2f,"5小时前","晚上陪女朋友逛街，这样穿怎么样？","http://t.388g.com/uploads/allimg/140510/22135V1Z-13.jpg");
        mList.add(rb1);
        mList.add(rb2);
        mList.add(rb3);
        mList.add(rb4);
        mList.add(rb5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == -1){
                for (String  s: mSelectPath)
                {
                    Log.i("path",s);
                }
            }
        }
    }
}
