package MyTest;






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

import javax.imageio.ImageIO;

import edu.ufl.digitalworlds.j4k.J4KSDK;

import javax.swing.JTextArea;

public class KinectVideoManager extends J4KSDK {

	int windowWidth;
	int windowHeight;
	byte[] videoBuffer;
	FileOutputStream exported_img;
	File report;
	SmileDetector smiley = new SmileDetector();
	int smile_counter =0;
	String serverUrl;
	int img_id = 0;
	JTextArea infoDisplay = null;
	BufferedWriter writer = null;
	
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
	

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
	    BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
	    Graphics2D g = dimg.createGraphics();  
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	    RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
	    g.drawImage(img, 0, 0, newW, newH, null);  
	    g.dispose();  
	    return dimg;  
	}  
	
	
	public void writeVideoFramePNG(int video_width, int video_height ,byte array[], int frame_id)
	{
		try {
			exported_img = new FileOutputStream("D:\\xampp\\htdocs\\img\\captured_img"+frame_id+".png");
			//org_img = new FileOutputStream(".\\video_stream\\img_"+frame_id);
			
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
				img.setRGB(x,y,(new Color(array[idx+2]&0xFF,array[idx+1]&0xFF,array[idx+0]&0xFF)).getRGB());
				idx+=4;
			}
			
			//ImageIO.write(img ,"PNG", org_img);
			BufferedImage scaledImg = resize(img,1920/3,1080/3);
			ImageIO.write(scaledImg,"PNG", exported_img);
		} catch (IOException e) {
        	e.printStackTrace();
        }
		
		infoDisplay.append("Image saved\n");
        System.out.println("Image saved");
        // Upload image here
        
	}
	
	@Override
	public void onColorFrameEvent(byte[] color_frame) {
		
		try {
		writer = new BufferedWriter(new FileWriter(report,true));
		
		windowWidth = getColorWidth();
		windowHeight = getColorHeight();
		
		//windowWidth = 800;
		//windowHeight = 600;
		
		videoBuffer = color_frame;
		infoDisplay.append("Starting smile detection...\n");
		
		writeVideoFramePNG(windowWidth,windowHeight,color_frame,img_id);
		String path = serverUrl + "//img//captured_img"+img_id+".png";
		
		try {
			writer.write("captured_img"+img_id+".png\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(smiley.detectSmile(path))
		{
			if (smiley.getSmile_value()>50)
			{
				smile_counter = smile_counter +1;
		
				try {
					writer.write("Smile detected\r\n");
					writer.write("Smile Confidence:"+ smiley.getSmile_value()+"\r\n");
					writer.write(smile_counter+" smile(s) detected\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				infoDisplay.append("Smile\n");
				infoDisplay.append("Smile Confidence:"+ smiley.getSmile_value()+"\n");
				infoDisplay.append(smile_counter+" smile(s) detected\n");
				
			
				
				
				System.out.println("Smile");
				System.out.println(smile_counter+" smile(s) detected");
				smile_counter = 0;
				
			}
			else
			{
				try {
					writer.write("No smile \r\n");
					
				} catch (IOException e) {
				
					}
				infoDisplay.append("No smile\n");
				System.out.println("No smile");
				smile_counter = 0;
			}
		}
		else
		{
			try {
				writer.write("Error during smile detection\r\n");
				
			} catch (IOException e) {
			
				}
			infoDisplay.append("Error during smile detection, please refer to console\n");
			System.out.println("Error during smile detection");
		}
		img_id=img_id+1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
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

	public void setColorSize(int i, int j) {
		// TODO Auto-generated method stub
		windowWidth=i;
		windowHeight=j;
	}
	
	
	
	

}


