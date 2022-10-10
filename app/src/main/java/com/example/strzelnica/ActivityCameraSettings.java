package com.example.strzelnica;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Arrays;

public class ActivityCameraSettings extends AppCompatActivity {

    private TextureView textureView;
    private CameraManager cameraManager;
    private String myCameraID;
    private CameraDevice myCameraDevice;
    private CaptureRequest.Builder myCaptureRequestBuilder;
    private CameraCaptureSession myCameraCaptureSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camerasettings);

        textureView = findViewById(R.id.textureViewCamera);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        openCamera();

        Button buttonStart = findViewById(R.id.buttonStart2);
        TextView textViewTime = findViewById(R.id.textViewTime);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStart.setVisibility(view.GONE);
                new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        textViewTime.setText("Start za : " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        textViewTime.setText("START");
                        new CountDownTimer(5000, 1000) {

                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {
                                textViewTime.setVisibility(view.GONE);
                            }

                        }.start();
                    }

                }.start();
            }
        });


    }

    private CameraDevice.StateCallback myStateCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            myCameraDevice = cameraDevice;
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            myCameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            myCameraDevice.close();
            myCameraDevice = null;
        }
    };

    private void openCamera() {
        try {
            myCameraID = cameraManager.getCameraIdList()[0];
            ActivityCompat.requestPermissions(ActivityCameraSettings.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraManager.openCamera(myCameraID, myStateCallBack, null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cameraPreview(View view){
        TextView textViewStart = findViewById(R.id.textViewStart);
        TextView textViewInfo = findViewById(R.id.textViewInfo);
        Button button = findViewById(R.id.button);
        Button buttonStart = findViewById(R.id.buttonStart2);

        textViewStart.setVisibility(view.GONE);
        textViewInfo.setVisibility(view.GONE);
        button.setVisibility(view.GONE);
        buttonStart.setVisibility(view.VISIBLE);


        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        Surface surface = new Surface(surfaceTexture);



        try {
            myCaptureRequestBuilder = myCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            myCaptureRequestBuilder.addTarget(surface);

            myCameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    myCameraCaptureSession = cameraCaptureSession;
                    myCaptureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                    try {
                        myCameraCaptureSession.setRepeatingRequest(myCaptureRequestBuilder.build(), null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                }
            }, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }


}