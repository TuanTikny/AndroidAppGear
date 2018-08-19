package com.tuanbapk.appgear.ConnectData;

import android.content.Context;
import android.os.AsyncTask;

import com.tuanbapk.appgear.Base.StringBase;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class AsyncGetProduct extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... strings) {

        Request.Builder builder = new Request.Builder();
        builder.url(strings[0]);
        Request request = builder.build();
        try {
            Response response = StringBase.okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
