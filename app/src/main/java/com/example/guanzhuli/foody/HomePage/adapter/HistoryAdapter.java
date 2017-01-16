package com.example.guanzhuli.foody.HomePage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.guanzhuli.foody.R;

/**
 * Created by Guanzhu Li on 1/15/2017.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder>{
    private Context mContext;

    public HistoryAdapter(Context context) {
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

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

class HistoryHolder extends RecyclerView.ViewHolder {
    TextView mTextId, mTextName, mTextQuantity, mTextTotal, mTextDate, mTextAddress, mTextStatus;
    public HistoryHolder(View itemView) {
        super(itemView);
        mTextId = (TextView) itemView.findViewById(R.id.history_id);
        mTextName = (TextView) itemView.findViewById(R.id.history_name);
        mTextQuantity = (TextView) itemView.findViewById(R.id.history_quantity);
        mTextTotal = (TextView) itemView.findViewById(R.id.history_total);
        mTextDate = (TextView) itemView.findViewById(R.id.history_date);
        mTextAddress = (TextView) itemView.findViewById(R.id.history_address);
        mTextStatus = (TextView) itemView.findViewById(R.id.history_status);
    }
}
