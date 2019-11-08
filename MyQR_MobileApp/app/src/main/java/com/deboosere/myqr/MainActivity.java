package com.deboosere.myqr;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import com.deboosere.myqr.camera.BarcodeCaptureActivity;

public class MainActivity extends AppCompatActivity {
    public static boolean once;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if(!once){
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
          //  Log.d("KOKO", "onCreate: ");
           // once = true;
        //}
        //DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference();
        //scoresRef.keepSynced(true);


        //FirebaseApp.initializeApp(getApplicationContext());
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
           startActivity(intent);
     findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            // Intent i = new Intent(MainActivity.this, LeftActivity.class);
             //startActivity(i);
            // overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left);
             new AnimationManager().slideToBottom((findViewById(R.id.image)));
         }
     });
        ((ImageView)findViewById(R.id.image)).setImageBitmap(new QRCodeGenerator().getQRCode("chaboox@gmail.com"));

    }
}
