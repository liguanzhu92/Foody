package com.example.guanzhuli.foody.HomePage.fragment;
// Lily: Designed and initialized UI. Set fragment replacement and on button click listener.
// Xiao: implemented view replacement based on bundle info passing from prev fragment and custom AlertDialog.


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.guanzhuli.foody.CartPage.CartActivity;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.ShoppingCartItem;
import com.example.guanzhuli.foody.controller.VolleyController;
import com.example.guanzhuli.foody.model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailFragment extends Fragment {
    TextView mTextId, mTextRecipe, mTextCategory, mTextPrice;
    Button mButtonAdd;
    ImageView mImageView;
    Food food;
    final private String TAG = "FoodDetail";
    CollapsingToolbarLayout collapsingToolbarLayout;


    View view;

    public FoodDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_detail, container, false);

        initView();
        initFoodInfo();

        setButtonListener();


        return view;
    }

    private void initView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.food_detail_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Food Name");
        mTextId = (TextView) view.findViewById(R.id.food_detail_id);
        mTextRecipe = (TextView) view.findViewById(R.id.food_detail_recipe);
        mTextCategory = (TextView) view.findViewById(R.id.food_detail_category);
        mTextPrice = (TextView) view.findViewById(R.id.food_detail_price);
        mButtonAdd = (Button) view.findViewById(R.id.food_detail_add);
        mImageView = (ImageView) view.findViewById(R.id.food_detail_image);
    }

    private void initFoodInfo(){
        food = new Food();
        food.setName(getArguments().getString("foodName"));
        food.setId(getArguments().getInt("foodId"));
        food.setPrice(getArguments().getDouble("foodPrice"));
        food.setRecepiee(getArguments().getString("foodRec"));
        food.setCategory(getArguments().getString("foodCat"));
        food.setImageUrl(getArguments().getString("foodImage"));
        mTextId.setText(String.valueOf(food.getId()));
        mTextCategory.setText(food.getCategory());
        mTextRecipe.setText(food.getRecepiee());
        mTextPrice.setText(String.valueOf(food.getPrice()));
        collapsingToolbarLayout.setTitle(food.getName());

        ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
        imageLoader.get(getArguments().getString("foodImage"), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    food.setImage(response.getBitmap());
                    mImageView.setImageBitmap(food.getImage());
                }
            }
        });
    }

    private void setButtonListener(){
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShoppingCartItem.getInstance(getContext()).addToCart(food);
                TextView cartNumber = (TextView)getActivity().findViewById(R.id.cart_item_number);
                cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getSize()));

                new AlertDialog.Builder(getActivity()).setTitle("Successful!").setIcon(
                        android.R.drawable.ic_dialog_info)
                        .setMessage("Add 1 " + food.getName() + " to cart!")
                        .setPositiveButton("Jump to cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent cartAct = new Intent(getActivity(), CartActivity.class);
                                startActivity(cartAct);
                            }
                        })
                        .setNegativeButton("Continue", null).show();
            }
        });
    }

}
