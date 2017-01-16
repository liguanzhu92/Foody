package com.example.guanzhuli.foody.CartPage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.guanzhuli.foody.R;

/**
 * Created by Guanzhu Li on 1/16/2017.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutHolder>{

    private Context mContext;

    public CheckoutAdapter(Context context) {
        mContext = context;
    }


    @Override
    public CheckoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_checkout, parent, false);
        CheckoutHolder checkoutHolder = new CheckoutHolder(view);

        return checkoutHolder;
    }

    @Override
    public void onBindViewHolder(CheckoutHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
class CheckoutHolder extends RecyclerView.ViewHolder {
    TextView mTextName, mTextQuantity, mTextPrice;

    public CheckoutHolder(View itemView) {
        super(itemView);
        mTextName = (TextView) itemView.findViewById(R.id.checkout_name);
        mTextPrice = (TextView) itemView.findViewById(R.id.checkout_price);
        mTextQuantity = (TextView) itemView.findViewById(R.id.checkout_quantity);
    }
}