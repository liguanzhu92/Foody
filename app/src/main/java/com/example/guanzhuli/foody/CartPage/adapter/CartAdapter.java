package com.example.guanzhuli.foody.CartPage.adapter;
// Lily: Designed and coded base function for adapter.
// Xiao: Changed functions and design based on different using.


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.guanzhuli.foody.CartPage.CartActivity;
import com.example.guanzhuli.foody.CartPage.fragment.CartFragment;
import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.HomePage.adapter.AllFoodAdapter;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.controller.ShoppingCartItem;
import com.example.guanzhuli.foody.controller.VolleyController;
import com.example.guanzhuli.foody.model.Food;

/**
 * Created by Guanzhu Li on 1/15/2017.
 */
public class CartAdapter extends RecyclerView.Adapter<CartHolder> implements View.OnClickListener{
    private Context mContext;
    private final String TAG = "ADAPTER";

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
    public void onBindViewHolder(final CartHolder holder, int position) {

        int id = ShoppingCartItem.getInstance(mContext).getFoodInCart().get(position);
        final Food curFood = ShoppingCartItem.getInstance(mContext).getFoodById(id);
        final int curFoodNumber = ShoppingCartItem.getInstance(mContext).getFoodNumber(curFood);

        holder.mTextName.setText(curFood.getName());
        holder.mTextCategory.setText(curFood.getCategory());
        holder.mEditQuantity.setText(String.valueOf(curFoodNumber));
        holder.mTextPrice.setText(String.valueOf(curFoodNumber * curFood.getPrice()));
        holder.mImage.setImageBitmap(curFood.getImage());

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curFoodNumber == 0){
                    return;
                }
                int currentNumber = curFoodNumber - 1;
                ShoppingCartItem.getInstance(mContext).setFoodNumber(curFood, currentNumber);


                HomePageActivity.cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(mContext).getSize()));
                CartFragment.cart_total.setText(String.valueOf(ShoppingCartItem.getInstance(mContext).getPrice()));
                notifyDataSetChanged();
            }
        });

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curFoodNumber == 99){
                    return;
                }
                int currentNumber = curFoodNumber + 1;
                ShoppingCartItem.getInstance(mContext).setFoodNumber(curFood, currentNumber);

                HomePageActivity.cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(mContext).getSize()));
                CartFragment.cart_total.setText(String.valueOf(ShoppingCartItem.getInstance(mContext).getPrice()));
                notifyDataSetChanged();
            }
        });

    }

    public void deleteData(int position) {
        int id = ShoppingCartItem.getInstance(mContext).getFoodInCart().get(position);
        Food curFood = ShoppingCartItem.getInstance(mContext).getFoodById(id);
        ShoppingCartItem.getInstance(mContext).setFoodNumber(curFood, 0);


        HomePageActivity.cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(mContext).getSize()));
        CartFragment.cart_total.setText(String.valueOf(ShoppingCartItem.getInstance(mContext).getPrice()));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ShoppingCartItem.getInstance(mContext).getFoodTypeSize();
    }



    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    private AllFoodAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(AllFoodAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view,String.valueOf(view.getTag()));
        }
        else{
            Log.e("CLICK", "ERROR");
        }
    }

}

class CartHolder extends RecyclerView.ViewHolder {
    TextView mTextName, mTextCategory, mTextPrice;
    ImageView mImage;
    TextView mEditQuantity;
    Button btn_minus;
    Button btn_plus;
    TextView total_price;
    public CartHolder(View itemView) {
        super(itemView);
        mTextName = (TextView) itemView.findViewById(R.id.cart_name);
        mTextCategory = (TextView) itemView.findViewById(R.id.cart_category);
        mTextPrice = (TextView) itemView.findViewById(R.id.cart_price);
        mEditQuantity = (TextView) itemView.findViewById(R.id.cart_quantity);
        mImage = (ImageView) itemView.findViewById(R.id.cart_image);


        btn_minus = (Button) itemView.findViewById(R.id.cart_btn_minus);
        btn_plus = (Button) itemView.findViewById(R.id.cart_btn_plus);



    }
}
