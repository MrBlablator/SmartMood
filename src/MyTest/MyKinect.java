package MyTest;






import java.util.Date;

import edu.ufl.digitalworlds.j4k.J4KSDK;

public class MyKinect extends J4KSDK {

	int windowWidth;
	int windowHeight;
	byte[] videoBuffer;
	
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	public byte[] getVideoBuffer() {
		return videoBuffer;
	
	}
	
	
	
	@Override
	public void onColorFrameEvent(byte[] color_frame) {
		
		windowWidth = getColorWidth();
		windowHeight = getColorHeight();
		videoBuffer = color_frame;
		
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


