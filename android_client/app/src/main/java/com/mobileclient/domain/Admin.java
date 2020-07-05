package com.mobileclient.domain;

import java.io.Serializable;


/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 00:07
 **/
public class Admin implements Serializable{
    String username;
    String passwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
