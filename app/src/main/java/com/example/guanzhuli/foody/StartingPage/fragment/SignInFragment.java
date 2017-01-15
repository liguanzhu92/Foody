package com.example.guanzhuli.foody.StartingPage.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.StartingPage.SignInActivity;
import com.example.guanzhuli.foody.controller.SPManipulation;
import com.example.guanzhuli.foody.controller.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guanzhu Li on 1/13/2017.
 */
public class SignInFragment extends Fragment {

    // Declare all views name;
    View view;
    Button btn_signIn;
    EditText mobile, password;
    TextView toSignUp;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init();
        return view;
    }

    private void init(){
        // Init all views
        mobile = (EditText) view.findViewById(R.id.sign_in_username);
        password = (EditText) view.findViewById(R.id.sign_in_pwd);


        toSignUp = (TextView) view.findViewById(R.id.to_sign_up);
        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set Tap Switch

            }
        });
        btn_signIn = (Button) view.findViewById(R.id.sign_in_btn);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginInfo();
            }
        });
    }



    // Set Default URL and Json
    final private String Login_URL = "http://rjtmobile.com/ansari/fos/fosapp/fos_login.php";
    final private String TAG = "SIGNIN";
    private String buildUrl(){
        return Login_URL + "?user_phone=" + mobile.getText().toString() + "&user_password=" + password.getText().toString();
    }
    private void checkLoginInfo() {
        SignInActivity.showPDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, buildUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response --> " + response);
                        if (response.toString().contains("success")){
                            Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                            SPManipulation.getInstance(getContext()).setMobile(mobile.getText().toString());
                            SPManipulation.getInstance(getContext()).setPwd(password.getText().toString());
                            Intent homePageIntent = new Intent(getActivity(), HomePageActivity.class);
                            startActivity(homePageIntent);
                        }
                        else {
                            Toast.makeText(getContext(), "Wrong password/mobile. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        SignInActivity.disPDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage(), error);
                        SignInActivity.disPDialog();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> params = new HashMap<String, String>();
                params.put("user_phone",mobile.getText().toString());
                params.put("user_password",password.getText().toString());
                Log.e("POST",params.toString());
                return params;
            }
        };
        VolleyController.getInstance().addToRequestQueue(stringRequest, TAG);
    }
}
