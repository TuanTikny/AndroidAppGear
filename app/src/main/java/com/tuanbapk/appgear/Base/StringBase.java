package com.tuanbapk.appgear.Base;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class StringBase {
    public static final String ADDUSER_OPERATION = "addUser";

    public static final String LOGINUSER_OPERATION = "loginUser";
    public static final String STATUS = "status";
    public static final String KETQUA ="ketqua";
    public static final String IS_LOGGED_IN = "logged";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String BIRTHDAY = "birthday";
    public static final String PHONE = "phone";

    public static final String FORGOTPASS_OPERATION = "forgotPass";
    public static final String UPDATEPASSWITHCODE_OPERATION = "updatePasswithCode";
    public static final String UPDATEUSERINFOR_OPERATION = "updateUserInfor";
    public static final String UPDATEPASS_OPERATION = "updatePass";

    public static final String TAG = "LoiTaiAPI";

    public static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(10,TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();
}
