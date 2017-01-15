package com.example.guanzhuli.foody.HomePage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.foody.R;

/**
 * Created by Guanzhu Li on 1/14/2017.
 */
public class NonVegFoodAdapter extends RecyclerView.Adapter<NonVegHolder>{
    private Context mContext;

    public NonVegFoodAdapter(Context context) {
        mContext = context;
    }

    @Override
    public NonVegHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_food, parent, false);
        NonVegHolder nonVegHolder = new NonVegHolder(v);
        return nonVegHolder;
    }

    @Override
    public void onBindViewHolder(NonVegHolder holder, int position) {
        holder.mTextCategory.setVisibility(View.INVISIBLE);
        holder.mTextCateTitle.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return 2;
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
        mTextName = (TextView) itemView.findViewById(R.id.food_id);
        mTextPrice = (TextView) itemView.findViewById(R.id.food_id);
        mTextId = (TextView) itemView.findViewById(R.id.food_id);
        mTextCategory = (TextView) itemView.findViewById(R.id.food_category);
        mTextCateTitle = (TextView) itemView.findViewById(R.id.food_category_title);
    }
}
