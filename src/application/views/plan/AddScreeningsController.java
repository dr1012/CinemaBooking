package application.views.plan;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.MainApplication;
import application.models.films.Film;
import application.models.films.Seance;
import application.views.plan.util.MultiSelect;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * 
 * The controller involved in the Add screenings scene.
 * 
 * @author Joseph Courtley
 *
 */
public class AddScreeningsController implements Initializable {

	private MainApplication main;
	private ArrayList<String> bookedTimes;
	private ObservableList<String> list;
	private Stage stage;
	
//	@FXML private ComboBox<String> films;
	@FXML private ComboBox<Film> films;
	@FXML private ListView<String> times;
	
	
	
	@FXML private Label statusLbl;
	@FXML private Label fNameAlert;
	@FXML private Label dateAlert;
	@FXML private Label timesAlert;
	
	@FXML private DatePicker dates;
	@FXML private Button saveBtn;
	@FXML private ImageView pic;
	
	
	@FXML
	private void exitAddScreenings() {
		stage.close();
	}
	
	public void setTheStage(Stage stage) {
		this.stage = stage;
	}
	
	
	
	/**
	 * Method checks if all fields are filled in, and updates statusLbl prompting the user to fill in any that are missing.
	 * 
	 * Afterwards, adds screenings to the main application's seanceData observable List, and updates the bookedTimes ArrayList and calls
	 * refresh on the ListView of screening times to display the newly booked screenings immediately.
	 * 
	 */
	@FXML
	private void saveScreeningData() {
		
		
		boolean detailsFilled = true;
		ObservableList<String> listOfTimesPicked;
		listOfTimesPicked = times.getSelectionModel().getSelectedItems();
		String warning = "You need to fill in all fields before you can save.";
		String filmName = "";
		LocalDate datePicked = null;
		fNameAlert.getStyleClass().remove("alertLabels");
		dateAlert.getStyleClass().remove("alertLabels");
		timesAlert.getStyleClass().remove("alertLabels");
		
		if (films.getSelectionModel().isEmpty()) {
			fNameAlert.setText("!");
			fNameAlert.getStyleClass().add("alertLabels");
			detailsFilled = false;
		} else {
			filmName = films.getValue().getName();
		}
		
		if (dates.getValue() == null) {
			dateAlert.setText("!");
			dateAlert.getStyleClass().add("alertLabels");
			detailsFilled = false;
		} else {
			datePicked = dates.getValue();
		}
		
		if (listOfTimesPicked.isEmpty()) {
			timesAlert.setText("!");
			timesAlert.getStyleClass().add("alertLabels");
			detailsFilled = false;
		}
		

		if (! detailsFilled) {
			
			statusLbl.setText(warning);
			
			return;
		}
		
		
		
		
		for (String time : listOfTimesPicked) {
			
			main.getSeanceData1().add(new Seance(datePicked, time, filmName));
		}
		
		File file = new File("SeanceData.xml");
		
		main.saveData(file);
		
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle("Save successful");
        
        String pluralOrNot = "";
        if (listOfTimesPicked.size() > 1) {
        	pluralOrNot += "s";
        }
        alert.setHeaderText(String.format("Screening%s successfully saved", pluralOrNot));
        
        alert.showAndWait();
        
        times.getSelectionModel().clearSelection();
        
        for (Seance screening : main.getSeanceData1()) {
			if (screening.getDay().equals(dates.getValue())) {
			bookedTimes.add(screening.getTime());
			}
		}
		
        times.refresh();
		
	}
	
	
	/**
	 * This method is called when the user interacts with the DatePicker. It filters which times are available for screenings on the day chosen,
	 * and displays this in the ListView by calling its refresh method.
	 */
	@FXML
	private void datePicked() {
		
		bookedTimes = new ArrayList<String>();
		times.getSelectionModel().clearSelection();
		
		LocalDate date = dates.getValue();
		
		if (date == null) {
			statusLbl.setText("Choose a date to see what times are available");
		} else if (date.isBefore(LocalDate.now())) {
			statusLbl.setText("Can't pick a date before today!");
		} else {
			statusLbl.setText("");
			for (Seance screening : main.getSeanceData1()) {
				if (screening.getDay().equals(dates.getValue())) {
				bookedTimes.add(screening.getTime());
				}
			}
		}
		
		
		times.refresh();
		
		
	}
	
	
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statusLbl.setText("Choose a date to see what times are available");
	}
	
	
	
	
	
	
	
	/**
	 * This method is called when the AddScreenings FXML file is loaded.
	 * 
	 * To keep things object oriented, a reference to main is passed through to the AddScreeningsController.
	 * This can only happen after initialize() has been called, so setMain effectively does the job of initialize().
	 * 
	 * @param main
	 * @param tempStage
	 */
	
	public void setMain(MainApplication main, Stage tempStage, String filmToBeAutomaticallyLoaded) {
		
		this.main = main;
		this.stage = tempStage;
		
		
		

		
		films.setItems(main.getFilmData1());
		films.setCellFactory((comboBox) -> {
			return new ListCell<Film>() {
				@Override
				protected void updateItem(Film item, boolean empty) {
					super.updateItem(item, empty);
					
					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getName());
					}
						
				}
			};
		});
		
		
		
		
		
		
		films.setConverter(new StringConverter<Film>() {
			@Override
			public String toString(Film film) {
				if (film == null) {
					return null;
				} else {
					return film.getName();
				}
			}
			
			@Override
			public Film fromString(String filmString) {
				return null;
			}
		});
		
		films.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	String path = newSelection.getPath();
		    	File file = new File(path);
		    	Image image = new Image(file.toURI().toString());
		        pic.setImage(image);
		        pic.setPreserveRatio(true);
		        pic.setFitHeight(144);
		        
		    }
		});
		
		if (!filmToBeAutomaticallyLoaded.equals("")) {
			for (Film film : main.getFilmData1()) {
				if (film.getName().equals(filmToBeAutomaticallyLoaded)) {
					films.getSelectionModel().select(film);
					
				}
			}
		}

		
		list = FXCollections.observableArrayList();
		for (int i = 9; i < 20; i++) {
			if (i < 10) list.add("0" + Integer.toString(i) + ":00");
			else list.add(Integer.toString(i) + ":00");
		}
		
		
		times.setItems(list);
		
		times.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		times.addEventFilter( MouseEvent.MOUSE_PRESSED, MultiSelect.multiSelectEventHandler());
		times.addEventFilter( MouseEvent.MOUSE_RELEASED, MultiSelect.multiSelectEventHandler());
		
		times.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override 
			public ListCell<String> call(ListView<String> param){
				return new ListCell<String>() {
					@Override 
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						
						LocalDate date = dates.getValue();
						setStyle(null);
						this.getStyleClass().remove("crossedOut");

						if (item == null || empty) {
							setText(null);
							setGraphic(null);
						} else {
							setText(item);
							setDisable(false); //this was causing the problem. It needs to be reset
							setStyle("-fx-background-color: #b1c0d8");

							if (date == null) {
								setDisable(true);
							} else if (date.isBefore(LocalDate.now()) || (date.isEqual(LocalDate.now()) && item.compareTo(java.time.LocalTime.now().toString()) < 0)) {
								setDisable(true);
								setStyle("-fx-background-color: #f5d6ff; -fx-strikethrough: true;");
								this.getStyleClass().add("crossedOut");
							} else {
								
								
								setStyle("-fx-background-color: #e0ffdd");
								
								for (String time : bookedTimes) {
									if (time.equals(item)) {
										setText(item + " - already booked");
										setDisable(true);
										setStyle("-fx-background-color: #c46a5e");
										return;
									}
								}
							}
						}
					}
				};
			}
		});
		
		
		
		
		
		
		
		
		
		
	}	

	
	
	
	
	
	
	
	
	
	
	
	
	
}
