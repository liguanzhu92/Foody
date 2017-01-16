package com.example.guanzhuli.foody.HomePage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.guanzhuli.foody.R;

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
        View view = inflater.inflate(R.layout.fragment_track, container, false);

        mEditOrderSearch = (EditText) view.findViewById(R.id.track_edit_search);
        mTextSearch = (TextView) view.findViewById(R.id.track_search);
        mTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderId = mEditOrderSearch.getText().toString();
                mLinearLayout.setVisibility(View.VISIBLE);
                /*--------insert code to get data---*/
                displayData(view);
            }
        });
        mLinearLayout = (LinearLayout) view.findViewById(R.id.track_detail_block);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mLinearLayout.setVisibility(View.VISIBLE);
            displayData(view);
        } else {
            mLinearLayout.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void displayData(View view) {
        mTextID = (TextView) view.findViewById(R.id.track_id);
        mTextDate = (TextView) view.findViewById(R.id.track_date);
        mTextTotal = (TextView) view.findViewById(R.id.track_total);
        mTextStatus = (TextView) view.findViewById(R.id.track_status);
        mImageStatus = (ImageView) view.findViewById(R.id.track_image_status);
        /*------using parseWord to get the string, parseImage to get the resource id-----*/
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
