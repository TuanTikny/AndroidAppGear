package com.tuanbapk.appgear.Fragmentmain;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tuanbapk.appgear.Base.ApiBase;
import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.ConnectData.AsyncUser;
import com.tuanbapk.appgear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import ru.katso.livebutton.LiveButton;

public class InforUserFragment extends Fragment {
    TextView tv_email;
    EditText name_user,et_birthday,phone;
    LiveButton btn_update;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inforuser, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
        tv_email = view.findViewById(R.id.tv_email);
        name_user = view.findViewById(R.id.et_name);
        phone = view.findViewById(R.id.et_phone);
        btn_update = view.findViewById(R.id.btn_update);
        et_birthday = view.findViewById(R.id.et_birthday);

//        editor.putString(StringBase.NAME, "");
//        editor.putInt(StringBase.ID,-1);
//        editor.putString(StringBase.EMAIL,"");
//        editor.putString(StringBase.PASS,"");
//        editor.putString(StringBase.BIRTHDAY,"");
//        editor.putString(StringBase.PHONE,"");

        tv_email.setText(pref.getString(StringBase.EMAIL, ""));
        name_user.setText(pref.getString(StringBase.NAME, ""));
        phone.setText(pref.getString(StringBase.PHONE, ""));
        et_birthday.setText(pref.getString(StringBase.BIRTHDAY, ""));

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AsyncUser asyncUser = new AsyncUser("","","","","",
                        pref.getString(StringBase.ID, ""),
                        name_user.getText().toString(),
                        et_birthday.getText().toString(),
                        phone.getText().toString(),getView().getContext());
                asyncUser.execute(ApiBase.UPDATEUSERINFOR);

                try {
                    JSONObject jsonObject = new JSONObject(asyncUser.get());
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
