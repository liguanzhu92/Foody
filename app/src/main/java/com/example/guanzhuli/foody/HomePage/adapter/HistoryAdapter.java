package com.example.guanzhuli.foody.HomePage.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.guanzhuli.foody.HomePage.fragment.HistoryFragment;
import com.example.guanzhuli.foody.HomePage.fragment.TrackFragment;
import com.example.guanzhuli.foody.R;

/**
 * Created by Guanzhu Li on 1/15/2017.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {
    private Context mContext;
    private FragmentActivity mActivity;

    public HistoryAdapter(Context context) {
        mActivity = (FragmentActivity)context;
        mContext = context;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_history, parent, false);
        HistoryHolder historyHolder = new HistoryHolder(v);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        holder.mTextTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "track", Toast.LENGTH_SHORT).show();
                TrackFragment trackFragment = new TrackFragment();
                Bundle bundle = new Bundle();
                bundle.putString("test", "test");
                trackFragment.setArguments(bundle);
                mActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_fragment_container, trackFragment)
                        .addToBackStack(HistoryFragment.class.getName())
                        .commit();
            }
        });

        holder.mTextUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "update address", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mTextCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

class HistoryHolder extends RecyclerView.ViewHolder {
    TextView mTextId, mTextName, mTextQuantity, mTextTotal, mTextDate, mTextAddress;
    TextView mTextTrack, mTextUpdate, mTextCancel;
    public HistoryHolder(View itemView) {
        super(itemView);
        mTextId = (TextView) itemView.findViewById(R.id.history_id);
        mTextName = (TextView) itemView.findViewById(R.id.history_name);
        mTextQuantity = (TextView) itemView.findViewById(R.id.history_quantity);
        mTextTotal = (TextView) itemView.findViewById(R.id.history_total);
        mTextDate = (TextView) itemView.findViewById(R.id.history_date);
        mTextAddress = (TextView) itemView.findViewById(R.id.history_address);
        mTextTrack = (TextView) itemView.findViewById(R.id.history_track);
        mTextUpdate = (TextView) itemView.findViewById(R.id.history_update);
        mTextCancel = (TextView) itemView.findViewById(R.id.history_cancel);
    }
}
