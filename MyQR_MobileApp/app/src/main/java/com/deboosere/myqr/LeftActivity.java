package com.deboosere.myqr;

import android.os.Bundle;
import android.widget.ListView;

import com.deboosere.myqr.base.BaseSwipeBackActivity;

import java.util.ArrayList;

public class LeftActivity extends BaseSwipeBackActivity {
    ListView listView;
    CustomContactAdapter customContactAdapter;
    ArrayList<UserModel> userModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        customContactAdapter = new CustomContactAdapter(userModels, getApplicationContext());
        listView.setAdapter(customContactAdapter);
    //    setContentView(R.layout.activity_left);
     //   fromLeftRb.setChecked(true);

    }

    private void initView() {
        listView = findViewById(R.id.list_view);
        userModels = new ArrayList<>();
        userModels.add(new UserModel("Adam","DEBOOSERE","","","","","","","",""));
        userModels.add(new UserModel("Adel","Achour","","","","","","","",""));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_left;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_out_right, R.anim.fade_right_finish);
    }
}
