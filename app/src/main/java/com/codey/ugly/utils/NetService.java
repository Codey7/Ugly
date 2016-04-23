package com.codey.ugly.utils;

import com.codey.ugly.bean.BaseUserBean;
import com.codey.ugly.bean.JudgeResult;
import com.codey.ugly.bean.SignUserBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Mr.Codey on 2016/4/22.
 * 网络请求
 */
public interface NetService
{
    //判断手机号被注册后是否设置密码

    //z注册
    @POST("/user/signin")
    Call<JudgeResult> createUser(@Body SignUserBean signUserBean);

    @POST("/user/login")
    Call<JudgeResult> variUser(@Body BaseUserBean baseUserBean);
}
