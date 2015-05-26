package myTest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSmile  extends Thread {

	private Thread t;
	private String img_name;
	private String img_path;
	private SmileDetector smiley;
	private BufferedWriter writer;
	boolean smile;
	AtomicInteger smile_counter;
	
	/* Hello */
	ThreadSmile(String image_name, String image_link, BufferedWriter my_writer, AtomicInteger current_smile_counter){
		img_name = image_name;
		img_path= image_link;
		writer = my_writer;
		smile_counter = current_smile_counter;
		smiley = new SmileDetector();
		System.out.println("Thread for "  + img_name+" created");
		
	}
	
	@Override
	public synchronized void run() {
		try {
			
			System.out.println("Starting smile detection for "+img_name);
			System.out.println("Image link "+img_path);
			
			try {
				writer.write(img_name+"\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(smiley.detectSmile(img_path))
			{
				if (smiley.getSmile_value()>50)
				{
					/* Information update */
					smile = true;
					smile_counter.set(smile_counter.incrementAndGet());
					System.out.println(smile_counter);
			
					try {
						writer.write("Smile detected\r\n");
						writer.write("Smile Confidence:"+ smiley.getSmile_value()+"\r\n");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
					
					
					
				}
				else
				{
					try {
						smile = false;
						writer.write("No smile \r\n");
						
					} catch (IOException e) {
					
						}
					
					System.out.println("No smile");
					smile = false;
				}
			}
			else
			{
				try {
					writer.write(smiley.getError()+"\r\n");
					
				} catch (IOException e) {
				
					}
				
				System.out.println("Error during smile detection");
			}
		
			} 
			finally
			{
				try {
					String upload_sentence = "Upload time: " + smiley.getUpload_time() + " milliseconds\r\n";
					writer.write(upload_sentence);
					writer.write(smile_counter+" smile(s) currently detected\r\n\r\n ");
					writer.close();
					System.out.println(img_name+" upload ended");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
	}

	public void start()
	{
		System.out.println("Sending " + img_name);
		if (t == null){
			t  = new Thread(this, img_name);
			t.start();
		}
	}



}

