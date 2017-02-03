package com.example.guanzhuli.foody.CartPage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.ShoppingCartItem;
import com.example.guanzhuli.foody.model.Food;

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

        if (position == getItemCount() - 2){
            holder.mTextName.setText("");
            holder.mTextPrice.setText("1.99");
            holder.mTextQuantity.setText("Shipping");
            return;
        }
        else if (position == getItemCount() - 1){
            holder.mTextName.setText("");
            holder.mTextPrice.setText("" + (ShoppingCartItem.getInstance(mContext).getPrice() * 0.06));
            holder.mTextQuantity.setText("Tax");
            return;
        }

        int id = ShoppingCartItem.getInstance(mContext).getFoodInCart().get(position);
        final Food curFood = ShoppingCartItem.getInstance(mContext).getFoodById(id);
        final int curFoodNumber = ShoppingCartItem.getInstance(mContext).getFoodNumber(curFood);
        holder.mTextName.setText(curFood.getName());
        holder.mTextPrice.setText(String.valueOf(curFoodNumber * curFood.getPrice()));
        holder.mTextQuantity.setText(String.valueOf(curFoodNumber));
    }

    @Override
    public int getItemCount() {
        return ShoppingCartItem.getInstance(mContext).getFoodTypeSize() + 2;
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