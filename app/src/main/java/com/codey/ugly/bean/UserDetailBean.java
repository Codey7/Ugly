package com.codey.ugly.bean;

import java.util.ArrayList;

/**
 * 用户详细信息  肥胖标准按国际标准计算
 * Created by Mr.Codey on 2016/4/23.
 */
public class UserDetailBean extends BaseUserBean
{
    private String bodyType;

    private ArrayList<String> girlList;
    private Boolean isPersonal;

    public UserDetailBean()
    {
    }

    public UserDetailBean(String sex, Boolean isPersonal)
    {
        this.isPersonal = isPersonal;
        this.setSex(sex);
    }

    public UserDetailBean(String bodyType, ArrayList<String> girlList)
    {
        this.bodyType = bodyType;
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

    public Boolean getPersonal()
    {
        return isPersonal;
    }

    public void setPersonal(Boolean personal)
    {
        isPersonal = personal;
    }
}
