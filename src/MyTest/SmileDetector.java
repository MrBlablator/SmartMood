package MyTest;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.facepp.result.FaceppResult;

public class SmileDetector {

		FaceppResult result = null;
		double smile_value=0;
		
		
	public FaceppResult getResult() {
		return result;
	}
	
	public double getSmile_value() {
		return smile_value;
	}
	
	public Boolean detectSmile(String image_path) {
		
		HttpRequests httpRequests = new HttpRequests("2f0c3fd5708b4b225968281727e754da", "tQoz3GSQNXuy69hz10fuw2FxEQexxhD1");
		
		
		
		System.out.println("Smile detection");
		
		//detection/detect
		try {
			 result = httpRequests.detectionDetect(new PostParameters().setUrl(image_path));
			 System.out.println(result);
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
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
				System.out.println("No face detected");
				return false;
			}
		
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		

	}

}
