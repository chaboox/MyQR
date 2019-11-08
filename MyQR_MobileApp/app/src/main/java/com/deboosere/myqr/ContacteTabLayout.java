package com.deboosere.myqr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.deboosere.myqr.base.BaseSwipeBackActivity;

public class ContacteTabLayout extends BaseSwipeBackActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.contact_tab_layout);

       // SetupActionBar();
        initView();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(adapter);
      //  SetupViewPagerTabLayout();
       // TabSwitchListener();
        //setListener();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.contact_tab_layout;
    }


    private void setListener() {

    }

    private void TabSwitchListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                System.out.println("Tab " + position + " selected ");
                switch (position) {
                    case 0: //ListTicket
                      //  ListTicketsResolu.handlerticketResolu.sendEmptyMessage(0); //stp vérifie si la listview est vide et dabar rassek
                        break;

                    case 1: //ListTicketClos

                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void SetupViewPagerTabLayout() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }



    private void SetupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //show a caret even if android:parentActivityName is not specified.
        actionBar.setTitle("Tickets");
    }


    private void initView() {
        //homebutton = findViewById(R.id.homeiconID);
        //refreshbutton = findViewById(R.id.refreshIconID);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        MyScansFragment myScansFragment = new MyScansFragment();
        TheirScansFragment theirScansFragment = new TheirScansFragment();

        viewPagerAdapter.addFragment(myScansFragment, "En cours");
        viewPagerAdapter.addFragment(theirScansFragment, "Clos");
        viewPager.setAdapter(viewPagerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
          /*  case R.id.homeiconID:
                finish();
                break;
            case R.id.refreshIconID:
                refreshCurrentFragmentsTicket();
                break;*/
        }
    }



    private class HandlerTab extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menutablayout, menu);

        return true;
    }
    private FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return MyScansFragment.newInstance(String.valueOf(position));
        }

        @Override
        public int getCount() {
            return 2;
        }
    };



}