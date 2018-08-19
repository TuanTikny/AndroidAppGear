package com.tuanbapk.appgear.Fragmentmain;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.tuanbapk.appgear.Adapter.ProductAdapter;
import com.tuanbapk.appgear.Base.ApiBase;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        gv_sanpham = view.findViewById(R.id.gv_sanpham);
        progress = view.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        adapter = new ProductAdapter(view.getContext(),R.layout.item_product,Getdata());
        gv_sanpham.setAdapter(adapter);

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
