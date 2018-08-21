package com.tuanbapk.appgear.Base;

public class ApiBase {
    public static String API = "http://192.168.137.95:3000/gearapi";
    public static final String
            ADDUSER = API+"/addUser",
            LOGINUSER=API+"/loginUser",
            FORGOTPASS=API+"/forgotPass",
            UPDATEPASSWITHCODE= API+"/updatePasswithCode",
            UPDATEPASS=API+"/updatePass",
            UPDATEUSERINFOR=API+"/updateUserInfor",
            LISTPRODUCTS=API+"/Listproducts",
            FINDPRODUCTS= API+"/Findproducts?name=",
            UPDATEQUATITY=API+"/updateQuatity",
            ADDODER=API+"/addOder",
            GETODER=API+"/getOders",
            UPDATEORDER=API+"/updateStatus";
}
