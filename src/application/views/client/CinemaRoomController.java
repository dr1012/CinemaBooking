package application.views.client;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.models.films.Booking;
import application.models.films.Film;
import application.models.films.Seance;
import application.MainApplication;
import application.views.client.MainControl;
import application.views.plan.util.Fader;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;
import javafx.util.Duration;

/***************************************************************************************
 * This class is based on the following source: 
 * 
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 * 
 ***************************************************************************************/


/**
 * This class is the controller for the CinemaRoom.fxml file. It controls the
 * viewing booking functionality and the graphical representation of the cinema
 * room. Cinema room seats are JavaFX rectangles with mous-click event
 * listeners.
 * 
 * @author David Rudolf
 * 
 *
 */
public class CinemaRoomController {
	
	
	MainApplication main;
	
	@FXML
	private Button backButton;

	@FXML
	private Rectangle a1;

	@FXML
	private Rectangle a2;

	@FXML
	private Rectangle a3;

	@FXML
	private Rectangle a4;

	@FXML
	private Rectangle a5;

	@FXML
	private Rectangle a6;

	@FXML
	private Rectangle b1;

	@FXML
	private Rectangle b2;

	@FXML
	private Rectangle b3;

	@FXML
	private Rectangle b4;

	@FXML
	private Rectangle b5;

	@FXML
	private Rectangle b6;

	@FXML
	private Rectangle c1;

	@FXML
	private Rectangle c2;

	@FXML
	private Rectangle c3;

	@FXML
	private Rectangle c4;

	@FXML
	private Rectangle c5;

	@FXML
	private Rectangle c6;

	@FXML
	private Rectangle d1;

	@FXML
	private Rectangle d2;

	@FXML
	private Rectangle d3;

	@FXML
	private Rectangle d4;

	@FXML
	private Rectangle d5;

	@FXML
	private Rectangle d6;

	@FXML
	private Rectangle e1;

	@FXML
	private Rectangle e2;

	@FXML
	private Rectangle e3;

	@FXML
	private Rectangle e4;

	@FXML
	private Rectangle e5;

	@FXML
	private Rectangle e6;

	@FXML
	private Rectangle f1;

	@FXML
	private Rectangle f2;

	@FXML
	private Rectangle f3;

	@FXML
	private Rectangle f4;

	@FXML
	private Rectangle f5;

	@FXML
	private Rectangle f6;

	@FXML
	private DatePicker datePicker;

	@FXML
	private ComboBox choiceBox;

	@FXML
	private Label seatLabel;

	@FXML
	private Label priceLabel;

	/**
	 * This JavaFX ObsevableList is used to hold the viewings (seance) of the
	 * selected movie that are in the future.
	 */
	private ObservableList<Seance> filteredSeanceData = FXCollections.observableArrayList();

	/**
	 * This JavaFx ObservableList is used to hold the bookings that are at the
	 * selected date and time (selected through the JavaFX DatePicker and ComboBox).
	 */
	private ObservableList<Booking> filteredBookingData = FXCollections.observableArrayList();

	/**
	 * This ArrayList is used to contain all the strings that label the seats in the
	 * cinema room in the right order. Starting with a1, a2, a3, etc.; The string
	 * that labels the seat contains one letter (a to f) and one integer (1-6).
	 */
	ArrayList<String> seatList = new ArrayList<String>();

	int ticketPrice;
	/**
	 * This Java Map is used to map the different JavaFX rectangles that represent
	 * the seats to their label (in form of a String).
	 */
	Map<String, Rectangle> hashtable = new HashMap<String, Rectangle>();

	int totalPrice;

	/**
	 * This integer array will register how many times has a certain JavaFX
	 * rectangle has been clicked. The array has a length of 36, representing the 36
	 * seats in the cinema room.
	 */
	private static int[] clickCounts = new int[36];

	/**
	 * This ArrayList will contain all the seats that have been selected by the
	 * user. This will then be used to book the selected seats and to set the text
	 * for the price and seat JavaFX labels.
	 */
	private static ArrayList<String> seatSelectionArray = new ArrayList<String>();

	/**
	 * This Boolean is used to single the program that there is a booking on the
	 * selected date/time and the cinema seats can be booked. If the value of this
	 * Boolean remains false then the seats will not be clickable.
	 */
	Boolean bookingStatus;

	/**
	 * This ArrayList contains the seats from all the filtered bookings so that the
	 * colour of the rectangles representing booked seats will change to red on
	 * initialization.
	 */
	private ArrayList<String> seat = new ArrayList<String>();

	String movieTitle;
	String seatString;

