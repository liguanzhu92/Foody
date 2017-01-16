package com.example.guanzhuli.foody.HomePage.fragment;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.guanzhuli.foody.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailFragment extends Fragment {
    TextView mTextId, mTextRecipe, mTextCategory, mTextPrice;
    Button mButtonAdd;
    ImageView mImageView;

    public FoodDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.food_detail_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Food Name");
        mTextId = (TextView) view.findViewById(R.id.food_detail_id);
        mTextRecipe = (TextView) view.findViewById(R.id.food_detail_recipe);
        mTextCategory = (TextView) view.findViewById(R.id.food_detail_category);
        mTextPrice = (TextView) view.findViewById(R.id.food_detail_price);
        mButtonAdd = (Button) view.findViewById(R.id.food_detail_add);
        mImageView = (ImageView) view.findViewById(R.id.food_detail_image);
        return view;
    }

}
