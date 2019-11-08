package com.deboosere.myqr;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

public class UserPopUp {
    Activity mActivity;
    Dialog dialog;

    public void showDialog(Activity activity){
        mActivity = activity;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_user);

        /*Button CallAsker = (Button) dialog.findViewById(R.id.callasker);
        CallAsker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete
               // FormActivity.handler.sendEmptyMessage(Constant.CANCEL_TASK);
                dialog.dismiss();
            }
        });*/
        dialog.show();

    }

    public void dismiss(){
        dialog.dismiss();
    }
}

