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
public class AllFoodAdapter extends RecyclerView.Adapter<AllHolder>{
    private Context mContext;

    public AllFoodAdapter(Context context) {
        mContext = context;
    }

    @Override
    public AllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_food, parent, false);
        AllHolder allHolder = new AllHolder(v);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(AllHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
class AllHolder extends RecyclerView.ViewHolder {
    NetworkImageView mImage;
    ImageView mImageView;
    TextView mTextId, mTextName, mTextCategory, mTextPrice;

    public AllHolder(View itemView) {
        super(itemView);
        // mImage = (NetworkImageView) itemView.findViewById(R.id.food_img);
        mImageView = (ImageView) itemView.findViewById(R.id.food_img);
        mTextId = (TextView) itemView.findViewById(R.id.food_id);
        mTextName = (TextView) itemView.findViewById(R.id.food_id);
        mTextPrice = (TextView) itemView.findViewById(R.id.food_id);
        mTextId = (TextView) itemView.findViewById(R.id.food_id);
    }
}