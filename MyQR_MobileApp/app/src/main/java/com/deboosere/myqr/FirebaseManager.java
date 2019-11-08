package com.deboosere.myqr;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseManager {
    private FirebaseDatabase database ;

    public FirebaseManager() {
        database = FirebaseDatabase.getInstance();

    }

    public FirebaseManager(Context context) {
        FirebaseApp.initializeApp(context);
        database = FirebaseDatabase.getInstance();

    }

    public void scanPerson(String idUser, String idUserScanned){
        String time = System.currentTimeMillis() + "";
        database.getReference("my_scan").child(idUser).child(idUserScanned).child("time").setValue(time);
        database.getReference("their_scan").child(idUserScanned).child(idUser).child("time").setValue(time);
    }

    public void testFirebase(){
        String time = System.currentTimeMillis() + "";
        database.getReference("users").child("5").child("email").setValue("ASLQ@");
        database.getReference("users").child("7").child("email").setValue("ASLQ@");
        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.d("ALLK", "onDataChange: " + dataSnapshot1.getKey() + "   " + dataSnapshot1.child("email").getValue());
                }
               // String value = dataSnapshot.getValue(String.class);
              //  Log.d("uii", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("CANc", "Failed to read value.", error.toException());
            }
        });
    }

    public void getMyScanUsers(final ArrayList<UserModel> userModels){
       // MyScansFragment.handler.sendEmptyMessage(Constants.UPDATE);
        database.getReference().child("my_scan").child(Constants.USERCODE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    getUserByCode(dataSnapshot1.getKey(), userModels);
                }
                // String value = dataSnapshot.getValue(String.class);
                //  Log.d("uii", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("CANc", "Failed to read value.", error.toException());
            }
        });
    }

    private void getUserByCode(final String key, final ArrayList<UserModel> userModels) {
        database.getReference().child("users").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  int id =  removeElementByCode(key, userModels);
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
             //   for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.d("MPP", "onDataChange: " + dataSnapshot.getKey());
                    if(id == -1)
                    userModels.add(new UserModel(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("lastName").getValue().toString(),"","","","","","",key,""));

                    else userModels.set(id, new   UserModel(dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("lastName").getValue().toString(),"","","","","","",key,""));
                    MyScansFragment.handler.sendEmptyMessage(Constants.POPULATE);
              //  }
                // String value = dataSnapshot.getValue(String.class);
                //  Log.d("uii", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("CANc", "Failed to read value.", error.toException());
            }
        });
    }

    private int removeElementByCode(String key, ArrayList<UserModel> userModels) {
        int i = 0;
        for(UserModel e:userModels){
            if(key.equals(e.getCode()))
               return i;
            i++;

        }
        return -1;
    }


}
