package com.codey.ugly.bean;

import java.util.ArrayList;

/**
 * 用户详细信息  肥胖标准按国际标准计算
 * Created by Mr.Codey on 2016/4/23.
 */
public class UserDetailBean
{
    private String bodyType;
    private ArrayList<String> jobList;

    private ArrayList<String> girlList;

    public UserDetailBean(String bodyType, ArrayList<String> jobList, ArrayList<String> girlList)
    {
        this.bodyType = bodyType;
        this.jobList = jobList;
        this.girlList = girlList;
    }

    public ArrayList<String> getGirlList()
    {
        return girlList;
    }

    public void setGirlList(ArrayList<String> girlList)
    {
        this.girlList = girlList;
    }

    public String getBodyType()
    {
        return bodyType;
    }

    public void setBodyType(String bodyType)
    {
        this.bodyType = bodyType;
    }

    public ArrayList<String> getJobList()
    {
        return jobList;
    }

    public void setJobList(ArrayList<String> jobList)
    {
        this.jobList = jobList;
    }

}
