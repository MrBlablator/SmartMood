package MyTest;






import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.ufl.digitalworlds.j4k.J4KSDK;

public class MyKinect extends J4KSDK {

	int windowWidth;
	int windowHeight;
	byte[] videoBuffer;
	FileOutputStream f;
	SmileDetector smiley = new SmileDetector();
	int smile_counter =0;
	
	
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	public byte[] getVideoBuffer() {
		return videoBuffer;
	
	}
	

	
	public void writeVideoFramePNG(int video_width, int video_height ,byte array[])
	{
		try {
			f = new FileOutputStream("captured_img.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			
			BufferedImage img=new BufferedImage(video_width,video_height,BufferedImage.TYPE_INT_RGB);
			int idx=0;
			for(int y=0;y<video_height;y++)
			for(int x=0;x<video_width;x++)
			{
				img.setRGB(x,y,(new Color(array[idx+0]&0xFF,array[idx+1]&0xFF,array[idx+2]&0xFF)).getRGB());
				idx+=4;
			}
			
			ImageIO.write(img,"PNG", f);
		} catch (IOException e) {
        	e.printStackTrace();
        }
        System.out.println("Image saved");
        // Upload image here
        
        smiley.detectSmile("http://i.imgur.com/dtG2Yu5.jpg");
        if (smiley.getSmile_value()>75)
        {
        	smile_counter = smile_counter +1;
        	System.out.println("Smile detected");
        	System.out.println(smile_counter+" smile(s) detected");
        }
        else
        {
        	System.out.println("No smile detected");
        }
		}
	
	@Override
	public void onColorFrameEvent(byte[] color_frame) {
		
		windowWidth = getColorWidth();
		windowHeight = getColorHeight();
		videoBuffer = color_frame;
		
		writeVideoFramePNG(windowWidth,windowHeight,color_frame);
		
		
	}

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
	
	public static void main(String[] args)
	{
		
		if(System.getProperty("os.arch").toLowerCase().indexOf("64")<0)
		{
			System.out.println("WARNING: You are running a 32bit version of Java.");
			System.out.println("This may reduce significantly the performance of this application.");
			System.out.println("It is strongly adviced to exit this program and install a 64bit version of Java.\n");
		}
		
		System.out.println("This program will run for about 20 seconds.");
		MyKinect kinect=new MyKinect();
		kinect.start(J4KSDK.COLOR|J4KSDK.DEPTH|J4KSDK.SKELETON);
		
		
		//Sleep for 20 seconds.
		try {Thread.sleep(20000);} catch (InterruptedException e) {}
		
		
		kinect.stop();		
		//System.out.println("FPS: "+kinect.counter*1000.0/(new Date().getTime()-kinect.time));
	}

}


