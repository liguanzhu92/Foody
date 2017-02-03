package com.example.guanzhuli.foody.HomePage.fragment;
// Lily: Designed UI. Set fragment replacement.
//       Implemented custom animation.
//       Implemented parseWord and parseImage function for order status.
// Xiao: Implemented data request.
//       Designed and implemented communication between history fragment via bundle.


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.VolleyController;
import com.example.guanzhuli.foody.model.Food;
import com.example.guanzhuli.foody.model.Order;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackFragment extends Fragment {
    // LinearLayout Property: no data pass, invisible; get data from track, visible; after search, visible
    private LinearLayout mLinearLayout;
    // search part
    private EditText mEditOrderSearch;
    private TextView mTextSearch;
    // display part
    private TextView mTextID, mTextDate, mTextTotal, mTextStatus;
    private ImageView mImageStatus;

    private View fragView;

    private final String baseUrl = "http://rjtmobile.com/ansari/fos/fosapp/order_track.php?&order_id=";

    private int orderId;
    private Order order;

    private final int[] imageResources = {R.mipmap.pack, R.mipmap.delivery, R.mipmap.fork, R.mipmap.alert};
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Track");
    }

    public TrackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_track, container, false);


        mEditOrderSearch = (EditText) fragView.findViewById(R.id.track_edit_search);
        mTextSearch = (TextView) fragView.findViewById(R.id.track_search);

        order = new Order();

        mLinearLayout = (LinearLayout) fragView.findViewById(R.id.track_detail_block);
        mTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    orderId = Integer.valueOf(mEditOrderSearch.getText().toString().trim());
                    mLinearLayout.setVisibility(View.VISIBLE);
                    getData(fragView);
                /*--------insert code to get data---*/
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("ERROR", e.toString());
                    Toast.makeText(getActivity(), "Wrong Id Format. Please Try Again!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        Bundle orderBundle = this.getArguments();
        if (orderBundle != null) {
            orderId = orderBundle.getInt("OrderId");
            getData(fragView);
            mLinearLayout.setVisibility(View.VISIBLE);
        } else {
            mLinearLayout.setVisibility(View.INVISIBLE);
        }
        return fragView;
    }

    private void getData(final View view){
        final String TAG = "TRACK_FRAGMENT";
        HomePageActivity.showPDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, buildUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d(TAG, jsonObject.toString());

                try{
                    JSONArray orderArray = jsonObject.getJSONArray("Order Detail");
                    for (int i = 0; i < orderArray.length(); i++) {
                        JSONObject c = orderArray.getJSONObject(i);

                        int id = c.getInt("OrderId");
                        double totalOrder = c.getDouble("TotalOrder");
                        String status = c.getString("OrderStatus");
                        String date = c.getString("OrderDate");

                        order.setId(id);
                        order.setTotal(totalOrder);
                        order.setStatus(status);
                        order.setDate(date);
                    }
                    displayData(view);
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
        Log.e("URL", jsonObjReq.getUrl());
        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private String buildUrl() {
        return baseUrl + orderId;
    }

    private void displayData(View view) {
        mTextID = (TextView) view.findViewById(R.id.track_id);
        mTextDate = (TextView) view.findViewById(R.id.track_date);
        mTextTotal = (TextView) view.findViewById(R.id.track_total);
        mTextStatus = (TextView) view.findViewById(R.id.track_status);
        mImageStatus = (ImageView) view.findViewById(R.id.track_image_status);
        /*------using parseWord to get the string, parseImage to get the resource id-----*/

        mTextID.setText("" + order.getId());
        mTextDate.setText(order.getDate());
        mTextTotal.setText("" + order.getTotal());
        mTextStatus.setText(parseWord(order.getStatus()));
        mImageStatus.setImageResource(parseImage(order.getStatus()));

    }

    private String parseWord(String s) {
        if (s.equals("1")) {
            return "Packing";
        } else if (s.equals("2")) {
            return "On the way";
        } else if (s.equals("3")) {
            return "Delivered";
        } else {
            return "Error";
        }
    }

    private int parseImage(String s) {
        if (s.equals("1")) {
            return imageResources[0];
        } else if (s.equals("2")) {
            return imageResources[1];
        } else if (s.equals("3")) {
            return imageResources[2];
        } else {
            return imageResources[3];
        }
    }

}
