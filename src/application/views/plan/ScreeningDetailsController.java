package application.views.plan;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import application.MainApplication;
import application.models.films.Booking;
import application.models.films.Film;
import application.models.films.Seance;
import application.util.DateConversion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Controller used to display information to the employee pertaining to a particular screening.
 * 
 * Depends on which screening had been picked in the main Employee Table view.
 * 
 * @author Joseph Courtley
 *
 */
public class ScreeningDetailsController implements Initializable {

	private MainApplication main;
	private Stage stage;

	@FXML private Rectangle a1; @FXML private Rectangle a2; @FXML private Rectangle a3; @FXML private Rectangle a4; @FXML private Rectangle a5; @FXML private Rectangle a6;

	@FXML private Rectangle b1; @FXML private Rectangle b2; @FXML private Rectangle b3; @FXML private Rectangle b4; @FXML private Rectangle b5; @FXML private Rectangle b6;

	@FXML private Rectangle c1; @FXML private Rectangle c2; @FXML private Rectangle c3; @FXML private Rectangle c4; @FXML private Rectangle c5; @FXML private Rectangle c6; 
	
	@FXML private Rectangle d1; @FXML private Rectangle d2; @FXML private Rectangle d3; @FXML private Rectangle d4; @FXML private Rectangle d5; @FXML private Rectangle d6; 
	
	@FXML private Rectangle e1; @FXML private Rectangle e2; @FXML private Rectangle e3; @FXML private Rectangle e4; @FXML private Rectangle e5; @FXML private Rectangle e6;

	@FXML private Rectangle f1; @FXML private Rectangle f2; @FXML private Rectangle f3; @FXML private Rectangle f4; @FXML private Rectangle f5; @FXML private Rectangle f6;
	
	@FXML private Button backButton;
	@FXML private DatePicker datePicker; 
	@FXML private ComboBox choiceBox; 
	@FXML private Label numBookedSeats; 
	@FXML private Label numRemainingSeats; 
	@FXML private Label BookingsTotalCost;
	@FXML private Label fName; 
	@FXML private Label fDate; 
	@FXML private Label fTime;
	@FXML private ImageView filmPic;
	
	@FXML private Button test;

    private ObservableList<Seance> filteredSeanceData =  FXCollections.observableArrayList();
	private ObservableList<Booking> filteredBookingData = FXCollections.observableArrayList();
	
	private ArrayList<String> seatList = new ArrayList<String>();
	private int ticketPrice;
	private int totalPrice;
	
	
	private Map<String,Rectangle> hashtable = new HashMap<String,Rectangle>();
	private ArrayList<String> seat = new ArrayList<String>();
	
	private String movieTitle;
	private String imgPath;
	private LocalDate screeningDay;
	private String time;
	
	
	@FXML
	private void exitScreeningDetail() {
		stage.close();
	}
	
	/**
	 * Displays which seats are booked for the screening.
	 * 
	 */
	public void updateSeats() {

		for(Booking booking :filteredBookingData) {

			seat.add(booking.getSeat());

		}
		
		numBookedSeats.setText(Integer.toString(seat.size()));
		numRemainingSeats.setText(Integer.toString(36 - seat.size()));
		BookingsTotalCost.setText("£" + Integer.toString(seat.size() * ticketPrice));

		for(int i=0; i<seat.size(); i++) {

				if(hashtable.containsKey((seat.get(i)))) {

				hashtable.get(seat.get(i)).setFill(Color.RED);

				}
				else{
					hashtable.get(seat.get(i)).setFill(Color.GREEN);
				}
				}

	}
	
	
	
	
	
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	/**
	 * Sets the relevant filtered screening data.
	 */
	public void setFilteredSeanceData() {
		for(Seance seance: main.getSeanceData1()) {
			if(seance.getFilm().equals(movieTitle) &&  (  seance.getDay().isAfter(LocalDate.now()) || (seance.getDay().isEqual(LocalDate.now()) && seance.getTime().compareTo(java.time.LocalTime.now().toString()) < 0)      ))  {
				filteredSeanceData.add(seance);
			}
		}
		}
	
	/**
	 * Sets the relevant filtered booking data.
	 */
	public void setFilteredBookingData() {
		filteredBookingData.clear();
		
	    for(Booking booking: main.getBookingData1()) {

	    	if(booking.getTitle().equals(movieTitle) && booking.getScreeningDate().equals(screeningDay) && ((Integer.toString(booking.getScreeningTime()) + ":00").equals(time))) {
	    		
	    		filteredBookingData.add(booking);
	    	}
	}

	}
	
	/**
	 * Initialises the data involved in the controller. Sets up the hashtable which will be used to determine 
	 * which seats to colour red (if they are booked).
	 * 
	 * @param main
	 * @param seance
	 */
	public void setMain(MainApplication main, Seance seance) {
		this.main = main;
		
		
		hashtable.put("a1", a1);
		hashtable.put("a2", a2);
		hashtable.put("a3", a3);
		hashtable.put("a4", a4);
		hashtable.put("a5", a5);
		hashtable.put("a6", a6);
		hashtable.put("b1", b1);
		hashtable.put("b2", b2);
		hashtable.put("b3", b3);
		hashtable.put("b4", b4);
		hashtable.put("b5", b5);
		hashtable.put("b6", b6);
		hashtable.put("c1", c1);
		hashtable.put("c2", c2);
		hashtable.put("c3", c3);
		hashtable.put("c4", c4);
		hashtable.put("c5", c5);
		hashtable.put("c6", c6);
		hashtable.put("d1", d1);
		hashtable.put("d2", d2);
		hashtable.put("d3", d3);
		hashtable.put("d4", d4);
		hashtable.put("d5", d5);
		hashtable.put("d6", d6);
		hashtable.put("e1", e1);
		hashtable.put("e2", e2);
		hashtable.put("e3", e3);
		hashtable.put("e4", e4);
		hashtable.put("e5", e5);
		hashtable.put("e6", e6);
		hashtable.put("f1", f1);
		hashtable.put("f2", f2);
		hashtable.put("f3", f3);
		hashtable.put("f4", f4);
		hashtable.put("f5", f5);
		hashtable.put("f6", f6);
		
		
		
		movieTitle = seance.getFilm();
		screeningDay = seance.getDay();
		time = seance.getTime();
		
		for(Film film: main.getFilmData1()) {
        	if(film.getName().equals(movieTitle)) {
        		ticketPrice = film.getTicketPrice();
        		imgPath = film.getPath();
        	}
        }
		
		fName.setText(movieTitle);
		fDate.setText(DateConversion.format(screeningDay));
		fTime.setText(time);
		
		
		File file = new File(imgPath);
		Image image = new Image(file.toURI().toString());
		
		filmPic.setPreserveRatio(true);
		filmPic.setImage(image); 
		filmPic.setFitHeight(200);

		File file1 = new File("Bookings.xml");
        this.main.loadData(file1);
        
        setFilteredSeanceData();
        setFilteredBookingData();


        for (String key : hashtable.keySet()){
		seatList.add(key);
        }
        
        updateSeats();
		
	}
	
	public void setTheStage(Stage stage) {
		this.stage = stage;
	}

}
