package com.deboosere.myqr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomContactAdapter extends ArrayAdapter<UserModel> implements View.OnClickListener{

    private ArrayList<UserModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        ImageView imageProfil;
    }



    public CustomContactAdapter(ArrayList<UserModel> data, Context context) {
        super(context, R.layout.row_contact, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        UserModel dataModelBarcode =(UserModel)object;




        switch (v.getId()) {

         /*   case R.id.item_info:
                /*               MainActivity.barcodeDisplay.remove(MainActivity.barcodeDisplay.indexOf(dataModelBarcode.code));*/
            /*   Snackbar.make(v, " " + dataModelBarcode.code+ " deleted ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;


        }*/
        }

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserModel userModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_contact, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.imageProfil = (ImageView) convertView.findViewById(R.id.profile_image);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;


        viewHolder.name.setText(userModel.getFirstName() + " " + userModel.getLastName());
       // Glide.with(getContext()).load(userModel.getImageUrl()).into(viewHolder.imageProfil);

      //  viewHolder.info.setOnClickListener(this);
       // viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }


}
