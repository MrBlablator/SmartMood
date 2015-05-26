package myTest;






import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import edu.ufl.digitalworlds.j4k.J4KSDK;

import javax.swing.JTextArea;

/* Principal class of the application, allows communication with the Kinect sensor and
 * handle frame reception
 */
public class KinectVideoManager extends J4KSDK {

	int windowWidth;
	int windowHeight;
	byte[] videoBuffer;
	FileOutputStream exported_img;
	File report;
	SmileDetector smiley = new SmileDetector();
	public AtomicInteger  smile_counter = new AtomicInteger(0);
	String serverUrl;
	int img_id = 0;
	JTextArea infoDisplay = null;
	BufferedWriter writer = null;
	
	// On call a report is created
	public KinectVideoManager(){
		report = new File("report.txt");
		System.out.print("report created");
		 try {
			writer = new BufferedWriter(new FileWriter(report,true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			writer.write("Report of smile detection\r\n\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/* Basic getters and setters */
	public void setInfoDisplay(JTextArea textBox){
		infoDisplay = textBox;
	}
	
	public void setServerUrl(String url){
		serverUrl = url;
	}
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	public byte[] getVideoBuffer() {
		return videoBuffer;
	
	}
	

	/* Resizing function if the image is too big */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
	    BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
	    Graphics2D g = dimg.createGraphics();  
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	    RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
	    g.drawImage(img, 0, 0, newW, newH, null);  
	    g.dispose();  
	    return dimg;  
	}  
	
	
	/* Function that allows the conversion from a Kinect video frame to a png file* */
	public void writeVideoFramePNG(int video_width, int video_height ,byte array[], int frame_id)
	{
		try {
			exported_img = new FileOutputStream("D:\\xampp\\htdocs\\img\\captured_img"+frame_id+".png"); // "hard" path to the web server
			//org_img = new FileOutputStream(".\\video_stream\\img_"+frame_id);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			/* Rendering of the image */
			BufferedImage img=new BufferedImage(video_width,video_height,BufferedImage.TYPE_INT_RGB);
			int idx=0;
			for(int y=0;y<video_height;y++)
			for(int x=0;x<video_width;x++)
			{
				img.setRGB(x,y,(new Color(array[idx+2]&0xFF,array[idx+1]&0xFF,array[idx+0]&0xFF)).getRGB());
				idx+=4;
			}
			
			//ImageIO.write(img ,"PNG", org_img);
			
			/* Scaling */
			BufferedImage scaledImg = resize(img,1920/3,1080/3);
			
			/* Image writing */
			ImageIO.write(scaledImg,"PNG", exported_img);
		} catch (IOException e) {
        	e.printStackTrace();
        }
		
		/* log and information update */
		infoDisplay.append("Image saved\n");
        System.out.println("Image saved");
        // Upload image here
        
	}
	
	/* Function that is called every time a frame is provided by the Kinect camera */
	@Override
	public void onColorFrameEvent(byte[] color_frame) {
		
		/* Frame saving */
		windowWidth = getColorWidth();
		windowHeight = getColorHeight();
		writeVideoFramePNG(windowWidth,windowHeight,color_frame,img_id);
		String img_path = serverUrl + "//img//captured_img"+img_id+".png";
		videoBuffer = color_frame; // frame is added as property for future uses
		
		
		/* Smile detection using SmileDetector class 
		 * Information is updated depending on the smile detection result
		 */
		try {
			writer = new BufferedWriter(new FileWriter(report,true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ThreadSmile smileyThread = new ThreadSmile("captured_img"+img_id+".png",img_path,writer,smile_counter);
		smileyThread.start();
	
		
		img_id=img_id+1;
		
		
		
		
	}

	
	/* Never used */
	
	@Override
	public void onDepthFrameEvent(short[] arg0, byte[] arg1, float[] arg2,
			float[] arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkeletonFrameEvent(boolean[] arg0, float[] arg1,
			float[] arg2, byte[] arg3) {
		// TODO Auto-generated method stub
		
	}

	
	public void setColorSize(int i, int j) {
		// TODO Auto-generated method stub
		windowWidth=i;
		windowHeight=j;
	}
	
	
	
	

}


