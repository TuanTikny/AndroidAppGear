package com.tuanbapk.appgear.Fragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment implements View.OnClickListener {
    private AppCompatButton btn_register;
    private EditText et_email,et_pass1,et_pass2;
    private TextView tv_login;
    private ProgressBar progress;
    private SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
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
                            progress.setVisibility(View.VISIBLE);
                            registerProcess(email,pass2);
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

    private void registerProcess(String email, String pass){
        AsyncUser asyncUser = new AsyncUser(email,pass,"","","",getView().getContext());
        asyncUser.execute(ApiBase.ADDUSER);

        try {
            JSONObject jsonObject = new JSONObject(asyncUser.get());
            if(Boolean.parseBoolean(jsonObject.getString(StringBase.STATUS))){
                // Kết quả trả về đăng ký thành công
                Snackbar.make(getView(), jsonObject.getString(StringBase.KETQUA), Snackbar.LENGTH_LONG).show();
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

    private void goToLogin(){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(StringBase.EMAIL,et_email.getText().toString());
        editor.putString(StringBase.PASS,et_pass2.getText().toString());
        editor.apply();

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }
}
