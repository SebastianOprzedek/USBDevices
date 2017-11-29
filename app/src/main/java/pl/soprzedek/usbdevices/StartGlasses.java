package pl.soprzedek.usbdevices;

import android.graphics.Color;
import android.widget.ImageView;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

import java.io.File;

import static org.opencv.highgui.Highgui.CV_CAP_ANDROID;

/**
 * Created by sebas on 08.11.2017.
 */

public class StartGlasses {
    ImageProcessor eyeProc;
    ImageProcessor sceneProc;
    VideoCapture captureEye;
    VideoCapture captureScene;
    int eyeCamera = CV_CAP_ANDROID;
    int sceneCamera = CV_CAP_ANDROID + 1;
    ImageView imageView;
    ImageView imageView2;

    public StartGlasses(ImageView imageView, ImageView imageView2) {
        this.imageView = imageView;
        this.imageView2 = imageView2;
        System.loadLibrary("opencv_java3");

        new File("capture").mkdirs();

        captureEye =new VideoCapture(eyeCamera);
        captureScene =new VideoCapture(sceneCamera);
        //eye=2
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setColorFilter(Color.BLACK);
        if(captureEye.isOpened())
            imageView.setColorFilter(Color.GRAY);
        else
            imageView.setColorFilter(Color.MAGENTA);
        if(captureScene.isOpened())
            imageView2.setColorFilter(Color.GRAY);
        else
            imageView2.setColorFilter(Color.MAGENTA);

        //eyeProc = new ImageProcessor("INZCAMERA1",imageView, captureEye);
        //sceneProc = new ImageProcessor("INZCAMERA2",imageView2, captureScene);

        //true powoduje zapis kolejnych obraz�w do /capture
//        eyeProc.setCapturingImages(false);
//        sceneProc.setCapturingImages(false);


//        JFrame eyeFrame = new JFrame("Eye image");
//        eyeFrame.getContentPane().add(eyePanel);
//        eyeFrame.setSize(660,520);
//        eyeFrame.setLocation(10, 10);
//        eyeFrame.setVisible(true);

//        JFrame sceneFrame = new JFrame("Scene");
//        sceneFrame.getContentPane().add(scenePanel);
//        sceneFrame.setSize(660,520);
//        sceneFrame.setLocation(500, 50);
//        sceneFrame.setVisible(true);
    }

    public void refersh(int i){
        captureEye =new VideoCapture(i);
        captureScene =new VideoCapture(i+1);
        //eye=2
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setColorFilter(Color.BLACK);
        if(captureEye.isOpened())
            imageView.setColorFilter(Color.GRAY);
        else
            imageView.setColorFilter(Color.MAGENTA);
        if(captureScene.isOpened())
            imageView2.setColorFilter(Color.GRAY);
        else
            imageView2.setColorFilter(Color.MAGENTA);
    }
}
