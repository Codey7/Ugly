package com.codey.ugly.bean;

/**
 * Created by Mr.Codey on 2016/4/22.
 * 用户基本信息
 */
public class SignUserBean extends BaseUserBean
{

    private String username;
    private String sex;

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public SignUserBean(String account, int type, String username, String pwd, String sex)
    {
        super(account,pwd,type);
        this.username = username;
        this.sex=sex;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

}
