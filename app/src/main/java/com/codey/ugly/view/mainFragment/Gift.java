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
import com.codey.ugly.view.activity.GiftArticle;
import com.codey.ugly.view.adapter.ArticleAdapter;
import com.codey.ugly.bean.Article;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/3/30.
 * 女生模块
 */
public class Gift extends Fragment
{
    private View view;
    private Spinner mspSelect;
    private ListView mlvArticle;
    private ArrayList<Article> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.giftfrag,container,false);
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
        String arr[]={"全部","服饰","包","配饰","化妆品","零食","花","其他"};
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
                Intent intent = new Intent(getActivity().getApplicationContext(), GiftArticle.class);
                startActivity(intent);
            }
        });
    }


    private void addData()
    {
        mList=new ArrayList<>();
        Article da1=new Article(4,"男生冬天应该穿什么?",
                "https://img.alicdn.com/imgextra/i3/2228361831/" +
                        "TB2_yPCiFXXXXbOXpXXXXXXXXXX_!!2228361831.jpg","ds");
        Article da2=new Article(5,"男生冬天应该穿什么?","http://d06.res.meilishuo.net/pic/_o/ed" +
                "/71/e4e8bef839287af1ace2e8aa16e9_460_464.c1.jpg","dd");
        mList.add(da1);
        mList.add(da2);
    }
}