	/**
	 * This is the main method for the CinemaRoom.fxml file. It is called after the
	 * constructor, when the CinemaRoom window is launched. First, the Java map is
	 * populated. Each JavaFX rectangle is linked to a 2-character string that will
	 * act as therectangle's label. The Boolean "BookingStatus" is by default set to
	 * False. The movie title of the movie that was selected from the
	 * Main_Client.fxml window is identified. Then the BookingData, SeanceData and
	 * FilmData are loaded from the XML files. The fileteredSeanceData JavaFX
	 * ObservableList is populated with seances (viewings) that only happen at a
	 * later date/time. This is achieved by calling the setFilteredSeanceData()
	 * method. The seatList ArrayList is populated with the string labels from the
	 * Java map. Finally, the ticketPrice of the selected movie is stored in the
	 * ticketPrice arrtibute.
	 */
	@FXML
	void initialize() {
		
		/***************************************************************************************
		 * Title: Java - The Hashtable Class
		 * Author: Tutorials Point 
		 * Availability: https://www.tutorialspoint.com/java/java_hashtable_class.htm
		 *
		 ***************************************************************************************/
		
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
		
		movieTitle = MainClientControl.getButtonId();

		File file1 = new File("Bookings.xml");
		File file2 = new File("SeanceData.xml");
		File file3 = new File("FilmData.xml");

		MainApplication.loadBookingDataFromFile(file1);
		MainApplication.loadSeanceDataFromFile(file2);
		MainApplication.loadFilmDataFromFile(file3);

		setFilteredSeanceData();
		

		for (String key : hashtable.keySet()) {
			seatList.add(key);
		}

		for (Film film : MainApplication.getFilmData()) {
			if (film.getName().equals(movieTitle)) {
				ticketPrice = film.getTicketPrice();
			}
		}
		
		if(MainClientControl.getDatePickerInput() != null) {
			datePicker.setValue(MainClientControl.getDatePickerInput());
			
			setChoiceBox(datePicker.getValue());
		}
		else {
		
		bookingStatus = Boolean.FALSE;

		}
	}

	/**
	 * This method governs the behaviour of the program when a JavaFX rectangle
	 * (that represents a cinema room seat) is clicked. First, the method checks the
	 * Boolean bookingStatus. If it is set to false, this indicates that either a
	 * date and time haven't been selected or there are not viewings on the selected
	 * date. In this case nothing will happen when the seats are clicked and an
	 * alert window is shown. If the Boolean is true, then the program will proceed
	 * into checking which rectangle was clicked (through the 'getSource()' method).
	 * The seat label is reconstructed from getSource() method and is registered in
	 * the 'seat' String. Since this just be equal to one of the seat labels in the
	 * seatList ArrayList, it's index within the ArraList is found. This index
	 * should be identical to the index of the same seat in the clickCounts integer
	 * array and therefore the specific element of the clickCounts array is
	 * incremented by 1.
	 * 
	 * The first if() statement checks if the color of the clicked rectangle is red.
	 * If it is red, this means the seat was already booked for this viewing and
	 * therefore cannot be booked again. The program prevents any changes to be made
	 * to that specific seat. If the clicked seat is not red, the program checks how
	 * many times this rectangle was clicked previously by analysing the clickCounts
	 * array at the correct index. If the seat was clicked an even number of times
	 * it means that it is not selected. If it has been clicked an odd number of
	 * times it means that it has been selected. If the number of times it has been
	 * clicked is odd then the color of the selected seat will turn to yellow, as a
	 * visual label for 'selected', and the seat label will be added to the
	 * SelectionArray ArrayList which keeps a register of all the selected seats.
	 * The label (name) of this seat is the printed out out to the JavaFX label. If
	 * the number of times it has been clicked is even, the color of the selected
	 * seat will turn green, indicating that is has been deselected. The seat label
	 * will be removed from the SelectionArray.
	 * 
	 * @param event
	 * @throws Exception
	 */

