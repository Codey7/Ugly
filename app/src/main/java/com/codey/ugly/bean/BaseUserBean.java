package com.codey.ugly.bean;

/**
 * Created by Mr.Codey on 2016/4/23.
 */
public class BaseUserBean
{
    private String account;//手机号码
    private String pwd;
    private int type;//登录来源

    public BaseUserBean(String account, String pwd, int type)
    {
        this.account = account;
        this.pwd = pwd;
        this.type = type;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
