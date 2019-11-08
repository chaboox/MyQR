package com.deboosere.myqr;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class TheirScansFragment extends Fragment {

    private ListView listView;
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<UserModel> userModels;
    private CustomContactAdapter adapter;
    public static Handler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_scans_fragment, container, false);

        initView(view);
        registerForContextMenu(listView);
        getFirebaseData();

        return view;
    }
    public static TheirScansFragment newInstance(String title) {
        TheirScansFragment testFragment = new TheirScansFragment();

        return testFragment;
    }

    private void getFirebaseData() {

    }

    private void populateListView() {

    }

    private void initView(View view) {
        swipeLayout = view.findViewById(R.id.swipe_container);
        swipeLayout.setColorScheme(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light);
        listView = view.findViewById(R.id.list_my_scan);
        userModels = new ArrayList<>();
        handler = new HandlerTheirScans();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("THEIR", "onResume: ");
    }

    public class HandlerTheirScans extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.POPULATE:
                    populateListView();
                    break;
            }
        }
    }
}
