package com.codey.ugly.mainFragment;

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
import com.codey.ugly.activity.DetailsArticle;
import com.codey.ugly.adapter.ArticleAdapter;
import com.codey.ugly.bean.Article;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/3/30.
 * 穿搭模块
 */
public class Deploy extends Fragment implements View.OnClickListener
{
    private View view;
    private Spinner mspSelect;
    private ListView mlvArticle;
    private ArrayList<Article> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.deployfrag,container,false);
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
        String arr[]={"全部","服饰","发型","化妆品","其他"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.check_text_view,arr);
        mspSelect.setAdapter(adapter);
    }

    private void setAdapter()
    {
        mlvArticle= (ListView) view.findViewById(R.id.lv_article);
        addData();
        ArticleAdapter articleAdapter=new ArticleAdapter(getActivity(),mList);
        mlvArticle.setAdapter(articleAdapter);

        mlvArticle.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //后期可通过url/id跳转
                Intent intent=new Intent(getActivity().getApplicationContext(), DetailsArticle.class);
                startActivity(intent);
            }
        });
    }


    private void addData()
    {
        mList=new ArrayList<>();
        Article da1=new Article("男生冬天应该穿什么?","https://img.alicdn.com/imgextra/i3/2228361831/TB2_yPCiFXXXXbOXpXXXXXXXXXX_!!2228361831.jpg");
        Article da2=new Article("春天到了?","http://d06.res.meilishuo.net/pic/_o/ed/71/e4e8bef839287af1ace2e8aa16e9_460_464.c1.jpg");
        Article da3=new Article("男生冬天应该穿什么?","https://img.alicdn.com/imgextra/i3/2228361831/TB2_yPCiFXXXXbOXpXXXXXXXXXX_!!2228361831.jpg");
        Article da4=new Article("春天到了?","http://d06.res.meilishuo.net/pic/_o/ed/71/e4e8bef839287af1ace2e8aa16e9_460_464.c1.jpg");
        mList.add(da1);
        mList.add(da2);
        mList.add(da3);
        mList.add(da4);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

        }
    }
}
