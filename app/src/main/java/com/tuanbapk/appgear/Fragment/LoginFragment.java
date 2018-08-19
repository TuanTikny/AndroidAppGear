package com.tuanbapk.appgear.Fragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tuanbapk.appgear.R;

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

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
        btn_login = (AppCompatButton) view.findViewById(R.id.btn_login);
        edt_email = (EditText) view.findViewById(R.id.et_email);
        edt_password = (EditText) view.findViewById(R.id.et_password);
        tv_register = (TextView) view.findViewById(R.id.tv_register);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
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
                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_register:
                Snackbar.make(getView(), getView().getContext().getResources().getString(R.string.Dangky_tieude), Snackbar.LENGTH_LONG).show();
                goToRegister();
                break;
            case R.id.tv_forgot:
                Snackbar.make(getView(), "Go to Forgot Password ", Snackbar.LENGTH_LONG).show();
                goToResetPassword();
                break;
        }
    }

    private void loginProcess(String email, String password){
        Toast.makeText(getView().getContext(), "Login Xử lí", Toast.LENGTH_SHORT).show();

//        response.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                ServerResponse resp = response.body();
//                Snackbar.make(getView(),resp.getMessage(),Snackbar.LENGTH_LONG).show();
//
//                if(resp.getResult().equals(Constants.SUCCESS)){
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putBoolean(Constants.IS_LOGGED_IN,true);
//                    editor.putString(Constants.EMAIL,resp.getUser().getEmail());
//                    editor.putString(Constants.NAME, resp.getUser().getName());
//                    editor.putString(Constants.UNIQUE_ID,resp.getUser().getUnique_id());
//                    editor.apply();
//                    goToProfile();
//                }
//                progressBar.setVisibility(View.INVISIBLE);             }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
//                Log.d(Constants.TAG,"failed");
//                Snackbar.make(getView(),t.getMessage(),Snackbar.LENGTH_LONG).show();
//            }
//        });
    }

    private void goToRegister() {
        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, register);
        ft.commit();
    }
    private void goToProfile() {
        Fragment profile = new ProfileFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, profile);
        ft.commit();
        Toast.makeText(getView().getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
    }
    private void goToResetPassword(){
        Fragment reset = new ResetpasswordFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,reset);
        ft.commit(); }

}
