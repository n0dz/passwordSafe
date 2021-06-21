package com.nodz.datasafe.Model;

public class InfoModel {

    String appid,appname, username, password;

    public InfoModel() {
    }

    public InfoModel(String appid,String appname, String username, String password) {
        this.appname = appname;
        this.username = username;
        this.password = password;
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
