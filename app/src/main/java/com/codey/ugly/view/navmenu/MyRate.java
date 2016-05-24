package com.codey.ugly.view.navmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.codey.ugly.R;
import com.codey.ugly.view.adapter.MyRateAdapter;
import com.codey.ugly.bean.RateBean;
import com.codey.ugly.core.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/5.
 */
public class MyRate extends BaseFragment
{
    private ListView mListView;
    private ArrayList<RateBean> mList;
    @Override
    public int getLayout()
    {
        return R.layout.my_rate;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState)
    {
        mListView= (ListView) view.findViewById(R.id.lv_my_rate);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setAdapter();
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
    private void setAdapter()
    {

        addData();
        Log.i("list_num",mList.size()+"");
        MyRateAdapter adapter= new MyRateAdapter(getActivity(),mList);

        mListView.setAdapter(adapter);
    }
}
