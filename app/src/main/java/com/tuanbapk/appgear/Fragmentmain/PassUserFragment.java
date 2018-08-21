package com.tuanbapk.appgear.Fragmentmain;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tuanbapk.appgear.Base.ApiBase;
import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.ConnectData.AsyncUser;
import com.tuanbapk.appgear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import ru.katso.livebutton.LiveButton;

public class PassUserFragment extends Fragment {

    EditText et_old_password,et_new_password;
    LiveButton btn_update;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updatepass, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
        et_new_password = view.findViewById(R.id.et_new_password);
        et_old_password = view.findViewById(R.id.et_old_password);
        btn_update = view.findViewById(R.id.btn_update);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_old_password.getText().toString().trim().equals("") || et_new_password.getText().toString().trim().equals("")){
                    Snackbar.make(getView(), "Vui Lòng Nhập Đủ Thông Tin", Snackbar.LENGTH_LONG).show();
                }else {
                    AsyncUser asyncUser = new AsyncUser(pref.getString(StringBase.EMAIL, ""),"","",
                            et_old_password.getText().toString().trim(),
                            et_new_password.getText().toString().trim(),
                            "",
                            "",
                            "",
                            "",getView().getContext());
                    asyncUser.execute(ApiBase.UPDATEPASS);

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

            }
        });
    }
}
