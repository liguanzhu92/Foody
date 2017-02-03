package com.example.guanzhuli.foody.HomePage.adapter;
// Lily: Designed and coded base function for adapter.
// Xiao: Changed functions and design based on different using.

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.HomePage.fragment.AllTabFragment;
import com.example.guanzhuli.foody.HomePage.fragment.TrackFragment;
import com.example.guanzhuli.foody.R;
import com.example.guanzhuli.foody.model.Order;

import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 1/15/2017.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder>{
    private Context mContext;
    private ArrayList<Order> orders;

    public HistoryAdapter(Context context, ArrayList<Order> orders) {
        mContext = context;
        this.orders = orders;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_history, parent, false);
        HistoryHolder historyHolder = new HistoryHolder(v);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, final int position) {
        holder.mTextId.setText("" + orders.get(position).getId());
        holder.mTextName.setText(orders.get(position).getName());
        holder.mTextQuantity.setText("" + orders.get(position).getQuantity());
        holder.mTextTotal.setText("" + orders.get(position).getTotal());
        holder.mTextDate.setText("" + orders.get(position).getDate());
        holder.mTextAddress.setText(orders.get(position).getAddress());

        holder.btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("OrderId", orders.get(position).getId());
                TrackFragment trackFrag = new TrackFragment();
                trackFrag.setArguments(bundle);
                ((HomePageActivity) mContext).getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_fragment_container, trackFrag)
                        .addToBackStack(AllTabFragment.class.getName())
                        .commit();
            }
        });


        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void notifyData(ArrayList<Order> orders) {
//        Log.d("notifyData ", foods.size() + "");
        this.orders = orders;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}

class HistoryHolder extends RecyclerView.ViewHolder {
    TextView mTextId, mTextName, mTextQuantity, mTextTotal, mTextDate, mTextAddress, mTextStatus;
    TextView btn_track, btn_update, btn_cancel;
    public HistoryHolder(View itemView) {
        super(itemView);
        mTextId = (TextView) itemView.findViewById(R.id.history_id);
        mTextName = (TextView) itemView.findViewById(R.id.history_name);
        mTextQuantity = (TextView) itemView.findViewById(R.id.history_quantity);
        mTextTotal = (TextView) itemView.findViewById(R.id.history_total);
        mTextDate = (TextView) itemView.findViewById(R.id.history_date);
        mTextAddress = (TextView) itemView.findViewById(R.id.history_address);

        btn_track = (TextView) itemView.findViewById(R.id.history_track);
        btn_cancel = (TextView) itemView.findViewById(R.id.history_cancel);

//        mTextStatus = (TextView) itemView.findViewById(R.id.history_status);
    }
}
