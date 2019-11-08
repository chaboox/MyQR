package com.deboosere.myqr;

import android.os.Bundle;

import com.deboosere.myqr.base.BaseSwipeBackActivity;

public class RightAcitivity extends BaseSwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_right_acitivity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_right_acitivity;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_out_right, R.anim.fade_left_finish);
    }
}
