package com.tuanbapk.appgear.ConnectData;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tuanbapk.appgear.Base.StringBase;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncUserLoAd extends AsyncTask<String,Void,String> {
    String email,pass;
    Context context;

    public AsyncUserLoAd(String email, String pass, Context context) {
        this.email = email;
        this.pass = pass;
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        FormBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("pass",pass)
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
