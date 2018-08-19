package com.tuanbapk.appgear.Fragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tuanbapk.appgear.Base.ApiBase;
import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.ConnectData.AsyncUser;
import com.tuanbapk.appgear.MainProducts;
import com.tuanbapk.appgear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private AppCompatButton btn_login;
    private EditText edt_email, edt_password;
    private TextView tv_register,tv_forgot;
    private ProgressBar progressBar;
    private SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;     }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        edt_email.setText(pref.getString(StringBase.EMAIL, ""));
        edt_password.setText(pref.getString(StringBase.PASS, ""));
    }

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
        btn_login = view.findViewById(R.id.btn_login);
        edt_email = view.findViewById(R.id.et_email);
        edt_password = view.findViewById(R.id.et_password);
        tv_register = view.findViewById(R.id.tv_register);
        progressBar = view.findViewById(R.id.progress);
        tv_forgot = view.findViewById(R.id.tv_forgot);
        tv_forgot.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    loginProcess(email, password);
                } else {
                    Snackbar.make(getView(), getView().getContext().getResources().getString(R.string.Truong_korong), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_register:
                Snackbar.make(getView(), getView().getContext().getResources().getString(R.string.Dangky_tieude), Snackbar.LENGTH_LONG).show();
                goToRegister();
                break;
            case R.id.tv_forgot:
                Snackbar.make(getView(), getView().getContext().getResources().getString(R.string.Quen_matkhau), Snackbar.LENGTH_LONG).show();
                goToResetPassword();
                break;
        }
    }

    private void loginProcess(String email, String pass){

        AsyncUser asyncUser = new AsyncUser(email,pass,"","","","","","","",getView().getContext());
        asyncUser.execute(ApiBase.LOGINUSER);

        try {
            JSONObject jsonObject = new JSONObject(asyncUser.get());
            if(Boolean.parseBoolean(jsonObject.getString(StringBase.STATUS))){
                // Kết quả đăng nhập thành công
                progressBar.setVisibility(View.INVISIBLE);
                // Lấy json con
                JSONObject jsoncon = (JSONObject) jsonObject.get("inforUser");

                Intent intent = new Intent();
                intent.setClass(getView().getContext(), MainProducts.class);
                intent.putExtra(StringBase.IS_LOGGED_IN,true);
                intent.putExtra(StringBase.ID,Integer.parseInt(jsoncon.getString("id")));
                intent.putExtra(StringBase.EMAIL,jsoncon.getString("email"));
                intent.putExtra(StringBase.NAME,jsoncon.getString("name"));
                intent.putExtra(StringBase.BIRTHDAY,jsoncon.getString("birthday"));
                intent.putExtra(StringBase.PHONE,jsoncon.getString("phone"));
                startActivity(intent);

                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(StringBase.IS_LOGGED_IN,true);
                editor.putInt(StringBase.ID,Integer.parseInt(jsoncon.getString("id")));
                editor.putString(StringBase.EMAIL,jsoncon.getString("email"));
                editor.putString(StringBase.NAME,jsoncon.getString("name"));
                editor.putString(StringBase.BIRTHDAY,jsoncon.getString("birthday"));
                editor.putString(StringBase.PHONE,jsoncon.getString("phone"));
                editor.apply();

            }else {
                Snackbar.make(getView(), jsonObject.getString(StringBase.KETQUA), Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void goToRegister() {
        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, register);
        ft.commit();
    }

    private void goToResetPassword(){
        Fragment reset = new ResetpasswordFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,reset);
        ft.commit(); }

}
