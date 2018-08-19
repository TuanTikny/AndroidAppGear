package com.tuanbapk.appgear;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.Fragment.LoginFragment;
import com.tuanbapk.appgear.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getPreferences(0);

        if (pref.getBoolean(StringBase.IS_LOGGED_IN, true)){
            Intent intent = new Intent();
            intent.setClass(this, MainProducts.class);
            startActivity(intent);
        }else {
            Fragment login = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,login);
            ft.commit();
        }


    }
}
