import java.io.*;
import org.apache.commons.net.ftp.*;

class ImageUpload {
	private String username, password;

    public ImageUpload(String username, String password) {
    	
    	this.username = username;
    	this.password = password;
    	
    }

    public String uploadImage(String filename, String filePath) {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        boolean success = false;

        try {
        	
        	int reply;
            client.connect("ftp.richardluong.se");
            System.out.println("Connected");
            System.out.print(client.getReplyString());

            reply = client.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {
              client.disconnect();
              System.err.println("FTP server refused connection.");
              System.exit(1);
            }
            
            // Create an InputStream of the file to be uploaded
            fis = new FileInputStream(filePath);

            // Store file to server
            success = client.storeFile(filename, fis);
            
            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return (success) ? "http://richardluong.se/smart_mood/" + filename : "";
    }

}