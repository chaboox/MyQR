
package com.deboosere.myqr.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deboosere.myqr.AnimationManager;
import com.deboosere.myqr.CashManager;
import com.deboosere.myqr.Constants;
import com.deboosere.myqr.OnSwipeListener;
import com.deboosere.myqr.QRCodeGenerator;
import com.deboosere.myqr.R;
import com.deboosere.myqr.User;
import com.deboosere.myqr.UserPopUp;
import com.deboosere.myqr.ViewPagerActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public final class BarcodeCaptureActivity extends AppCompatActivity implements BarcodeGraphicTracker.BarcodeUpdateListener, View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "Barcode-reader";
    private LinearLayout linearLayout;

    // intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;

    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // constants used to pass extra data in the intent
    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String BarcodeObject = "Barcode";
    private Toast toast;
    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    public static GraphicOverlay<BarcodeGraphic> mGraphicOverlay;

    // helper objects for detecting taps and pinches.
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private GestureDetector gestureDetector2;
    private Button done;
    private ImageView goLeft, goRight, qrIcon, qrImage;
    private CardView cardView;
    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.barcode_capture);

        initGesturePosture();
        initView();
        setListener();




        // read parameters from the intent used to launch the activity.
        boolean autoFocus = getIntent().getBooleanExtra(AutoFocus, false);
        boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(true, useFlash);
        } else {
            requestCameraPermission();
        }

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

       /* Snackbar.make(mGraphicOverlay, "Tap to capture. Pinch/Stretch to zoom",
                Snackbar.LENGTH_LONG)
                .show();*/
        User user = CashManager.getScan(getPreferences(MODE_PRIVATE));
        //(qrImage).setImageBitmap(new QRCodeGenerator().getQRCode(barcode.rawValue));
        ((TextView)findViewById(R.id.name)).setText("Nom: " + user.nom );
        ((TextView)findViewById(R.id.phone)).setText("Telephone: " + user.telephone);
        ((TextView)findViewById(R.id.email)).setText("Email: " + user.email);
        ((TextView)findViewById(R.id.blood)).setText("G. Sang: " + user.sang);
        ((TextView)findViewById(R.id.address)).setText("Adresse: " + user.address);
    }

    private void initGesturePosture() {
        gestureDetector2 =new GestureDetector(this,new OnSwipeListener(){

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction==Direction.up){
                    //do your stuff
                    Log.d(TAG, "onSwipe: up");

                }

                if (direction==Direction.down){
                    //do your stuff
                    Log.d(TAG, "onSwipe: down");
                }

                if (direction==Direction.right){
                    //do your stuff
                   /* new AnimationManager().RotageImageView(goRight, Constants.TWO_LOOPS, 500);
                    Intent i2 = new Intent(BarcodeCaptureActivity.this, RightAcitivity.class);
                    startActivity(i2);
                    overridePendingTransition(R.anim.fade_in_right, R.anim.fade_out_right);
                    Log.d(TAG, "onSwipe: right");*/

                }

                if (direction==Direction.left){
                    //do your stuff
                   /* new AnimationManager().RotageImageView(goLeft, Constants.TWO_LOOPS, 500);
                    Intent i = new Intent(BarcodeCaptureActivity.this, ViewPagerActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left);
                    Log.d(TAG, "onSwipe: left");*/

                }
                return true;
            }


        });
    }

    public void initView(){
       mPreview = findViewById(R.id.preview);
       mGraphicOverlay = findViewById(R.id.graphicOverlay);
       done = findViewById(R.id.done_g);
       goLeft = findViewById(R.id.goLeft);
       goRight = findViewById(R.id.goRight);
       qrIcon = findViewById(R.id.qr_icon);
       linearLayout = findViewById(R.id.topLayout);
       qrImage = findViewById(R.id.image);
        (qrImage).setImageBitmap(new QRCodeGenerator().getQRCode("chaboox@gmail.com"));
        cardView = findViewById(R.id.cardview2);

   }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goLeft:
                new AnimationManager().RotageImageView(goLeft, Constants.TWO_LOOPS, 500);
                Intent i = new Intent(BarcodeCaptureActivity.this, ViewPagerActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left);
                break;

             case R.id.goRight:
                 new UserPopUp().showDialog(this);
               /* new AnimationManager().RotageImageView(goRight, Constants.TWO_LOOPS, 500);
                Intent i2 = new Intent(BarcodeCaptureActivity.this, RightAcitivity.class);
                startActivity(i2);
                overridePendingTransition(R.anim.fade_in_right, R.anim.fade_out_right);*/
                break;
            case R.id.qr_icon:
                if(cardView.getVisibility() == View.INVISIBLE){
                    new AnimationManager().RotageImageView(qrIcon, Constants.INFINITE_LOOPS, 700);
                    new AnimationManager().slideToBottom(cardView);}
                else {
                    new AnimationManager().slideToTop(cardView);
                    new AnimationManager().StopRotationImageView(qrIcon);
                }
                break;
        }
    }

   public void setListener(){
       goLeft.setOnClickListener(this);
       goRight.setOnClickListener(this);
       qrIcon.setOnClickListener(this);
       linearLayout.setOnTouchListener(this);
   }

    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        findViewById(R.id.topLayout).setOnClickListener(listener);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean b = scaleGestureDetector.onTouchEvent(e);

        boolean c = gestureDetector.onTouchEvent(e);

        return b || c || super.onTouchEvent(e);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: LOLOL2" );
        super.onStop();
        mPreview.stop();

    }
    
    



    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay, this);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
               // Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                //Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
        }

        mCameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
           // mPreview.stop();
        }
        Log.d(TAG, "onPause: MOMO");

    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            boolean autoFocus = getIntent().getBooleanExtra(AutoFocus,false);
            boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
            createCameraSource(true, useFlash);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage("Permission camera denied")
                .setPositiveButton("OK", listener)
                .show();
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    /**
     * onTap returns the tapped barcode result to the calling Activity.
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the activity is ending.
     */
    private boolean onTap(float rawX, float rawY) {
        // Find tap point in preview frame coordinates.
        int[] location = new int[2];
        mGraphicOverlay.getLocationOnScreen(location);
        float x = (rawX - location[0]) / mGraphicOverlay.getWidthScaleFactor();
        float y = (rawY - location[1]) / mGraphicOverlay.getHeightScaleFactor();

        // Find the barcode whose center is closest to the tapped point.
        Barcode best = null;
        float bestDistance = Float.MAX_VALUE;
        for (BarcodeGraphic graphic : mGraphicOverlay.getGraphics()) {
            Barcode barcode = graphic.getBarcode();
            if (barcode.getBoundingBox().contains((int) x, (int) y)) {
                // Exact hit, no need to keep looking.
                best = barcode;
                break;
            }
            float dx = x - barcode.getBoundingBox().centerX();
            float dy = y - barcode.getBoundingBox().centerY();
            float distance = (dx * dx) + (dy * dy);  // actually squared distance
            if (distance < bestDistance) {
                best = barcode;
                bestDistance = distance;
            }
        }

        if (best != null) {
            Intent data = new Intent();
            data.putExtra(BarcodeObject, best);
            setResult(CommonStatusCodes.SUCCESS, data);
            finish();
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d(TAG, "onTouch: ");
        gestureDetector2.onTouchEvent(motionEvent);
        return true;
    }

    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
          return true;
            // for now we don't need to choose return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mCameraSource.doZoom(detector.getScaleFactor());
        }
    }

    @Override
    public void onBarcodeDetected(final Barcode barcode) {
        //do something with barcode data returned
        Log.d(TAG, "onBarcodeDetected: "+barcode.rawValue+" "+barcode.format+" "+barcode.valueFormat);
        //TODO do what you have todo

        String transformedCode = transformeCode(barcode.rawValue);
        //FirebaseApp.initializeApp(getApplicationContext());
       // new FirebaseManager(getApplicationContext()).scanPerson("x3@gmail!?!com",transformedCode);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + barcode.rawValue);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                if (value != null){
                    (qrImage).setImageBitmap(new QRCodeGenerator().getQRCode(barcode.rawValue));
                    if(value.actif == true){

                ((TextView)findViewById(R.id.name)).setText("Nom: " + value.nom );
                ((TextView)findViewById(R.id.phone)).setText("Telephone: " + value.telephone);
                ((TextView)findViewById(R.id.email)).setText("Email: " + value.email);
                ((TextView)findViewById(R.id.blood)).setText("G. Sang: " + value.sang);
                ((TextView)findViewById(R.id.address)).setText("Adresse: " + value.address);
                        CashManager.SaveScan(value, getPreferences(MODE_PRIVATE));}
                else {
                        ((TextView)findViewById(R.id.name)).setText("User inactif" );
                        ((TextView)findViewById(R.id.phone)).setText("" );
                        ((TextView)findViewById(R.id.email)).setText("" );
                        ((TextView)findViewById(R.id.blood)).setText("");
                        ((TextView)findViewById(R.id.address)).setText("");
                    }
                    if(cardView.getVisibility() == View.INVISIBLE){
                        new AnimationManager().RotageImageView(qrIcon, Constants.INFINITE_LOOPS, 700);
                        new AnimationManager().slideToBottom(cardView);}
                    CashManager.SaveScan(value, getPreferences(MODE_PRIVATE));
                }
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
      {



              // ((Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(200); // for 200 ms

            //   (new ToneGenerator(AudioManager.STREAM_MUSIC, 100)).startTone(ToneGenerator.TONE_CDMA_PRESSHOLDKEY_LITE,200);
        //   MainActivity.barcodeDisplay.add(barcode.displayValue);
           int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
           String currentMinute = Calendar.getInstance().get(Calendar.MINUTE)+"";
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           Calendar c = Calendar.getInstance();
           String date = sdf.format(c.getTime());
           if(currentMinute.length() == 1) currentMinute = "0"+currentMinute;
          // MainActivity.barcodeDisplayData.add(new BarcodeData(barcode.displayValue, intCodeToString(barcode.format), currentHour + ":" + currentMinute, date));
       }
    }

    private String transformeCode(String rawValue) {
        return rawValue.replace(".", "!?!");
    }

    @Override
    public void onBackPressed() {
       // Intent data = new Intent(this, MainActivity.class);
      //  startActivity(data);
        finish();
    }
    public String intCodeToString(int code){
        if(code == 1) return "Code-128";
        else if(code == 2) return "Code-39";
        else if(code == 32) return "EAN-13";
        else if(code == 256) return "QR Code";
        else return "Code "+code;
    }
}
