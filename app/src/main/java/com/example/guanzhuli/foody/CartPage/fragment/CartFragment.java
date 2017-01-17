package com.example.guanzhuli.foody.CartPage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guanzhuli.foody.CartPage.adapter.CartAdapter;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.ShoppingCartItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private RecyclerView mRecyclerView;
    public static TextView cart_total;

    private Button mButtonCancel, mButtonCheckout;

    private View view;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cart, container, false);
        init();
        return view;
    }

    private void init(){

        cart_total = (TextView) view.findViewById(R.id.cart_total_price);
        cart_total.setText(String.valueOf(ShoppingCartItem.getInstance().getPrice()));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_cart);
        mRecyclerView.setAdapter(new CartAdapter(getContext()));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mButtonCancel = (Button)view.findViewById(R.id.cart_back);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        mButtonCheckout = (Button)view.findViewById(R.id.cart_checkout);
        mButtonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckoutFragment checkoutFragment = new CheckoutFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.cart_container, checkoutFragment)
                        .addToBackStack(CartFragment.class.getName())
                        .commit();
            }
        });
    }

}
