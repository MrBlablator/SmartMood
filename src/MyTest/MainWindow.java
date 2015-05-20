package MyTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import edu.ufl.digitalworlds.j4k.J4KSDK;



public class MainWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	KinectVideoManager kinect;
	
	public MainWindow() {


        initKinect();
        initUI();
    }

    private void initKinect() {

    	try {
			kinect = new KinectVideoManager();
		
			
			kinect.setServerUrl("https://rctest.ngrok.io");
			
			
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void initUI() {
    	
    	JPanel basic = new JPanel();
    	basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
    	add(basic);
    	
    	JPanel infoPanel = new JPanel(new BorderLayout(5, 5));
    	infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
    	infoPanel.setMinimumSize(new Dimension(450,300));
    	
    	
    	JTextArea pane = new JTextArea();
    	String text = "Click on \"Start Smile Detection\" to start\n Results will be stored in the binary folder.";
    	pane.setText(text);
        pane.setEditable(false);
       
        kinect.setInfoDisplay(pane);
        JScrollPane scroll = new JScrollPane(pane);
    	infoPanel.add(scroll);
    	
    	basic.add(infoPanel);
    	
    	
    	 JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

         JButton start = new JButton("Start Smile Detection");
         start.setMnemonic(KeyEvent.VK_S);
         JButton stop = new JButton("Stop Smile Detection");
         stop.setMnemonic(KeyEvent.VK_O);
         JButton close = new JButton("Close");
         close.setMnemonic(KeyEvent.VK_C);

         bottom.add(start);
         bottom.add(stop);
         bottom.add(close);
         basic.add(bottom);
    	
         close.addActionListener(new ActionListener() {
        	    @Override
        	    public void actionPerformed(ActionEvent event) {
        	    	kinect.stop();
        	    	System.exit(0);
        	    }
        	});
         
         start.addActionListener(new ActionListener() {
        	    @Override
        	    public void actionPerformed(ActionEvent event) {
        	      
        	       pane.setText("");
        	       kinect.start(J4KSDK.COLOR);
        	    }
        	});
         
         stop.addActionListener(new ActionListener() {
     	    @Override
     	    public void actionPerformed(ActionEvent event) {
     	       kinect.stop();
     	     
     	    }
     	});
    	
    
        setTitle("Smile Detection Window");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
   

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run() {
            	MainWindow window = new MainWindow();
                window.setVisible(true);
            }
        });
    }
}
