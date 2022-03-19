package com.example.appsselfhy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;

import androidx.appcompat.app.AlertDialog;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.example.appsselfhy.slidetab.MyAdapter;
import com.example.appsselfhy.slidetab.SlidingTabLayout;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity {

    //Button btnNotification;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    // akses permision di bawah
    private PopupWindow popupWindow;
    ConstraintLayout poplayout;
    WebView webView;
    private boolean webOn = false;
    private  boolean adaFlash;
    private boolean flashLightStatus = false;
    private Camera camera;
    Camera.Parameters params;
    private Uri fileUri;

    private  static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private  static final int MEDIA_TYPE_IMAGE = 1;
    private  static final String IMAGE_DIRECTORY_NAME = "SELFHY";
    private  static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 100;
    private  static final int MEDIA_TYPE_VIDEO = 2;


    private  final static  int REQUEST_CODE_ASK_PERMISSIONS =1;
    private  static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{

            Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        poplayout = findViewById(R.id.pop_layout);
        webView = findViewById(R.id.web_iklc);
        checkPermissions();
        //pop up web dan check permissions

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //viewPager

        mViewPager=(ViewPager)findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),this));


        // sliding fragment
        mSlidingTabLayout=(SlidingTabLayout)findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorLightOrang));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view,R.id.tv_tab);
        mSlidingTabLayout.setViewPager(mViewPager);


//----------------------------------

    }// tutup onCreate

    public  void camera(View view){
        String fileName = System.currentTimeMillis()+".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }// tutup method camera

    public  void flash(View view){
        adaFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (camera == null){

            try {
                camera = Camera.open();
                params = camera.getParameters();

            }catch (Exception e){
                Log.e("Error","flash:" + e.toString());

            }
        }
        if (adaFlash){
            if (flashLightStatus) flashLightOff();
            else flashLightOn();
        }

        else Toast.makeText(HomeActivity.this,"no available hardware", Toast.LENGTH_SHORT).show();
    } //tutup method flash

    private void flashLightOn(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId,true);
            flashLightStatus=true;
        }catch (CameraAccessException e){

        }

    }// tutup method flashON


    private void flashLightOff(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            flashLightStatus = false;
        }catch (CameraAccessException e){

        }
    }// tutup method flashOff

    private Uri getOutputMediaFileUri(int mediaTypeImage){
        return Uri.fromFile(getOutputMediaFile(mediaTypeImage));
    }// tutup method uri

    private File getOutputMediaFile(int mediaTypeImage) {
        File mediaStoreDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        if (!mediaStoreDir.exists()){
            if(!mediaStoreDir.mkdir()){
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create" + IMAGE_DIRECTORY_NAME + "directory");
                return null;
            }
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;

        if (mediaTypeImage == MEDIA_TYPE_IMAGE){
            mediaFile = new File (mediaStoreDir.getPath()+File.pathSeparator + "/IMG_" +timestamp +".jpg");
        }
        else if (mediaTypeImage == MEDIA_TYPE_VIDEO){
            mediaFile = new File (mediaStoreDir.getPath()+File.pathSeparator + "/VID_" +timestamp +".mp4");
        }
        else{
            return null;
        }

        return mediaFile;
    }//method output
    // onRequest dan check permission di blok bawah



    // method-method menu option dan item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(HomeActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }// tutup method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_location:

                Intent intent = new Intent (HomeActivity.this, MapsActivity.class);
                startActivity(intent);

                return true;

            case R.id.action_setting:
                //Toast.makeText(this, R.string.setting, Toast.LENGTH_LONG).show();
                final AlertDialog.Builder tutup = new AlertDialog.Builder(this);
                tutup.setMessage("Apakah benar ingin keluar ?")
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();

                return true;

            case R.id.action_about:
                //Toast.makeText(this, R.string.about, Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(
                        findViewById(R.id.activity_home),
                        "Shopee Apk produced by Selfhy Ginting",
                        Snackbar.LENGTH_LONG
                );
                snackbar.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    // penutup method Option

    public  void internet (View view){
        if (webOn) popupWindow.dismiss();
        else{
            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popupweb,null);

            popupWindow = new PopupWindow(layout,500,700);
            webView = layout.findViewById(R.id.web_iklc);

            webView.loadUrl("https://www.google.com/");
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(true);
            popupWindow.showAtLocation(this.findViewById(R.id.activity_home), Gravity.CENTER, 0,0);
            webOn = true;
        }
    } //tutup method internet

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length-1;index>=0;--index){
                    if(grantResults[index] != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"required permission '" + permissions[index]
                                + "'not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                break;
        }
    }// tutup method onRequest

    private void checkPermissions(){
        final List<String> missingPermissions = new ArrayList<>();
        for (final String permission : REQUIRED_SDK_PERMISSIONS){

            final int result = ContextCompat.checkSelfPermission(this, permission);

            if (result != PackageManager.PERMISSION_GRANTED){
                missingPermissions.add(permission);
            }
        }

        if(!missingPermissions.isEmpty()){
            final String[] permissions =  missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this,permissions, REQUEST_CODE_ASK_PERMISSIONS);
        }else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS,REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }

    }// tutup method chech permission


}