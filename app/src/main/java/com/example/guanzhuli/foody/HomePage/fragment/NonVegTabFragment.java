package com.example.guanzhuli.foody.HomePage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.HomePage.adapter.AllFoodAdapter;
import com.example.guanzhuli.foody.HomePage.adapter.NonVegFoodAdapter;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.VolleyController;
import com.example.guanzhuli.foody.model.Food;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonVegTabFragment extends Fragment {
    private String baseUrl = "http://rjtmobile.com/ansari/fos/fosapp/fos_food.php?food_category=non-veg&city=";
    private String TAG = "NON_VEG_FOOD";


    ArrayList<Food> foods = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private NonVegFoodAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public NonVegTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (foods.size() == 0){
            objRequestMethod();
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_non_veg_tab, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_nonveg);
        adapter = new NonVegFoodAdapter(getContext(), foods);
        adapter.setOnItemClickListener(new AllFoodAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(getActivity(), "Click At: " + data, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void objRequestMethod(){
        HomePageActivity.showPDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, buildUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d(TAG, jsonObject.toString());

                try{
                    JSONArray foodsJsonArr = jsonObject.getJSONArray("Food");
                    for (int i = 0; i < foodsJsonArr.length(); i++) {
                        JSONObject c = foodsJsonArr.getJSONObject(i);
                        String id = c.getString("FoodId");
                        String name = c.getString("FoodName");
                        String recepiee = c.getString("FoodRecepiee");
                        String price = c.getString("FoodPrice");
                        String thumb = c.getString("FoodThumb");
                        final Food curFood = new Food();
                        curFood.setCategory("veg");
                        curFood.setName(name);
                        curFood.setRecepiee(recepiee);
                        curFood.setPrice(Double.valueOf(price));
                        curFood.setId(Integer.valueOf(id));
                        curFood.setImageUrl(thumb);

                        foods.add(curFood);
                        Log.e("Current Food", curFood.getName());

                        ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
                        imageLoader.get(thumb, new ImageLoader.ImageListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "Image Load Error: " + error.getMessage());
                            }
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                                if (response.getBitmap() != null) {
                                    curFood.setImage(response.getBitmap());
                                    Log.e("SET IMAGE", curFood.getName());
                                    adapter.notifyData(foods);
                                }
                            }
                        });
                        foods.get(i).setImage(curFood.getImage());
                    }

                }catch (Exception e){
                    System.out.println(TAG + e);
                }
                HomePageActivity.disPDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "ERROR" + volleyError.getMessage());
                Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                HomePageActivity.disPDialog();
            }
        });
        Log.e("URL", jsonObjReq.getUrl());
        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private String buildUrl() {
        return baseUrl + HomePageActivity.City;
    }


}
