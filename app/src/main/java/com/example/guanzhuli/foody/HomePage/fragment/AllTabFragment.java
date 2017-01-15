package com.example.guanzhuli.foody.HomePage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guanzhuli.foody.HomePage.adapter.AllFoodAdapter;
import com.example.guanzhuli.foody.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllTabFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public AllTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_tab, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_all);
        final AllFoodAdapter adapter = new AllFoodAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

}
