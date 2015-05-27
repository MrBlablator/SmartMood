import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MovieSelection implements ActionListener{

	MovieSelectionWindow window;
	String[] movies;
	
	public MovieSelection() {
		String[] movies2 = {"movie1.mp4", "movie2.mp4", "movie3.mp4", "movie4.mp4"};
		movies = movies2;
		window = new MovieSelectionWindow(movies);
		window.combobox.addActionListener(this);
		window.playButton.addActionListener(this);
		window.stopButton.addActionListener(this);
		window.pauseButton.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new MovieSelection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println(e);
		
		if (e.getActionCommand().equals("comboBoxChanged")) {
			window.movieTitle.setText(movies[window.combobox.getSelectedIndex()]);
		} else if (e.getActionCommand().equals("Play")) {
			
		} else if (e.getActionCommand().equals("Stop")) {
			
		} else if (e.getActionCommand().equals("Pause")) {
			
		}
		
	}
	
}
