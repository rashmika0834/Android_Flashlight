package com.rashmika.androidtorch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageButton = findViewById(R.id.torchButton);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new BasePermissionListener() {

            @Override
            public void onPermissionGranted(PermissionGrantedResponse response){
                    runFlashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response){
                Toast.makeText(MainActivity.this,"Camera permission required", Toast.LENGTH_SHORT).show();
            }


        }).check();
    }

    private void runFlashlight(){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v){
                if (!state){
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                            try{
                                String cameraID = cameraManager.getCameraIdList()[0];
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    cameraManager.setTorchMode(cameraID, true);
                                }
                                state = true;
                                imageButton.setImageResource(R.drawable.torch_on);


                            } catch (CameraAccessException e) {

                            }
                }

                else {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try{
                        String cameraID = cameraManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraID, false);
                        }
                        state = false;
                        imageButton.setImageResource(R.drawable.torch_off);


                    } catch (CameraAccessException e) {

                    }
                }
            }
        });
    }

}