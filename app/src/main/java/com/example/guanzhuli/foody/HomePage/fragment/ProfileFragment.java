package com.example.guanzhuli.foody.HomePage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.guanzhuli.foody.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView mTextMobile, mTextAddress;
    private TextView mTextUpdateMobile, mTextUpdateAddress, mTextUpdatePwd;
    private EditText mEditOldPwd, mEditNewPwd, mEditNewPwd2;
    private Button mButtonUpdate, mButtonCancel;
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
