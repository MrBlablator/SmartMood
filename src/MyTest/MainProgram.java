package MyTest;

import edu.ufl.digitalworlds.j4k.J4KSDK;

public class MainProgram {
	
	public static void main(String[] args)
	{
		KinectVideoManager kinect = null;
		if(System.getProperty("os.arch").toLowerCase().indexOf("64")<0)
		{
			System.out.println("WARNING: You are running a 32bit version of Java.");
			System.out.println("This may reduce significantly the performance of this application.");
			System.out.println("It is strongly adviced to exit this program and install a 64bit version of Java.\n");
		}
		
		System.out.println("This program will run for about 20 seconds.");
		try {
			kinect=new KinectVideoManager();
		
			
			kinect.setServerUrl("https://rctest.ngrok.io");
		
			
		
			kinect.start(J4KSDK.COLOR);
			
			
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Sleep for 20 seconds.
		try {Thread.sleep(90000);} catch (InterruptedException e) {}
		
		
		kinect.stop();		
		//System.out.println("FPS: "+kinect.counter*1000.0/(new Date().getTime()-kinect.time));
	}

}
