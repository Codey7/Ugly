package com.codey.ugly.utils;

import com.codey.ugly.bean.Article;
import com.codey.ugly.bean.BaseUserBean;
import com.codey.ugly.bean.JudgeResult;
import com.codey.ugly.bean.SignUserBean;
import com.codey.ugly.bean.UserDetailBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Mr.Codey on 2016/4/22.
 * 网络请求
 */
public interface NetService
{
    //判断手机号被注册后是否设置密码

    //z注册
    /*@Headers({
            "Content-Type:application/json;charset:uft-8"
    })*/
    @POST("/user/signin")
    Call<JudgeResult> createUser(@Body SignUserBean signUserBean);

    @POST("test")
    Call<String> variUser(@Body String  baseUserBean);

    @POST("/user/userdetail")
    Call<JudgeResult> sendDetail(@Body UserDetailBean userDetailBean);

    //穿搭模块
    @POST("/deploy")
    Call<List<Article>> getArticle(@Path("deploy_type") String deployType);
}
