package com.codey.ugly.view.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.codey.ugly.R;
import com.codey.ugly.view.activity.CommonArticle;
import com.codey.ugly.view.adapter.CommonAdapter;
import com.codey.ugly.bean.CommonBean;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/3/30.
 * 常识模块
 */
public class Common extends Fragment
{
    private View view;
    private Spinner mspSelect;
    private ListView mlvCommon;
    private ArrayList<CommonBean> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.commonfrag,container,false);
        init();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setAdapter();
    }
    private void init()
    {
        mspSelect= (Spinner) view.findViewById(R.id.sp_select);
        String arr[]={"全部","生理常识","交往常识","其他"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.check_text_view,arr);
        mspSelect.setAdapter(adapter);
    }

    private void setAdapter()
    {
        mlvCommon= (ListView) view.findViewById(R.id.lv_common);
        addData();
        CommonAdapter adapter=new CommonAdapter(getActivity(),mList);
        mlvCommon.setAdapter(adapter);


        mlvCommon.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //后期可通过url/id跳转
                Intent intent = new Intent(getActivity().getApplicationContext(), CommonArticle.class);
                startActivity(intent);
            }
        });
    }
    private void addData()
    {
        mList=new ArrayList<>();
        CommonBean  cb1=new CommonBean("http://img3.imgtn.bdimg.com/it/u=485674225,3065067116&fm=21&gp=0.jpg",
                "女生来大姨妈时，男朋友该怎么做？",2,22);
        CommonBean  cb2=new CommonBean("http://p.3761.com/pic/24431399857442.jpg",
                "动不动就生气？",12,12);
        CommonBean  cb3=new CommonBean("http://v1.qzone.cc/avatar/201304/30/17/23/517f8da83de2a686.jpg%21200x200.jpg",
                "她只是想要一个拥抱。",2,0);

        mList.add(cb1);
        mList.add(cb2);
        mList.add(cb3);
    }

}