	@FXML
	public void ClickedLabel(MouseEvent event) throws Exception {
		if (bookingStatus == Boolean.TRUE) {

			char char1 = event.getSource().toString().charAt(13);
			char char2 = event.getSource().toString().charAt(14);

			String seat = Character.toString(char1) + Character.toString(char2);
			int index = seatList.indexOf(seat);

			clickCounts[index]++;

			if (!(hashtable.get(seat).getFill().equals(Color.RED))) {
				if (clickCounts[index] % 2 != 0) {
					hashtable.get(seat).setFill(Color.YELLOW);
					seatSelectionArray.add(seat);
					/***************************************************************************************
					 * Title: Print array without brackets and commas
					 * 
					 * Author: Steven Vascellaro Date: 06/03/2017 Availability:
					 * https://stackoverflow.com/questions/4389480/print-array-without-brackets-and-commas
					 *
					 ***************************************************************************************/
					String outputString = seatSelectionArray.toString().replace(",", "") // remove the commas
							.replace("[", "") // remove the right bracket
							.replace("]", "") // remove the left bracket
							.trim();

					seatLabel.setText(outputString);
					seatString = outputString;
					totalPrice = seatSelectionArray.size() * ticketPrice;
					priceLabel.setText("  £" + Integer.toString(totalPrice));

				} else if (clickCounts[index] % 2 == 0 && clickCounts[index] > 0) {
					hashtable.get(seat).setFill(Color.GREEN);
					seatSelectionArray.remove(seat);

					/***************************************************************************************
					 * Title: Print array without brackets and commas
					 * 
					 * Author: Steven Vascellaro Date: 06/03/2017 Availability:
					 * https://stackoverflow.com/questions/4389480/print-array-without-brackets-and-commas
					 *
					 ***************************************************************************************/

					String outputString = seatSelectionArray.toString().replace(",", "") // remove the commas
							.replace("[", "") // remove the right bracket
							.replace("]", "") // remove the left bracket
							.trim();

					seatLabel.setText(outputString);
					seatString = outputString;
					totalPrice = seatSelectionArray.size() * ticketPrice;
					priceLabel.setText(" £" + Integer.toString(totalPrice));
				} else {
					hashtable.get(seat).setFill(Color.GREEN);
				}

			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("No date and/or time selected");

			alert.showAndWait();
		}
	}
	
	

	
	/**
	 * This method populates the filteredSeanceData JavaFX ObservableList so that it cointains only seances from the selected movie and at a later date and time than now.
	 */
	public void setFilteredSeanceData() {
		for (Seance seance : MainApplication.getSeanceData()) {
			if (seance.getFilm().equals(movieTitle)
					&& (seance.getDay().isAfter(LocalDate.now()) || (seance.getDay().isEqual(LocalDate.now())
							&& Integer.parseInt(seance.getTime().substring(0, 2)) > LocalTime.now().getHour()))) {
				filteredSeanceData.add(seance);
			}
		}
	}

	
	/**
	 * This method populates the JavaFX ComboBox with the times of the viewings for the selected date (selected through the DatePicker).
	 * The parameter is a LocalDate variable, when this method will be used this will be the input from the DatePicker.
	 * If the selected date doesn't contain any viewings then an alert window will appear indicating this. 
	 * @param  date
	 */
	public void setChoiceBox(LocalDate date) {
		ObservableList<String> viewingTimes = FXCollections.observableArrayList();

		for (Seance seance : filteredSeanceData) {
			if (seance.getDay().equals(date)) {
				viewingTimes.add(seance.getTime());
			}
		}
		if (viewingTimes.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("No viewings for" + movieTitle + "on this day");

			alert.showAndWait();
		} else {
			
			choiceBox.setItems(viewingTimes);
			
		}
	}

	// Populating filteredBookingData so that it contains only bookings from that
	// movie, date and time.
	
	/**
	 * This method populates the fileteredBookingData JavaFX ObservableList only with bookings that match the selected date and time from the
	 * DatePicker and ComboBox.
	 * note: here the Integer.parseInt() method is used because viewing times are saved in Seace.java as a string but Booking.java they are saved as an integer.
	 * This is allows for a nicer rendering when the times are displayed in the ComboBox (for example: for 11 o'clock it shows 11:00 [a string] rather than just 11 [an int]).
	 */
	public void setFilteredBookingData() {
		filteredBookingData.clear();
		int time = Integer.parseInt(choiceBox.getValue().toString().substring(0, 2));
		for (Booking booking : MainApplication.getBookingData()) {

			if (booking.getTitle().equals(movieTitle) && booking.getScreeningDate().equals(datePicker.getValue())
					&& booking.getScreeningTime() == time) {

				filteredBookingData.add(booking);
			}
		}

	}

	/**
	 * This method controls the initial color rendering of the rectangles(seats) at Initialization according to the Booking data. So that any seat that
	 * has been previously booked for the selected viewing date/time will have a red color and non-booked seats will have a green color. 
	 * Since this method is only used after checking that there is a viewing for the selected date and time, the Boolean bookingStatus is changed to 'true'
	 * in this method, this signals that changes can been made to the seat booking.
	 */
	public void updateSeats() {

		bookingStatus = Boolean.TRUE;
		for (Booking booking : filteredBookingData) {

			seat.add(booking.getSeat());

		}

		for (int i = 0; i < seat.size(); i++) {

			if (hashtable.containsKey((seat.get(i)))) {

				hashtable.get(seat.get(i)).setFill(Color.RED);

			} else {
				hashtable.get(seat.get(i)).setFill(Color.GREEN);
			}
		}

	}

	/**
	 * This method is called through an action event of the JavaFX DatePicker. When a date is selected, the JavaFX ComboBox displays the times of the film
	 * viewings for the selected date.
	 * @param  event
	 * @throws Exception
	 */
	public void selectedDate(ActionEvent event) throws Exception {
		setChoiceBox(datePicker.getValue());

	}

	/**
	 * This method is called through an action event of the JavaFX ComboBox. It updates the FilteredBookingData JavaFX ObservableList to contain only bookings
	 * of the selected date and time. 
	 * It then calls the updateSeats() method that updates the seats so that the ones that have been booked turn red and the other remain green. 
	 * @param  event
	 * @throws Exception
	 */
	public void comboAction(ActionEvent event) throws Exception {

		setFilteredBookingData();
		updateSeats();

	}
	
	/**
	 * This method is triggered when the 'Book' button is pressed. 
	 * It creates a new Booking element for the movie, date, time and seat. Since booking element has only one seat, it will create a booking element for  every seat that has been selected.
	 * The bookings are saved to the XML file using the saveBookingDataToFile(file) method from MainApplication.java. 
	 * The visual representation of the booked seats (the JavaFX rectangles) change their color to red. This makes them non-editable in this window. 
	 * An alert window indicates to the user that the his/her bookings have been saved correctly. 
	 * 
	 * @param  event
	 * 
	 */

	public void Book(ActionEvent event) {
		if(!seatSelectionArray.isEmpty()) {
		try {
			for (String seat : seatSelectionArray) {
				Booking booking = new Booking(MainControl.getCurrentUsername(), movieTitle, datePicker.getValue(),
						Integer.parseInt(choiceBox.getValue().toString().substring(0, 2)), LocalDate.now(), seat);
				MainApplication.getBookingData().add(booking);
				hashtable.get(seat).setFill(Color.RED);
			}

			File file = new File("Bookings.xml");
			MainApplication.saveBookingDataToFile(file);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Seats booked");
			alert.setHeaderText(null);
			alert.setContentText("You have booked the following seats:  " + seatString + "  For the viewing of  "+movieTitle+"  on the  " +datePicker.getValue()+ "  at  " +choiceBox.getValue().toString() );
			alert.showAndWait();
			
			
			try {
				
				FadeTransition fadeTrans = new FadeTransition();
				fadeTrans.setDuration(Duration.millis(1000));
				fadeTrans.setNode(choiceBox.getParent());
				fadeTrans.setFromValue(1);
				fadeTrans.setToValue(0);

				fadeTrans.setOnFinished(e -> {
					Stage stage;
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainApplication.class.getResource("views/client/Main_Client.fxml"));
					AnchorPane mainClient;
					
					try {
						mainClient = (AnchorPane) loader.load();
						mainClient.setOpacity(0);
						Scene scene = new Scene(mainClient);
						MainClientControl controller = loader.getController();
						controller.setMain(this.main);
						
						stage = (Stage) choiceBox.getScene().getWindow();
					
						stage.setScene(scene);
						stage.show();
						Fader.fadeIn(mainClient);
						
						
						
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					
					
				});

				fadeTrans.play();
				
				
				
			}
			catch(Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("No seats selected");
			alert.setHeaderText(null);
			alert.setContentText("You need to select at least one seat");
			alert.showAndWait();
		}
	}

	/**
	 * This method is triggered when the 'Back' button is pressed. 
	 * It closes the CinemaRoom.fxml window and opens the Main_Client.fxml window. 
	 * It is merely a means of navigation.
	 * 
	 * @param  event
	 * @throws Exception
	 */
	@FXML
	public void Back(ActionEvent event) throws Exception {
		Stage stage;

		stage = (Stage) backButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("Main_Client.fxml"));
		
		
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("views/client/Main_Client.fxml"));
        
        root = loader.load();
		Scene scene = new Scene(root);
		
		
		MainClientControl controller = loader.getController();
        controller.setMain(this.main);
		
        
		stage.setScene(scene);
		stage.show();
		
		
		
		
		
	}
	
	
	public void setMain(MainApplication main) {
		this.main = main;
		
	}

}
