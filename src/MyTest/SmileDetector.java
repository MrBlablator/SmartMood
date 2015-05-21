package MyTest;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.facepp.result.FaceppResult;

/* This class enable communication with the faceplusplus API and convert the JSON result in usable data */
public class SmileDetector {

		FaceppResult result = null;
		double smile_value=0;
		long upload_time = 0;
		String error = null;
		
	/* Basic getters and setters */
	public FaceppResult getResult() {
		return result;
	}
	
	public double getSmile_value() {
		return smile_value;
	}
	
	public double getUpload_time(){
		return upload_time;
	}
	
	public String getError() {
		// TODO Auto-generated method stub
		return error;
	}
	
	/* Main function that send a http request to face plus plus API with the link of the image to the server */
	public Boolean detectSmile(String image_path) {
		
		// First is identification request to allow the use of the API
		HttpRequests httpRequests = new HttpRequests("2f0c3fd5708b4b225968281727e754da", "tQoz3GSQNXuy69hz10fuw2FxEQexxhD1");
		upload_time = 0;
		
		
		System.out.println("Smile detection");
		
		//detection/detect
		try {
			long startTime = System.currentTimeMillis(); // Timer
			
			/* This sends a request with the image url and store the result */
			 result = httpRequests.detectionDetect(new PostParameters().setUrl(image_path));
			 long endTime = System.currentTimeMillis();
			 upload_time = endTime - startTime;
			 
			 /* Information update */
			 System.out.println("Upload time: " + (endTime - startTime) + " milliseconds");
			 System.out.println(result);
		} catch (FaceppParseException e) {
			// if problem with API, error message is updated
			error = "API error";
			e.printStackTrace();
			return false;
		}
		/* Results from the API are analyzed */
		try {
			if(result.get("face").getCount()!=0) // If a face is detected
			{
				//System.out.println(result.get("face").getCount());
				String smile =result.get("face").get(0).get("attribute").toString(); // Get the smile value of the first face detected (arbitrary)
				smile_value = Double.parseDouble(smile.substring(smile.indexOf("value") +7, smile.indexOf("value") +11).trim()); // Store this value.
			
				/* Update informations */
				System.out.println("Smile detection succeed");
				System.out.println("Smile confidence: "+smile_value);
			return true;
			}
			else
			{
				// If no face is detected
				error = "No face detected"; // update error message 
				System.out.println("No face detected"); // update information
				return false;
			}
		
		} catch (FaceppParseException e) {
			
			// never reached
			e.printStackTrace();
			error = "API error";
			return false;
		}
		

	}



}
