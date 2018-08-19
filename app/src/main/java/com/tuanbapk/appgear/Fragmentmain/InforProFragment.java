package com.tuanbapk.appgear.Fragmentmain;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.R;

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
        return view;
    }

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
        img_hinhsp = view.findViewById(R.id.img_hinhsp);
        tv_tensp =view.findViewById(R.id.tv_tensp);
        tv_giasp =view.findViewById(R.id.tv_giasp);
        tv_soluong =view.findViewById(R.id.tv_soluong);
        tv_description =view.findViewById(R.id.tv_description);
        btn_chonmua =view.findViewById(R.id.btn_chonmua);

        tv_tensp.setText(pref.getString(StringBase.NAMEPRO, ""));
        tv_giasp.setText(pref.getString(StringBase.PRICE, ""));
        tv_soluong.setText(pref.getString(StringBase.QUANTIY, "").toString());
        tv_description.setText(pref.getString(StringBase.DESCRIPTION, ""));

        Glide.with(this).load(pref.getString(StringBase.IMAGE, "")).into(img_hinhsp);


        btn_chonmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getView().getContext(), "Chức năng đặt mua", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
