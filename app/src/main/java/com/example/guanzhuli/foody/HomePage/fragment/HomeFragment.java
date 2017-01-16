package com.example.guanzhuli.foody.HomePage.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanzhuli.foody.HomePage.HomePageActivity;
import com.example.guanzhuli.foody.R;

import static com.example.guanzhuli.foody.HomePage.HomePageActivity.City;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    private TextView locationView;
    private TextView changeLocView;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Home");
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSectionsPagerAdapter =  new SectionsPagerAdapter(getChildFragmentManager());
        mTabLayout = (TabLayout) view.findViewById(R.id.home_tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = (ViewPager) view.findViewById(R.id.home_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTabLayout.setScrollPosition(position, 0, true);
                mTabLayout.setSelected(true);
                mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        locationView = (TextView) view.findViewById(R.id.home_TV_Location);
        locationView.setText(City);
        changeLocView = (TextView) view.findViewById(R.id.home_TV_notHere);
        changeLocView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_set_city,(ViewGroup) view.findViewById(R.id.dialog));
                new AlertDialog.Builder(getActivity()).setTitle("Please Input City Name").setIcon(
                        android.R.drawable.ic_dialog_info).setView(
                        layout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        EditText inputCity = (EditText) dialog.findViewById(R.id.dialog_et_city);
                        if (inputCity.getText().toString().equalsIgnoreCase("banglore")){
                            HomePageActivity.City = "banglore";
                            getActivity().recreate();
                        }
                        else if (inputCity.getText().toString().equalsIgnoreCase("delhi")){
                            HomePageActivity.City = "delhi";
                            getActivity().recreate();
                        }
                        else {
                            String SorryInfo = "We Currently Don't Have Service At Your Location!";
                            new AlertDialog.Builder(getActivity()).setTitle("Sorry!").setIcon(
                                    android.R.drawable.ic_dialog_info)
                                    .setMessage(SorryInfo)
                                    .setNegativeButton("Cancel", null).show();
                        }
                    }
                })
                        .setNegativeButton("Cancel", null).show();
            }
        });
        return view;
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    AllTabFragment tab1 = new AllTabFragment();
                    return tab1;
                case 1:
                    VegTabFragment tab2 = new VegTabFragment();
                    return tab2;
                case 2:
                    NonVegTabFragment tab3 = new NonVegTabFragment();
                    return tab3;
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "Veg";
                case 2:
                    return "Non Veg";
                default:
                    break;
            }
            return null;
        }
    }

}
