package com.tuanbapk.appgear.Fragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
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

import com.tuanbapk.appgear.Base.ApiBase;
import com.tuanbapk.appgear.ConnectData.AsyncAddUser;
import com.tuanbapk.appgear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    private AppCompatButton btn_register;
    private EditText et_email,et_pass1,et_pass2;
    private TextView tv_login;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        tv_login = (TextView)view.findViewById(R.id.tv_login);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_pass1 = (EditText)view.findViewById(R.id.et_pass1);
        et_pass2 = (EditText)view.findViewById(R.id.et_pass2);
        progress = (ProgressBar)view.findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:
                String email = et_email.getText().toString();
                String pass1 = et_pass1.getText().toString();
                String pass2 = et_pass2.getText().toString();

                Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher matcherEmail = patternEmail.matcher(email);

                if(!pass2.isEmpty() && !email.isEmpty() && !pass1.isEmpty()) {
                    if(matcherEmail.matches()==true){
                        if (pass1.equals(pass2)){
                            Snackbar.make(getView(), "Xử lí đăng ký", Snackbar.LENGTH_LONG).show();
                            registerProcess(email,pass2,progress);
                        }else {
                            Snackbar.make(getView(), "Mật khẩu nhập lại không chính xác", Snackbar.LENGTH_LONG).show();
                        }
                    }else {
                        Snackbar.make(getView(), "Không phải định dạng Email!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(getView(), "Trường không được để trống", Snackbar.LENGTH_LONG).show();
                }

                break;

        }
    }

    private void registerProcess(String email, String pass, ProgressBar progress){
        Toast.makeText(getView().getContext(), ApiBase.ADDUSER, Toast.LENGTH_SHORT).show();
        AsyncAddUser asyncAddUser = new AsyncAddUser(email,pass,getView().getContext(),progress);
        asyncAddUser.execute(ApiBase.ADDUSER);

        try {
            JSONObject jsonObject = new JSONObject(asyncAddUser.get());

            Toast.makeText(getView().getContext(), jsonObject.toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        response.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
//                ServerResponse resp = response.body();
//                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
//                progress.setVisibility(View.INVISIBLE);             }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                progress.setVisibility(View.INVISIBLE);
//                Log.d(Constants.TAG,"failed");
//                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show(); }
//        });

    }

    private void goToLogin(){
        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();     }
}
