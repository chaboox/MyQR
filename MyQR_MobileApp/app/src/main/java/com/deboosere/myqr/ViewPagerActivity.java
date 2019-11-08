package com.deboosere.myqr;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.deboosere.myqr.base.BaseSwipeBackActivity;


/**
 * Created by GongWen on 17/8/24.
 */

public class ViewPagerActivity extends BaseSwipeBackActivity {
    private ViewPager mViewPager;
    private Button button1, button2;
    private TabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);


        mViewPager.setAdapter(adapter);

        mViewPager.setCurrentItem(0);
        mViewPager.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.d(TAG, "onFocusChange: " + view.getId());
            }
        });
        mViewPager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.d(TAG, "onFocusChange: " + mViewPager.getCurrentItem());
                if(mViewPager.getCurrentItem() == 0){
                    button1.setBackgroundColor(getColor(R.color.colorPrimary));
                    button2.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                }
                else {
                    button2.setBackgroundColor(getColor(R.color.colorPrimary));
                    button1.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                }

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
                button1.setBackgroundColor(getColor(R.color.colorPrimary));
                button2.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
                button2.setBackgroundColor(getColor(R.color.colorPrimary));
                button1.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            }
        });
    }

    private FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            if(position == 0)
           return MyScansFragment.newInstance(String.valueOf(position));
            else return TheirScansFragment.newInstance(String.valueOf(position));
            //return TestFragment.newInstance(String.valueOf(position));
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
}
