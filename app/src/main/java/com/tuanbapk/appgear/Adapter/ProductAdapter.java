package com.tuanbapk.appgear.Adapter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.tuanbapk.appgear.Fragmentmain.InforProFragment;
import com.tuanbapk.appgear.Fragmentmain.ProductsFragment;
import com.tuanbapk.appgear.Model.Product;
import com.tuanbapk.appgear.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    Context c;
    int r;
    ArrayList<Product> arr;
    LayoutInflater inflater;


    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);

        this.c = context;
        this.r = resource;
        this.arr = objects;

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;

        HoderProduct hoder;

        if (view ==null){
            inflater= LayoutInflater.from(c);
            view = inflater.inflate(r,null);
            hoder = new HoderProduct(view);
            view.setTag(hoder);
        }else {
            hoder = (HoderProduct)view.getTag();
        }

        final Product product = arr.get(position);


        hoder.tv_tensp.setText(product.getName());
        hoder.tv_giasp.setText(String.valueOf(product.getPrice()));
        Glide.with(c).load(product.getImgurl()).into(hoder.img_hinhsp);

        hoder.btn_chonmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
