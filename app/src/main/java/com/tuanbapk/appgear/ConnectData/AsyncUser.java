package com.tuanbapk.appgear.ConnectData;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tuanbapk.appgear.Base.StringBase;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncUser extends AsyncTask<String,Void,String> {
    String email,pass,keycode,oldpass,newpass,id,name,birthday,phone;
    Context context;

    public AsyncUser(String email, String pass, String keycode, String oldpass, String newpass,String id,String name,String birthday,String phone, Context context) {
        this.email = email;
        this.pass = pass;
        this.keycode = keycode;
        this.oldpass = oldpass;
        this.newpass = newpass;
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.phone =phone;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
            FormBody formBody = new FormBody.Builder()
                    .add("email", email)
                    .add("pass",pass)
                    .add("keycode",keycode)
                    .add("oldpass",oldpass)
                    .add("newpass",newpass)
                    .add("id",id)
                    .add("name",name)
                    .add("birthday",birthday)
                    .add("phone",phone)
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(formBody)
                    .build();
            try {
                Response response = StringBase.okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("ERROR LINK",e.toString());
            }
        return null;
    }


}
