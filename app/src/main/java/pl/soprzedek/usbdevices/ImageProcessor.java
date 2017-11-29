package pl.soprzedek.usbdevices;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.widget.ImageView;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.android.Utils;

import java.io.File;

/**
 * Og�lna klasa pokazuj�ca w podanym panelu obraz otrzymany z capture
 * W�tek UpdateThread aktualizuje obraz wywo�uj�c processImage
 * 
 * @author pawel
 *
 */
public class ImageProcessor {
	String prefix;
	ImageView imageView;
	VideoCapture capture;
	boolean capturingImages = true;

	public boolean isCapturingImages() {
		return capturingImages;
	}

	public void setCapturingImages(boolean capturingImages) {
		this.capturingImages = capturingImages;
	}

	public VideoCapture getCapture() {
		return capture;
	}

		
	class UpdateThread implements Runnable {
		@Override
		public void run() {
			imageView.setColorFilter(Color.CYAN);
			while(capture.isOpened())  {
				imageView.setColorFilter(Color.GRAY);
				Mat image = new Mat();
				capture.read(image);  
				processImage(image);
			}
		}
	}

	
	public ImageProcessor(String prefix, ImageView panel, VideoCapture capture) {
		super();
		this.prefix = prefix;
		this.imageView = panel;
		this.capture = capture;
		Thread thread = new Thread(new UpdateThread());
		thread.start();
//        readFromFile(panel, capture);
	}

	public void readFromFile(ImageView panel, VideoCapture capture){
		String file = "/storage/emulated/0/test.jpg";
		Mat matrix = new Imgcodecs().imread(file);
		Bitmap bitmap = Bitmap.createBitmap(matrix.cols(), matrix.rows(),
				Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(matrix, bitmap);
//		Bitmap bitmap = BitmapFactory.decodeFile(file, options);
		imageView.setImageBitmap(bitmap);
	}

	long lastUpdate = 0;
	public void processImage(Mat image) {
		//tu si� co� b�dzie dzia�o w klasach dziedzicz�cych!
		long ts = System.currentTimeMillis();
		double dfreq =0;
		if(ts>lastUpdate+1) {
			dfreq = 1000 / (ts - lastUpdate);
		}
		int freq = (int)dfreq;
		lastUpdate = ts;
		Imgproc.putText(image, ""+freq, new Point(10,450), 0, 1, new Scalar(0,0,255));
		
		if(capturingImages) {
			String file = "capture/"+prefix+"_"+System.currentTimeMillis()+".jpg";
			//imagesToSave.put(file, new SerializableMat(image));
			Imgcodecs.imwrite(file, image);
		}
		Bitmap bitmap = null;
		Utils.matToBitmap(image, bitmap);
		imageView.setColorFilter(Color.RED);
		if(bitmap == null)
			imageView.setColorFilter(Color.YELLOW);
		imageView.setImageBitmap(bitmap);
	}
}
