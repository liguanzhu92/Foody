package com.example.guanzhuli.foody.StartingPage.fragment;


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
import com.android.volley.toolbox.StringRequest;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.StartingPage.SignInActivity;
import com.example.guanzhuli.foody.controller.VolleyController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guanzhu Li on 1/13/2017.
 */
public class SignUpFragment extends Fragment {
    private EditText username, mobile, email, password, repassword, address;
    private Button btn_signUp;
    private TextView toSignIn;

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init();
        return view;
    }

    private void init(){
        username = (EditText) view.findViewById(R.id.sign_up_username);
        mobile = (EditText) view.findViewById(R.id.sign_up_mobile);
        email = (EditText) view.findViewById(R.id.sign_up_email);
        password = (EditText) view.findViewById(R.id.sign_up_pwd);
        repassword = (EditText) view.findViewById(R.id.sign_up_pwd2);
        address = (EditText) view.findViewById(R.id.sign_up_address);

        toSignIn = (TextView) view.findViewById(R.id.to_sign_in);
        toSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_signUp = (Button) view.findViewById(R.id.sign_up_btn);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!repassword.getText().toString().equals(password.getText().toString())){
                    Toast.makeText(getActivity(), "Password and confirm password not same. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                registrationMethod();
            }
        });

    }


    // Set Default URL and Json
    final private String SignUp_URL = "http://rjtmobile.com/ansari/fos/fosapp/fos_reg.php";
    final private String TAG = "SIGNUP";
    private String buildUrl(){
        return SignUp_URL +
                "?user_phone=" + mobile.getText().toString() +
                "&user_email=" + email.getText().toString() +
                "&user_phone=" + mobile.getText().toString() +
                "&user_password=" + password.getText().toString() +
                "&user_add=" + address.getText().toString();
    }
    private void registrationMethod() {
        SignInActivity.showPDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, buildUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response --> " + response);
                        if (response.contains("successfully")){
                            getActivity().recreate();
                        }
                        else {
                            Toast.makeText(getActivity(), "Phone number already existed. Please try again!", Toast.LENGTH_SHORT).show();
                            mobile.setText("");
                            password.setText("");
                            repassword.setText("");
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
                params.put("user_name",username.getText().toString().trim());
                params.put("user_email",email.getText().toString().trim());
                params.put("user_phone",mobile.getText().toString().trim());
                params.put("user_password",password.getText().toString().trim());
                params.put("user_add",address.getText().toString().trim());
                Log.e("POST",params.toString());
                return params;
            }
        };
        VolleyController.getInstance().addToRequestQueue(stringRequest, TAG);
    }
}
