package com.example.guanzhuli.foody.HomePage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.model.Food;

import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 1/14/2017.
 */
public class NonVegFoodAdapter extends RecyclerView.Adapter<NonVegHolder> implements View.OnClickListener{
    private Context mContext;

    private ArrayList<Food> foods;

    public NonVegFoodAdapter(Context context, ArrayList<Food> foods) {
        mContext = context;
        this.foods = foods;
    }

    @Override
    public NonVegHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_food, parent, false);
        NonVegHolder nonVegHolder = new NonVegHolder(v);
        v.setOnClickListener(this);
        return nonVegHolder;
    }

    @Override
    public void onBindViewHolder(NonVegHolder holder, int position) {
        holder.mTextCategory.setVisibility(View.INVISIBLE);
        holder.mTextCateTitle.setVisibility(View.INVISIBLE);

        holder.mTextId.setText(String.valueOf(foods.get(position).getId()));
        holder.mTextName.setText(foods.get(position).getName());
        holder.mTextPrice.setText(String.valueOf(foods.get(position).getPrice()));
        holder.mImageView.setImageBitmap(foods.get(position).getImage());

        holder.itemView.setTag(foods.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }


    public void notifyData(ArrayList<Food> foods) {
//        Log.d("notifyData ", foods.size() + "");
        this.foods = foods;
        notifyDataSetChanged();
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

class NonVegHolder extends RecyclerView.ViewHolder {
    NetworkImageView mImage;
    ImageView mImageView;
    TextView mTextId, mTextName, mTextCategory, mTextPrice, mTextCateTitle;

    public NonVegHolder(View itemView) {
        super(itemView);
        // mImage = (NetworkImageView) itemView.findViewById(R.id.food_img);
        mImageView = (ImageView) itemView.findViewById(R.id.food_img);
        mTextId = (TextView) itemView.findViewById(R.id.food_id);
        mTextName = (TextView) itemView.findViewById(R.id.food_name);
        mTextPrice = (TextView) itemView.findViewById(R.id.food_price);
        mTextCategory = (TextView) itemView.findViewById(R.id.food_category);
        mTextCateTitle = (TextView) itemView.findViewById(R.id.food_category_title);
    }
}
