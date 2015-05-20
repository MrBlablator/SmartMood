package MyTest;

import javax.swing.JFrame;
import java.awt.EventQueue;


public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainWindow() {

        initUI();
    }

    private void initUI() {
        
        setTitle("Smile Detection Window");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
