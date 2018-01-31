package application.views.client;

import java.io.File;
import java.net.URL;

import java.util.ResourceBundle;

import application.models.films.Film;
import application.MainApplication;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * This is controller class that controls the MediaPlayer.fxml window. It loads
 * and plays the video file into a JavaFX mediaplayer.
 * 
 * @author David
 *
 */
public class MediaPlayerController implements Initializable {

	/***************************************************************************************
	 * This class is based on the following sources: 
	 * 
	 * Title: JavaFx Tutorial For Beginners 31 - Creating Media Player in JavaFX
	 * Author: ProgrammingKnowledge 
	 * Date: 20/01/2016 
	 * Availability: https://www.youtube.com/watch?v=sjiS4mhb0gQ
	 * 
	 * Title: Incorporating Media Assets Into JavaFX Applications - Controlling Media Playback
	 * Author: Cindy Castillo - Oracle corp.
	 * Date: April 2013
	 * Availability: https://docs.oracle.com/javafx/2/media/playercontrol.htm
	 * 
	 * 
	 *
	 ***************************************************************************************/
	@FXML
	private Stage stage;
	@FXML
	private Duration duration;
	@FXML
	private Slider timeSlider;
	@FXML
	private Label playTime;
	@FXML
	private Slider volumeSlider;
	@FXML
	private HBox mediaBar;
	@FXML
	private Button playButton;

	@FXML
	private MediaView mv;
	MediaPlayer mp;
	Media me;
	String path = null;

	private final boolean repeat = false;
	private boolean stopRequested = false;
	private boolean atEndOfMedia = false;

	String title = MainClientControl.getButtonId();

	/**
	 * This is the main method of the MediaPlayerController class. It is called when
	 * the MediaPlayer.fxml window is opened. It first gets the path of the media to
	 * be played. Loads the media into the JavaFX MediaPlayer.
	 * 
	 * Next the properties of the MediaPlayer and the JavaFX elements (Slider, Label, HBox, Button, etc.) are specified. 
	 * Finally the JavaFX MediaPlayer is set within the JavaFX MediaView and the resize properties of the ImageView are changed so
	 * that the ImageView is resized when the scene is resized whilst keeping the same aspect ratio. 
	 * The  autoplay property of the MediaPlayer is set to true so that the media starts playing when the window initialized.
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		returnTrailerPath();
		String path = new File(returnTrailerPath()).getAbsolutePath();
		me = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);

		mp.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				updateValues();
			}
		});

		mp.setOnPlaying(new Runnable() {
			public void run() {
				if (stopRequested) {
					mp.pause();
					stopRequested = false;
				} else {
					playButton.setText("||");
				}
			}
		});

		mp.setOnPaused(new Runnable() {
			public void run() {
				playButton.setText(">");
			}
		});

		mp.setOnReady(new Runnable() {
			public void run() {
				duration = mp.getMedia().getDuration();
				updateValues();
			}
		});

		mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
		mp.setOnEndOfMedia(new Runnable() {
			public void run() {
				if (!repeat) {
					playButton.setText(">");
					stopRequested = true;
					atEndOfMedia = true;
				}
			}
		});

		HBox.setHgrow(timeSlider, Priority.ALWAYS);
		timeSlider.setMinWidth(50);
		timeSlider.setMaxWidth(Double.MAX_VALUE);
		timeSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (timeSlider.isValueChanging()) {
					// multiply duration by percentage calculated by slider position
					mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
				}
			}
		});

		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (volumeSlider.isValueChanging()) {
					mp.setVolume(volumeSlider.getValue() / 100.0);
				}
			}
		});

		mv.setMediaPlayer(mp);
		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();
		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
		
		mp.setAutoPlay(true);
	}

	/**
	 * This method is called when the "(play)" button  is clicked.
	 * First the status of the MediaPlayer is checked, if the status is UNKNOWN or HALTED then nothing should be done. If however the 
	 * status is either PAUSED or READY or STOPPED then the MediaPlayer plays the media.
	 * 
	 * @param event
	 */
	public void Play(ActionEvent event) {

		Status status = mp.getStatus();

		if (status == Status.UNKNOWN || status == Status.HALTED) {
			// don't do anything in these states
			return;
		}

		if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
			// rewind the movie if we're sitting at the end
			if (atEndOfMedia) {
				mp.seek(mp.getStartTime());
				atEndOfMedia = false;
			}
			mp.play();
		} else {
			mp.pause();
		}

	}

	/**
	 * This method pauses the media.
	 * 
	 * @param event
	 */
	public void Pause(ActionEvent event) {
		mp.pause();

	}

	/**
	 * This method rewinds the media to the start.
	 * 
	 * @param event
	 */
	public void backToStart(ActionEvent event) {
		mp.seek(mp.getStartTime());

	}

	/**
	 * This method rewinds the media to the start and stops it.
	 * 
	 * @param event
	 */
	public void Stop(ActionEvent event) {
		mp.seek(mp.getStartTime());
		mp.stop();

	}

	/**
	 * This method returns the path of the media to be played. The path is saved in
	 * the Film.java class and is retrieved through the JavaFX ObservableList
	 * FilmData by using it's getter method 'getFilmData()'
	 * 
	 * @return String
	 */
	public String returnTrailerPath() {
		
		for (Film film : MainApplication.getFilmData()) {
			
			
			if (film.getName().equals(MainClientControl.getButtonId())) {
				System.out.println("found a movie match");
				path = film.gettrailerPath();
				System.out.println(path);
				break;
			}
		}
		
		return path;
	}

	
	/**
	 * This method updates the slider values so that the volume and media positions can be calculated. 
	 * It also asigns the text to the time label that displays the running time of the video.
	 */
	protected void updateValues() {
		if (playTime != null && timeSlider != null && volumeSlider != null) {
			Platform.runLater(new Runnable() {
				public void run() {
					Duration currentTime = mp.getCurrentTime();
					playTime.setText(formatTime(currentTime, duration));
					timeSlider.setDisable(duration.isUnknown());
					if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO)
							&& !timeSlider.isValueChanging()) {
						timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
					}
					if (!volumeSlider.isValueChanging()) {
						volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
					}
				}
			});
		}
	}

	/**
	 * This method formats the time values of the media so that it is in the hh:mm:sss format. 
	 * This is used to display the time in the media player window. 
	 * 
	 * @param elapsed
	 * @param duration
	 * @return String
	 */
	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}
	/**
	 * This method is used to stop the video when the MediaPlayer.fxml window is closed.
	 * It is called in the MainControl.java file.
	 * @param stage
	 */
	public void giveStage(Stage stage) {
		this.stage = stage;
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent we) {
				mp.stop();
			}
		});
		}
	
	

}