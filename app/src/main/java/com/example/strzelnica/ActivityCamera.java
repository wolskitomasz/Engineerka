package com.example.strzelnica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
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

import static xdroid.toaster.Toaster.toast;
import static xdroid.toaster.Toaster.toastLong;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ActivityCamera extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    Slider slider;
    JavaCameraView javaCameraView;
    Mat mat1, mat2;
    Scalar scalarLow, scalarHigh;
    Mat src;
//    Mat mrgba;
    MediaPlayer player;
    int licznik = 3;
    FileOutputStream fileOutputStream = null;
    int points = 0;
//    Core.MinMaxLocResult mmG = Core.minMaxLoc(mat2);
//    Point punkt = mmG.maxLoc;
//    CountDownTimer countDownTimer=null;


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

        File dir = getFilesDir();
        File file = new File(dir, "punkty");
        file.delete();
        try {
            fileOutputStream = openFileOutput("punkty", MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        javaCameraView = (JavaCameraView) findViewById(R.id.javaCameraView);

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

            src = inputFrame.rgba();
        int w = src.width();
        int h = src.height();
        int w_rect = w*3/4; // or 640
        int h_rect = h*3/4; // or 480

        int skala = w/774;
       // int points = 0;


            Imgproc.cvtColor(inputFrame.rgba(), mat1, Imgproc.COLOR_BGR2HSV) ;

            Core.inRange(mat1, scalarLow, scalarHigh, mat2);

        Core.MinMaxLocResult mmG = Core.minMaxLoc(mat2);
        Point punkt = mmG.maxLoc;

            // wyrzucone do gory
//            Core.MinMaxLocResult mmG = Core.minMaxLoc(mat2);

//          wyrzucone do gory
//           Point punkt = mmG.maxLoc;
        String punktx = String.valueOf(punkt.x * skala);
        String punkty = String.valueOf(punkt.y * skala);
           if(punkt.x > 100.0 && punkt.x < 100+h)
           {
//               if(licznik < 3)
//               {
                   Imgproc.circle(src, mmG.maxLoc, 25, new Scalar(0, 0, 255), 5, Imgproc.LINE_AA);
                   try {

                       Thread.sleep(4000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   play();
                   licznik++;
                   setText();

                   //zliczanie punktów
                   //10 punktow
                   if ((punkt.y + 100 > 292.00 * skala && punkt.y  + 100 < 486.00 * skala) && (punkt.x > 292.00 * skala && punkt.x < 486.00 * skala))
                   {
                       points+=10;

                   }
                   //8 punktów
                   else if ((punkt.y  + 100 > 196.00 * skala && punkt.y  + 100 < 581.00 * skala) && (punkt.x > 197.00 * skala && punkt.x < 582.00 * skala))
                   {
                       points+=8;
                   }
                   //4 punkty
                   else if ((punkt.y  + 100 > 102.00 * skala && punkt.y  + 100 < 677.00 * skala) && (punkt.x > 431.00 * skala && punkt.x < 676.00 * skala))
                   {
                       points+=4;
                   }
                   //2 punkty
                   else if ((punkt.y  + 100 > 5.00 * skala && punkt.y  + 100 < 774.00 * skala) && (punkt.x > 6.00 * skala && punkt.x < 774.00 * skala))
                   {
                       points+=2;
                   }

//                   System.out.println("KOORDYNATY X: " + (punkt.x * skala));
//                   System.out.println("KOORDYNATY Y: " + (punkt.y * skala));
//                   String punktX = punkt.x  +"\r\n" ;

//                   try {
//                       fileOutputStream.write(punktX.getBytes());
//                   } catch (IOException e) {
//                       e.printStackTrace();
//                   }

                }
               if (licznik == 3)
               {
                   try {
                       fileOutputStream.write(Integer.toString(points).getBytes());
//                       fileOutputStream.write(punkty.getBytes());
//                       fileOutputStream.write(punktx.getBytes());
                       fileOutputStream.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

//                   try {
//                       fileOutputStream.close();
//                   } catch (IOException e) {
//                       e.printStackTrace();
//                   }
                   startActivity(new Intent(ActivityCamera.this, ActivitySummary.class));

               }


//           }

        slider = findViewById(R.id.slider);
        int brightness = (int) slider.getValue();

        src.convertTo(src, -1, 1, brightness);

        Imgproc.rectangle(src,  new Point( 100 , 0), new Point( ((h+h)/2)+100,  (h+h)/2), new Scalar( 255, 0, 0 ), 5);

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
    public void setText()
    {
//        textView.setText("Pozostało "+ (10-licznik2) + "strzałów.");
       // Toast.makeText(getApplicationContext(), Integer.toString(licznik), Toast.LENGTH_LONG).show();
        toast("Pozostało " +Integer.toString(3-licznik)+" strzałów!");
    }

//    public void getPoints()
//    {
//        //10 punktow
//        if ((punkt.y > 292.00 && punkt.y < 486.00) && (punkt.x > 292.00 && punkt.x < 486.00))
//        {
//            points+=10;
//        }
//        //8 punktów
//        else if ((punkt.y > 196.00 && punkt.y < 581.00) && (punkt.x > 197.00 && punkt.x < 582.00))
//        {
//            points+=8;
//        }
//        //4 punkty
//        else if ((punkt.y > 102.00 && punkt.y < 677.00) && (punkt.x > 431.00 && punkt.x < 676.00))
//        {
//            points+=4;
//        }
//        //2 punkty
//        else if ((punkt.y > 5.00 && punkt.y < 774.00) && (punkt.x > 6.00 && punkt.x < 774.00))
//        {
//            points+=2;
//        }
//    }
}