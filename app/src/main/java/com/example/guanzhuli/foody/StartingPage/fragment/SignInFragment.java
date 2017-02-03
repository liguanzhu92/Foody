package com.example.guanzhuli.foody.StartingPage.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guanzhu Li on 1/13/2017.
 */
public class SignInFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{

    // Declare all views name;
    View view;
//    Button btn_signIn;
    EditText mobile, password;
    TextView toSignUp;

    LoginButton mLoginButton;
    SPManipulation mSPManipulation;
    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private static final int RC_SIGN_IN = 007;
    Button btn_signIn, mFbButtonSignIn;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        fbGoogleSignIn();
        init();
        return view;
    }


    private void fbGoogleSignIn(){
        mFbButtonSignIn = (Button) view.findViewById(R.id.button_fb_sign_in);
                /*-----------google sign in---------------*/
        btnSignIn = (SignInButton) view.findViewById(R.id.btn_sign_in);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
                /*---------------fb sign in-------------------*/
        mFbButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginButton.performClick();
            }
        });
        //mLoginButton = (LoginButton) view.findViewById(R.id.login_button);
        mLoginButton = new LoginButton(getContext());
        mLoginButton.setReadPermissions("email");
        // If using in a fragment
        mLoginButton.setFragment(this);
        // Other app specific specialization
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.e("fblogin", "success");
                final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String id = object.getString("id");
                                    Log.e("fblogin", id);
                                    mSPManipulation = SPManipulation.getInstance(getContext());
                                    mSPManipulation.setMobile(id);
                                    Intent intent = new Intent(getActivity(), HomePageActivity.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("fblogin", "fb log in error");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            // start anotger activity
            Intent intent = new Intent(getActivity(), HomePageActivity.class);
            startActivity(intent);
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
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
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                Log.e("JSON", jsonArray.toString());
                                JSONObject userInfo = jsonArray.getJSONObject(0);
                                Log.e("JSON", userInfo.toString());
                                SPManipulation.getInstance(getContext()).setName(userInfo.getString("UserName"));
                                SPManipulation.getInstance(getContext()).setEmail(userInfo.getString("UserEmail"));
                                SPManipulation.getInstance(getContext()).setAddress(userInfo.getString("UserAddress"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
