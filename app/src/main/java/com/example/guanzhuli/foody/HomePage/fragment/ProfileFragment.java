package com.example.guanzhuli.foody.HomePage.fragment;
// Lily: Implemented UI and initialized view.
//       Initialized on button click listener and basic functions for each button.
//       Implemented pwd and retype pwd check function.
// Xiao: Implemented web service communication for different functions.
//       Set view change after different function been called.

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.SPManipulation;
import com.example.guanzhuli.foody.controller.VolleyController;
import com.example.guanzhuli.foody.model.Order;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView mTextMobile, mTextAddress;
    private TextView mTextUpdateMobile, mTextUpdateAddress, mTextUpdatePwd;
    private EditText mEditOldPwd, mEditNewPwd, mEditNewPwd2;
    private Button mButtonConfirm, mButtonCancel;
    private LinearLayout mLinearPwd;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Profile");
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mTextMobile = (TextView) view.findViewById(R.id.profile_mobile);
        mTextMobile.setText(SPManipulation.getInstance(getContext()).getMobile());
        mTextAddress = (TextView) view.findViewById(R.id.profile_address);
        mTextAddress.setText(SPManipulation.getInstance(getContext()).getAddress());
        mTextUpdateMobile = (TextView) view.findViewById(R.id.profile_update_mobile);

        mTextUpdateMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_set_mobile,(ViewGroup) view.findViewById(R.id.dialog_mobile));
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Please Input Contact Information").setIcon(
                        android.R.drawable.ic_dialog_dialer).setView(
                        layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText inputMobile = (EditText) dialog.findViewById(R.id.dialog_et_mobile);
                        if (inputMobile.getText().toString().isEmpty()){
                            return;
                        }
                        try{
                            long number = Long.valueOf(inputMobile.getText().toString());
                            SPManipulation.getInstance(getActivity()).setMobile(inputMobile.getText().toString());
                            mTextMobile.setText(inputMobile.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Please Input Correct Phone Number!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", null).show();
            }
        });


        mEditOldPwd = (EditText) view.findViewById(R.id.profile_oldpwd);
        mEditNewPwd = (EditText) view.findViewById(R.id.profile_newpwd);
        mEditNewPwd2 = (EditText) view.findViewById(R.id.profile_newpwd2);
        mTextUpdateAddress = (TextView)view.findViewById(R.id.profile_update_address);
        mTextUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView mPhone = (TextView) mLinearPwd.findViewById(R.id.profile_tv_pwd);
                mEditOldPwd.setHint("Phone Number");
                mPhone.setText("Mobile:");
                TextView mPwd = (TextView) mLinearPwd.findViewById(R.id.profile_tv_newpwd);
                mPwd.setText("Password:");
                mEditNewPwd.setHint("Password");
                TextView mAddr = (TextView) mLinearPwd.findViewById(R.id.profile_tv_repwd);
                mAddr.setText("New Address");
                mEditNewPwd2.setHint("Address");
                mLinearPwd.setVisibility(View.VISIBLE);
                mButtonConfirm = (Button) mLinearPwd.findViewById(R.id.profile_confirm_button);
                mButtonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phone = mEditOldPwd.getText().toString();
                        String password = mEditNewPwd.getText().toString();
                        String address = mEditNewPwd2.getText().toString();
                        postUpdateAddress(phone, password, address);
                    }
                });


            }
        });
        mLinearPwd = (LinearLayout) view.findViewById(R.id.profile_pwd_linear);
        mLinearPwd.setVisibility(View.INVISIBLE);
        mTextUpdatePwd = (TextView) view.findViewById(R.id.profile_update_pwd);
        mTextUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView mPhone = (TextView) mLinearPwd.findViewById(R.id.profile_tv_pwd);
                mPhone.setText("Old Password:");
                mEditOldPwd.setHint("Old Password");
                TextView mPwd = (TextView) mLinearPwd.findViewById(R.id.profile_tv_newpwd);
                mPwd.setText("New Password:");
                mEditNewPwd.setHint("New Password");
                TextView mAddr = (TextView) mLinearPwd.findViewById(R.id.profile_tv_repwd);
                mAddr.setText("Retype:");
                mEditNewPwd2.setHint("Retype Password");
                mLinearPwd.setVisibility(View.VISIBLE);
                mButtonConfirm = (Button) mLinearPwd.findViewById(R.id.profile_confirm_button);
                mButtonConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String oldPwd = mEditOldPwd.getText().toString();
                        String newPwd = mEditNewPwd.getText().toString();
                        String newPwd2 = mEditNewPwd2.getText().toString();
                /*------use checkOldPwd() to check old pwd--------*/
                        if (!checkMatch(newPwd, newPwd2)) {
                            Toast.makeText(getContext(), "New password does not match", Toast.LENGTH_LONG).show();
                            mEditNewPwd.setText("");
                            mEditOldPwd.setText("");
                            mEditNewPwd2.setText("");
                            return;
                        }
                        postUpdatePwd(oldPwd, newPwd);
                    }
                });


            }
        });


        mButtonCancel = (Button) view.findViewById(R.id.profile_cancel_button);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLinearPwd.setVisibility(View.INVISIBLE);
                mEditNewPwd.setText("");
                mEditOldPwd.setText("");
                mEditNewPwd2.setText("");
            }
        });
        return view;
    }



    private void postUpdateAddress(String phone, String password, final String addr) {
        final String TAG = "UPDATE_ADDRESS";
        HomePageActivity.showPDialog();
        StringRequest strRequest = new StringRequest(Request.Method.GET, buildUrlAddr(phone, password, addr), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                if (response.contains("Your Delivery Address updated")){
                    Toast.makeText(getActivity(), "UPDATE SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    SPManipulation.getInstance(getContext()).setAddress(addr);
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
        VolleyController.getInstance().addToRequestQueue(strRequest);
    }

    private String buildUrlAddr(String phone, String pwd, String addr) {
        String baseUrl = "http://rjtmobile.com/ansari/fos/fosapp/fos_update_address.php?user_phone=";
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<addr.length(); i++){
            if (addr.charAt(i) == ' '){
                sb.append("%20");
            }
            else {
                sb.append(addr.charAt(i));
            }
        }
        return baseUrl +
                phone +
                "&user_password=" + pwd +
                "&user_add=" + sb.toString();
    }

    private void postUpdatePwd(String oldPwd, final String newPwd){
        final String TAG = "UPDATE_PWD";
        HomePageActivity.showPDialog();
        StringRequest strRequest = new StringRequest(Request.Method.GET, buildUrlPwd(oldPwd, newPwd), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                if (response.contains("password reset successfully")){
                    Toast.makeText(getActivity(), "UPDATE SUCCESSFUL", Toast.LENGTH_SHORT).show();
                    SPManipulation.getInstance(getContext()).setPwd(newPwd);
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
        VolleyController.getInstance().addToRequestQueue(strRequest);
    }

    private String buildUrlPwd(String oldPwd, String newPwd) {
        String baseUrl = "http://rjtmobile.com/ansari/fos/fosapp/fos_reset_pass.php?user_phone=";
        return baseUrl +
                SPManipulation.getInstance(getContext()).getMobile() +
                "&user_password=" + oldPwd +
                "&newpassword=" + newPwd;
    }


    private boolean checkMatch(String newPwd, String newPwd2) {
        return newPwd.equals(newPwd2);
    }


}
