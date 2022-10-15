package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.google.android.material.slider.Slider;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class test_camera extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    Slider slider;
    JavaCameraView javaCameraView;
    Mat mRGBAT;
    Mat mat1, mat2;
    Scalar scalarLow, scalarHigh;
    Mat src;


    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(test_camera.this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status)
            {
                case BaseLoaderCallback.SUCCESS:
                {
                    javaCameraView.enableView();
                    break;
                }
                default:
                {
                    super.onManagerConnected(status);
                    break;
                }
            }
            super.onManagerConnected(status);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camera);
        OpenCVLoader.initDebug();

        javaCameraView = findViewById(R.id.javaCameraView);

        javaCameraView.setCameraIndex(0);
        scalarLow = new Scalar(0,70,50);
        scalarHigh = new Scalar(10,255,255);

//        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(test_camera.this);
        javaCameraView.enableView();

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        src = new Mat(height, width, CvType.CV_8UC4);

        mat1 = new Mat(height, width, CvType.CV_8UC4);
        mat2 = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        src.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//        mRGBA = inputFrame.rgba();
//        mRGBAT = mRGBA.t();
//        Core.flip(mRGBA.t(), mRGBAT, 1);
//        Imgproc.resize(mRGBAT, mRGBAT, mRGBA.size());
//
//        return mRGBAT;


        src = inputFrame.rgba();
        mRGBAT = src.t();

        Core.flip(src.t(), mRGBAT, 1);
        Imgproc.resize(mRGBAT, mRGBAT, src.size());
        Imgproc.cvtColor(inputFrame.rgba(), mat1, Imgproc.COLOR_BGR2HSV);
        Core.inRange(mat1,scalarLow,scalarHigh,mat2);
        Core.MinMaxLocResult mmG = Core.minMaxLoc(mat2);

        Imgproc.circle(mRGBAT, mmG.maxLoc, 30, new Scalar(0,255,0), 5, Imgproc.LINE_AA);

        slider = findViewById(R.id.slider);
        int brightness = (int) slider.getValue();

        mRGBAT.convertTo(mRGBAT,-1,1,brightness);


        return mRGBAT;

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (javaCameraView != null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (javaCameraView != null)
        {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug())
        {
            Log.e("OpenCV", "Unable to load OpenCV!");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
        }

        else
        {
            Log.d("OpenCV", "OpenCV loaded Successfully!");
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        }
    }
}