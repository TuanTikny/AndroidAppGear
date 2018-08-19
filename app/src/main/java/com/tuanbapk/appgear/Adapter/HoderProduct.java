package com.tuanbapk.appgear.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuanbapk.appgear.R;

import ru.katso.livebutton.LiveButton;

public class HoderProduct {
    LinearLayout layout_sanpham;
    ImageView img_hinhsp;
    TextView tv_tensp,tv_giasp;
    LiveButton btn_chonmua;

    protected HoderProduct(View view){
        layout_sanpham =view.findViewById(R.id.layout_sanpham);
        img_hinhsp = view.findViewById(R.id.img_hinhsp);
        tv_tensp = view.findViewById(R.id.tv_tensp);
        tv_giasp =view.findViewById(R.id.tv_giasp);
        btn_chonmua =view.findViewById(R.id.btn_chonmua);
    }
}
