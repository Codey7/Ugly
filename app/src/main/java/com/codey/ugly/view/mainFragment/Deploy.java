package com.codey.ugly.view.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codey.ugly.R;
import com.codey.ugly.view.activity.DetailsArticle;
import com.codey.ugly.view.activity.EditArticle;
import com.codey.ugly.view.adapter.ArticleAdapter;
import com.codey.ugly.bean.Article;
import com.codey.ugly.core.BaseFragment;
import com.codey.ugly.utils.NetUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mr.Codey on 2016/3/30.
 * 穿搭模块
 */
public class Deploy extends BaseFragment implements View.OnClickListener
{
    private Spinner mspSelect;
    private ListView mlvArticle;
    private ArrayList<Article> mList;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        spinnerInit();
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public int getLayout()
    {
        return R.layout.deployfrag;
    }

    @Override
    public void initView(View view,Bundle savedInstanceState)
    {
        mspSelect= (Spinner) view.findViewById(R.id.sp_select);
        mlvArticle= (ListView) view.findViewById(R.id.lv_article);
        mList=new ArrayList<>();
        floatingActionButton= (FloatingActionButton) view.findViewById(R.id.fab);
    }


    public void upload(String type)
    {
        Call<List<Article>> call= NetUtil.getInstance().getNetService().getArticle(type);
        call.enqueue(new Callback<List<Article>>()
        {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response)
            {
                    mList.clear();
                    mList=(ArrayList<Article>) response.body();
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t)
            {
                showToast("deploy fail", Toast.LENGTH_LONG);
            }
        });
    }

    private void spinnerInit()
    {
        String arr[]=getResources().getStringArray(R.array.deploy_type);
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.check_text_view,arr);
        mspSelect.setAdapter(adapter);
        mspSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String item=adapter.getItem(position);
                setAdapter(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                setAdapter("全部");
            }
        });
    }

    private void setAdapter(String type)
    {
        //从网络获取数据
        //upload(type);
        addData(type);
        ArticleAdapter articleAdapter=new ArticleAdapter(getActivity(),mList);
        mlvArticle.setAdapter(articleAdapter);

        mlvArticle.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //后期可通过url/id跳转
                Article article= (Article) parent.getItemAtPosition(position);
                String url=article.getDetailsUrl();
                Intent intent=new Intent(getActivity().getApplicationContext(), DetailsArticle.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }


    private void addData(String type)
    {
        switch (type)
        {
            case "全部":
                setallData();
                break;
        }
    }

    private void setallData()
    {
        mList.clear();
        Article da1=new Article(1,"男生冬天应该穿什么?","https://img.alicdn.com/imgextra/i3/2228361831/TB2_yPCiFXXXXbOXpXXXXXXXXXX_!!2228361831.jpg","http://");
        Article da2=new Article(2,"春天到了?","http://d06.res.meilishuo.net/pic/_o/ed/71/e4e8bef839287af1ace2e8aa16e9_460_464.c1.jpg","http://");
        Article da3=new Article(3,"男生冬天应该穿什么?","https://img.alicdn.com/imgextra/i3/2228361831/TB2_yPCiFXXXXbOXpXXXXXXXXXX_!!2228361831.jpg","http://");
        Article da4=new Article(4,"春天到了?","http://d06.res.meilishuo.net/pic/_o/ed/71/e4e8bef839287af1ace2e8aa16e9_460_464.c1.jpg","http://");
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
            case R.id.fab:
                Intent intent=new Intent(getActivity(), EditArticle.class);
                startActivity(intent);
                break;
        }
    }
}
