package com.example.guanzhuli.foody.HomePage.adapter;
// Lily: Designed and coded base function for adapter.
// Xiao: Changed functions and design based on different using.

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
public class AllFoodAdapter extends RecyclerView.Adapter<AllHolder> implements View.OnClickListener{
    private Context mContext;
    ArrayList<Food> foods;
    public String TAG = "ALLFOOD";

//
//    public AllFoodAdapter(Context context) {
//        mContext = context;
//    }

    public AllFoodAdapter(Context context, ArrayList<Food> foods) {
        mContext = context;
        this.foods = foods;
    }

    @Override
    public AllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_food, parent, false);
        AllHolder allHolder = new AllHolder(v);
        v.setOnClickListener(this);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(AllHolder holder, int position) {
        holder.mTextId.setText(String.valueOf(foods.get(position).getId()));
        holder.mTextName.setText(foods.get(position).getName());
        holder.mTextPrice.setText(String.valueOf(foods.get(position).getPrice()));
        holder.mTextCategory.setText(foods.get(position).getCategory());
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

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
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
class AllHolder extends RecyclerView.ViewHolder {
    NetworkImageView mImage;
    ImageView mImageView;
    TextView mTextId, mTextName, mTextCategory, mTextPrice;

    public AllHolder(View itemView) {
        super(itemView);
        // mImage = (NetworkImageView) itemView.findViewById(R.id.food_img);
        mImageView = (ImageView) itemView.findViewById(R.id.food_img);
        mTextId = (TextView) itemView.findViewById(R.id.food_id);
        mTextName = (TextView) itemView.findViewById(R.id.food_name);
        mTextPrice = (TextView) itemView.findViewById(R.id.food_price);
        mTextId = (TextView) itemView.findViewById(R.id.food_id);
        mTextCategory = (TextView) itemView.findViewById(R.id.food_category);
    }
}