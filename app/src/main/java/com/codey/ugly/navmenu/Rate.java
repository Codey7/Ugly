package com.codey.ugly.navmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.codey.ugly.R;
import com.codey.ugly.adapter.OtherRateAdapter;
import com.codey.ugly.bean.RateBean;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.commm.ui.activities.PostFeedActivity;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/2.
 */
public class Rate extends Fragment
{
    private View view;
    private ListView mListView;
    private ArrayList<RateBean> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view=inflater.inflate(R.layout.rate,container,false);
        mListView= (ListView) view.findViewById(R.id.lv_other_rate);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setAdapter();
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent=new Intent(getActivity(), PostFeedActivity.class);
                Topic mTopic=new Topic();
                intent.putExtra(Constants.TAG_TOPIC, mTopic);
                startActivity(intent);
            }
        });
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
    /*    private Button button;
    private static final int REQUEST_CODE_PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getImageFromAlbum();
            }
        });
    }
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image*//*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

    }*/
}
