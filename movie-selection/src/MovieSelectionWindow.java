import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class MovieSelectionWindow extends JFrame{
	
	JComboBox combobox;
	JButton playButton, stopButton, pauseButton;
	JLabel movieTitle;
	
	String[] movies;
	
	public MovieSelectionWindow(String[] movies) {
		this.movies = movies;
		setupWindow();
	}
	
	private void setupWindow() {
		
		setSize(300,300);
		setTitle("SmartMood");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Select a movie: "));
		combobox = new MovieSelectionComboBox(movies);
		panel.add(combobox);
		add(panel, BorderLayout.NORTH);
		
		panel = new JPanel();
		movieTitle = new JLabel(movies[combobox.getSelectedIndex()]);
		panel.add(movieTitle);
		add(panel);
		
		panel = new JPanel();
		playButton = new JButton("Play");
		panel.add(playButton);
		
		stopButton = new JButton("Stop");
		panel.add(stopButton);
		
		pauseButton = new JButton("Pause");
		panel.add(pauseButton);
		add(panel, BorderLayout.SOUTH);
		
		setVisible(true);
		
	}

	
	
	
	
}

