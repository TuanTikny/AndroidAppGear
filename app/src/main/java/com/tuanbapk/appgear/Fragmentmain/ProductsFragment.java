package com.tuanbapk.appgear.Fragmentmain;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tuanbapk.appgear.Adapter.ProductAdapter;
import com.tuanbapk.appgear.Base.ApiBase;
import com.tuanbapk.appgear.Base.StringBase;
import com.tuanbapk.appgear.ConnectData.AsyncGetProduct;
import com.tuanbapk.appgear.Model.Product;
import com.tuanbapk.appgear.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProductsFragment extends Fragment {
    private GridView gv_sanpham;
    private ProgressBar progress;
    private ProductAdapter adapter;
    private SharedPreferences pref;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        pref = getActivity().getPreferences(0);
        gv_sanpham = view.findViewById(R.id.gv_sanpham);
        progress = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        adapter = new ProductAdapter(view.getContext(),R.layout.item_product,Getdata());
        gv_sanpham.setAdapter(adapter);


        gv_sanpham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                SharedPreferences.Editor editor = pref.edit();
                editor.putString(StringBase.IDPRO,String.valueOf(Getdata().get(i).getId()));
                editor.putString(StringBase.NAMEPRO,Getdata().get(i).getName());
                editor.putString(StringBase.QUANTIY,String.valueOf(Getdata().get(i).getQuantity()));
                editor.putString(StringBase.PRICE,Getdata().get(i).getPrice().toString());
                editor.putString(StringBase.IMAGE,Getdata().get(i).getImgurl());
                editor.putString(StringBase.DESCRIPTION,Getdata().get(i).getDescription());
                editor.apply();

                Fragment profile = new InforProFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_main, profile);
                ft.commit();
            }
        });
    }



    private ArrayList<Product> Getdata(){
        ArrayList<Product> arr = new ArrayList<Product>();
        AsyncGetProduct asyncGetProduct = new AsyncGetProduct();
        asyncGetProduct.execute(ApiBase.LISTPRODUCTS);

        try {
            JSONArray jsonArray = new JSONArray(asyncGetProduct.get());
            for (int i = 0 ; i < jsonArray.length();i++){
                JSONObject jsonO = jsonArray.getJSONObject(i);
                Product product = new Product(
                        Integer.parseInt(jsonO.getString("id")),
                        jsonO.getString("name"),
                        Integer.parseInt(jsonO.getString("quantity")),
                        Float.valueOf(jsonO.getString("price")),
                        jsonO.getString("image"),
                        jsonO.getString("description"));
                arr.add(product);
            }
            progress.setVisibility(View.INVISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
            progress.setVisibility(View.INVISIBLE);
        } catch (InterruptedException e) {
            e.printStackTrace();
            progress.setVisibility(View.INVISIBLE);
        } catch (ExecutionException e) {
            e.printStackTrace();
            progress.setVisibility(View.INVISIBLE);
        }
        return arr;
    }
}
