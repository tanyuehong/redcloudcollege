package com.redskt.commonutils;

public class ResultCode {

    public static Integer SUCCESS = 200;
    public static Integer ERROR = 201;

    public static Integer NOSUCCESS = 210;
    public static Integer LOGINNOTNAMLTips = 996;  // 登录信息不正常提示，不跳转登录页面
    public static Integer LOGINExpired = 999;  // 登录过期
    public static Integer LOGINNoToken = 998;  // 没有token
}
