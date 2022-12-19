package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class ActivityCamera extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    Slider slider;
    JavaCameraView javaCameraView;
    Mat mat1, mat2;
    Scalar scalarLow, scalarHigh;
    Mat src;
    Mat mrgba;
    MediaPlayer player;
    CountDownTimer countDownTimer=null;
    //  TextView mTextField = findViewById(R.id.textView11);
    //final MediaPlayer mp = MediaPlayer.create(this, R.raw.sample);
    TextView textView = findViewById(R.id.textView);



    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(ActivityCamera.this) {
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
        setContentView(R.layout.activity_camera);
        OpenCVLoader.initDebug();



        javaCameraView = findViewById(R.id.javaCameraView);

        javaCameraView.setCameraIndex(0);

        scalarLow = new Scalar(136,87,111);
        scalarHigh = new Scalar(180,255,255);


//        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(ActivityCamera.this);
        javaCameraView.enableView();

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        src = new Mat(height, width, CvType.CV_16UC4);

        mat1 = new Mat(height, width, CvType.CV_16UC4);
        mat2 = new Mat(height, width, CvType.CV_16UC4);
    }

    @Override
    public void onCameraViewStopped() {
        src.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

//        rysowanie kwadratu
        mrgba = inputFrame.rgba();
        int w = mrgba.width();
        int h = mrgba.height();
        int w_rect = w*3/4; // or 640
        int h_rect = h*3/4; // or 480

        Imgproc.rectangle(mrgba, new Point( (w-w_rect)/2, (h-h_rect)/2 ), new Point(
                        (w+w_rect)/2, (h+h_rect)/2 ), new Scalar( 255, 0, 0 ), 5);

            src = inputFrame.rgba();

            Imgproc.cvtColor(inputFrame.rgba(), mat1, Imgproc.COLOR_BGR2HSV) ;

            Core.inRange(mat1, scalarLow, scalarHigh, mat2);

            Core.MinMaxLocResult mmG = Core.minMaxLoc(mat2);


           Point punkt = mmG.maxLoc;
           int licznik =0;
           if(punkt.x > 200.0 && punkt.x < 800.0 && licznik <10)
           {
               Imgproc.circle(src, mmG.maxLoc, 25, new Scalar(0, 0, 255), 5, Imgproc.LINE_AA);
               try {
                   Thread.sleep(5000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               play();
               System.out.println("KOORDYNATY X: " +punkt.x);
               System.out.println("KOORDYNATY Y: " +punkt.y);
               textView.setText("Pozostało "+ (10-licznik) + "strzałów.");
               licznik++;
           }
        slider = findViewById(R.id.slider);
        int brightness = (int) slider.getValue();

        src.convertTo(src, -1, 1, brightness);
        return src;
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

    public void play()
    {
        if(player == null)
        {
            player = MediaPlayer.create(this, R.raw.sample);
        }
        player.start();
    }
}