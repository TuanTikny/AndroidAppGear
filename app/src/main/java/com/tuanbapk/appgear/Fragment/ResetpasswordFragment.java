package com.tuanbapk.appgear.Fragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tuanbapk.appgear.Base.ApiBase;
import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.ConnectData.AsyncUser;
import com.tuanbapk.appgear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ResetpasswordFragment extends Fragment implements View.OnClickListener {
    private AppCompatButton btn_reset;
    private EditText et_email,et_code,et_password;
    private TextView tv_timer;
    private ProgressBar progress;
    private TextView tv_login;
    private boolean isResetInitiated = false;
    private String email;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_password_reset,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        tv_login = view.findViewById(R.id.tv_login);
        btn_reset = view.findViewById(R.id.btn_reset);
        tv_timer = view.findViewById(R.id.timer);
        et_code = view.findViewById(R.id.et_code);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        et_password.setVisibility(View.GONE);
        et_code.setVisibility(View.GONE);
        tv_timer.setVisibility(View.GONE);
        btn_reset.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        progress = view.findViewById(R.id.progress);
    }

    private void initiateResetPasswordProcess(String email) {
        AsyncUser asyncUser = new AsyncUser(email,"","","","","","","","",getView().getContext());
        asyncUser.execute(ApiBase.FORGOTPASS);

        try {
            JSONObject jsonObject = new JSONObject(asyncUser.get());
            if(Boolean.parseBoolean(jsonObject.getString(StringBase.STATUS))){
                // Kết quả trả về gửi mail thành công
                Snackbar.make(getView(), jsonObject.getString(StringBase.KETQUA), Snackbar.LENGTH_LONG).show();
                et_email.setVisibility(View.GONE);
                    et_code.setVisibility(View.VISIBLE);
                    et_password.setVisibility(View.VISIBLE);
                    tv_timer.setVisibility(View.VISIBLE);
                    btn_reset.setText("Cập Nhật Mật Khẩu");
                    isResetInitiated = true;
                    startCountdownTimer();
            }else {
                Snackbar.make(getView(), jsonObject.getString(StringBase.KETQUA), Snackbar.LENGTH_LONG).show();
            }
            progress.setVisibility(View.INVISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void finishResetPasswordProcess(String email, String code, String password) {
        AsyncUser asyncUser = new AsyncUser(email,"",code,"",password,"","","","",getView().getContext());
        asyncUser.execute(ApiBase.UPDATEPASSWITHCODE);
        try {
            JSONObject jsonObject = new JSONObject(asyncUser.get());
            if(Boolean.parseBoolean(jsonObject.getString(StringBase.STATUS))){
                // Kết quả trả về cập nhật pass bằng code thành công
                Snackbar.make(getView(), jsonObject.getString(StringBase.KETQUA), Snackbar.LENGTH_LONG).show();
                countDownTimer.cancel();
                isResetInitiated = false;
                goToLogin();
            }else {
                Snackbar.make(getView(), jsonObject.getString(StringBase.KETQUA), Snackbar.LENGTH_LONG).show();
            }
            progress.setVisibility(View.INVISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void startCountdownTimer(){
        countDownTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished / 1000);         }

            public void onFinish() {
                Snackbar.make(getView(), "Vui Lòng Vào Mail để lấy code xác nhận", Snackbar.LENGTH_LONG).show();
                goToLogin();
            }
        }.start();
    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);     ft.commit(); }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_reset:

                if (!isResetInitiated) {
                    email = et_email.getText().toString();
                    if (!email.isEmpty()) {
                        progress.setVisibility(View.VISIBLE);
                        initiateResetPasswordProcess(email);
                    } else {
                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    String code = et_code.getText().toString();
                    String password = et_password.getText().toString();
                    if (!code.isEmpty() && !password.isEmpty()) {
                        finishResetPasswordProcess(email, code, password);
                    } else {
                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }

                }

                break;
            case R.id.tv_login:
                Fragment login = new LoginFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame,login);
                ft.commit();
                break;
        }
    }


}
