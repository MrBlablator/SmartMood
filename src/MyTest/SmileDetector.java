package MyTest;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.facepp.result.FaceppResult;

public class SmileDetector {

		FaceppResult result = null;
		double smile_value=0;
		long upload_time = 0;
		String error = null;
		
	public FaceppResult getResult() {
		return result;
	}
	
	public double getSmile_value() {
		return smile_value;
	}
	
	public double getUpload_time(){
		return upload_time;
	}
	
	public Boolean detectSmile(String image_path) {
		
		HttpRequests httpRequests = new HttpRequests("2f0c3fd5708b4b225968281727e754da", "tQoz3GSQNXuy69hz10fuw2FxEQexxhD1");
		upload_time = 0;
		
		
		System.out.println("Smile detection");
		
		//detection/detect
		try {
			long startTime = System.currentTimeMillis();
			 result = httpRequests.detectionDetect(new PostParameters().setUrl(image_path));
			 long endTime = System.currentTimeMillis();
			 upload_time = endTime - startTime;
			 System.out.println("Upload time: " + (endTime - startTime) + " milliseconds");
			 System.out.println(result);
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			error = "API error";
			e.printStackTrace();
			return false;
		}
		try {
			if(result.get("face").getCount()!=0)
			{
				System.out.println(result.get("face").getCount());
				String smile =result.get("face").get(0).get("attribute").toString();
				smile_value = Double.parseDouble(smile.substring(smile.indexOf("value") +7, smile.indexOf("value") +11).trim());
			
				System.out.println("Smile detection succeed");
				System.out.println("Smile confidence: "+smile_value);
			return true;
			}
			else
			{
				error = "No face detected";
				System.out.println("No face detected");
				return false;
			}
		
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error = "API error";
			return false;
		}
		

	}

	public String getError() {
		// TODO Auto-generated method stub
		return error;
	}

}
