package com.tuanbapk.appgear.ConnectData;

import android.os.AsyncTask;
import android.util.Log;

import com.tuanbapk.appgear.Base.StringBase;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncOder extends AsyncTask<String,Void,String> {

    String jsonproducts, jsonuser, totalprice,dateoder,status;

    public AsyncOder(String jsonproducts, String jsonuser, String totalprice, String dateoder, String status) {
        this.jsonproducts = jsonproducts;
        this.jsonuser = jsonuser;
        this.totalprice = totalprice;
        this.dateoder = dateoder;
        this.status = status;
    }

    @Override
    protected String doInBackground(String... strings) {

        FormBody formBody = new FormBody.Builder()
                .add("jsonproducts", jsonproducts)
                .add("jsonuser",jsonuser)
                .add("totalprice",totalprice)
                .add("dateoder",dateoder)
                .add("status",status)
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
