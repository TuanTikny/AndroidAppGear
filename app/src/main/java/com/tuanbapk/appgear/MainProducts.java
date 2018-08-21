package com.tuanbapk.appgear;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.Fragmentmain.InforProFragment;
import com.tuanbapk.appgear.Fragmentmain.InforUserFragment;
import com.tuanbapk.appgear.Fragmentmain.PassUserFragment;
import com.tuanbapk.appgear.Fragmentmain.ProductsFragment;
import com.tuanbapk.appgear.R;

public class MainProducts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);
        pref = getPreferences(0);

        Intent intent = MainProducts.this.getIntent();
        if (intent == null){
            return;
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(StringBase.IS_LOGGED_IN, intent.getBooleanExtra(StringBase.IS_LOGGED_IN,true));
        editor.putString(StringBase.NAME, intent.getStringExtra(StringBase.NAME));
        editor.putString(StringBase.ID,String.valueOf(intent.getIntExtra(StringBase.ID,0)));
        editor.putString(StringBase.EMAIL,intent.getStringExtra(StringBase.EMAIL));
        editor.putString(StringBase.PASS,intent.getStringExtra(StringBase.PASS));
        editor.putString(StringBase.BIRTHDAY,intent.getStringExtra(StringBase.BIRTHDAY));
        editor.putString(StringBase.PHONE,intent.getStringExtra(StringBase.PHONE));
        editor.apply();


        Fragment profile = new ProductsFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_main, profile);
        ft.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment profile = new ProductsFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_main, profile);
                ft.commit();
                Snackbar.make(view, "Danh sách Sản Phẩm", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_infor) {
            Fragment profile = new InforUserFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_main, profile);
            ft.commit();
        } else if (id == R.id.nav_changepass) {
            Fragment profile = new PassUserFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_main, profile);
            ft.commit();
        } else if (id == R.id.nav_products) {
            Fragment profile = new ProductsFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_main, profile);
            ft.commit();
        } else if (id == R.id.nav_oders) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_out) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(StringBase.IS_LOGGED_IN, false);
            editor.putString(StringBase.NAME, "");
            editor.putString(StringBase.ID,"-1");
            editor.putString(StringBase.EMAIL,"");
            editor.putString(StringBase.PASS,"");
            editor.putString(StringBase.BIRTHDAY,"");
            editor.putString(StringBase.PHONE,"");
            editor.apply();
            intent.putExtra(StringBase.TOKEN,"logout");
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
