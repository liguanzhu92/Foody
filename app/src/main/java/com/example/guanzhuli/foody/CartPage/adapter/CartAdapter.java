package com.example.guanzhuli.foody.CartPage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.guanzhuli.foody.R;

/**
 * Created by Guanzhu Li on 1/15/2017.
 */
public class CartAdapter extends RecyclerView.Adapter<CartHolder>{
    private Context mContext;

    public CartAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_cart, parent,false);
        CartHolder cartHolder = new CartHolder(view);
        return cartHolder;
    }

    @Override
    public void onBindViewHolder(CartHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

class CartHolder extends RecyclerView.ViewHolder {
    TextView mTextName, mTextCategory, mTextPrice;
    EditText mEditQuantity;
    public CartHolder(View itemView) {
        super(itemView);
        mTextName = (TextView) itemView.findViewById(R.id.cart_name);
        mTextCategory = (TextView) itemView.findViewById(R.id.cart_category);
        mTextPrice = (TextView) itemView.findViewById(R.id.cart_price);
        mEditQuantity = (EditText) itemView.findViewById(R.id.cart_quantity);
    }
}
