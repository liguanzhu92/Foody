package com.example.guanzhuli.foody.HomePage.fragment;

// Lily: Designed UI.
// Xiao: implemented data request.


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
import com.example.guanzhuli.foody.HomePage.adapter.HistoryAdapter;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.SPManipulation;
import com.example.guanzhuli.foody.controller.VolleyController;
import com.example.guanzhuli.foody.model.Food;
import com.example.guanzhuli.foody.model.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HistoryAdapter adapter;

    private ArrayList<Order> orders = new ArrayList<>();

    private String baseUrl = "http://rjtmobile.com/ansari/fos/fosapp/order_recent.php?&user_phone=";
    private String TAG = "ORDER_HISTORY";

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("History");
    }

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        if (orders.size()==0){
            objRequestMethod();
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_history);
        adapter = new HistoryAdapter(getContext(), orders);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
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
                    JSONArray orderJsonArray = jsonObject.getJSONArray("Order Detail");
                    for (int i = 0; i < orderJsonArray.length(); i++) {
                        JSONObject order = orderJsonArray.getJSONObject(i);
                        int id = order.getInt("OrderId");
                        String name = order.getString("OrderName");
                        int quantity = order.getInt("OrderQuantity");
                        double totalOrder = order.getDouble("TotalOrder");
                        String orderDeliverAdd = order.getString("OrderDeliverAdd");
                        String orderDate = order.getString("OrderDate");
                        String orderStatus = order.getString("OrderStatus");
                        final Order curOrder = new Order();
                        curOrder.setId(id);
                        curOrder.setName(name);
                        curOrder.setQuantity(quantity);
                        curOrder.setTotal(totalOrder);
//                        curOrder.setAddress(orderDeliverAdd);
                        curOrder.setAddress(SPManipulation.getInstance(getContext()).getAddress());
                        curOrder.setDate(orderDate);
                        curOrder.setStatus(orderStatus);
                        orders.add(curOrder);
                        adapter.notifyData(orders);
                    }

                }catch (Exception e){
                    System.out.println(e);
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
        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private String buildUrl() {
        return baseUrl + SPManipulation.getInstance(getActivity()).getMobile();
    }

}
