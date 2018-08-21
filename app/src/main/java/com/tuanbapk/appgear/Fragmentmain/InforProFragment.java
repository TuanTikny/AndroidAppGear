package com.tuanbapk.appgear.Fragmentmain;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tuanbapk.appgear.Base.ApiBase;
import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.ConnectData.AsyncOder;
import com.tuanbapk.appgear.Model.Product;
import com.tuanbapk.appgear.Model.User;
import com.tuanbapk.appgear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import ru.katso.livebutton.LiveButton;

public class InforProFragment extends Fragment {
    ImageView img_hinhsp;
    TextView tv_tensp,tv_giasp,tv_soluong,tv_description;
    LiveButton btn_chonmua;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inforproduct, container, false);
        initViews(view);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
        img_hinhsp = view.findViewById(R.id.img_hinhsp);
        tv_tensp = view.findViewById(R.id.tv_tensp);
        tv_giasp = view.findViewById(R.id.tv_giasp);
        tv_soluong =view.findViewById(R.id.tv_soluong);
        tv_description =view.findViewById(R.id.tv_description);
        btn_chonmua =view.findViewById(R.id.btn_chonmua);

       final String idpro = pref.getString(StringBase.IDPRO,"");
        final String namepro = pref.getString(StringBase.NAMEPRO, "");
        final String price = pref.getString(StringBase.PRICE, "");
        final String soluong = pref.getString(StringBase.QUANTIY, "").toString();
        final String mota = pref.getString(StringBase.DESCRIPTION, "");

        final JSONObject jopro = new JSONObject();
        try {
            jopro.put("idpro", idpro);
            jopro.put("namepro", namepro);
            jopro.put("price", price);
            jopro.put("soluong", soluong);
            jopro.put("mota", mota);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String id = pref.getString(StringBase.ID, "");
        final String email = pref.getString(StringBase.EMAIL, "");
        final String nameuser = pref.getString(StringBase.NAME, "");
        final String phone = pref.getString(StringBase.PHONE, "");
        String birthday = pref.getString(StringBase.BIRTHDAY, "");

        final JSONObject jouser = new JSONObject();

        try {
            jouser.put("id", id);
            jouser.put("email", email);
            jouser.put("nameuser", nameuser);
            jouser.put("phone", phone);
            jouser.put("birthday", birthday);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        tv_tensp.setText(namepro);
        tv_giasp.setText(price);
        tv_soluong.setText(soluong);
        tv_description.setText(mota);

        Glide.with(this).load(pref.getString(StringBase.IMAGE, "")).into(img_hinhsp);


        btn_chonmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

                AsyncOder asyncOder = new AsyncOder(jopro.toString(),jouser.toString(),price,timeStamp,"1");
                asyncOder.execute(ApiBase.ADDODER);

                try {
                    JSONObject jsonObject = new JSONObject(asyncOder.get());
                    if(Boolean.parseBoolean(jsonObject.getString(StringBase.STATUS))){
                        // Kết quả trả về cập nhật thành công hay thất bại
                        Snackbar.make(getView(), jsonObject.getString(StringBase.KETQUA), Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(getView(), jsonObject.getString(StringBase.KETQUA), Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
