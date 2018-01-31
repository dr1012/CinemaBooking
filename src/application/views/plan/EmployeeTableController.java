package application.views.plan;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import application.MainApplication;
import application.models.films.Film;
import application.models.films.Seance;
import application.util.DateConversion;
import application.util.PlaceHolderUtil;
import application.views.plan.util.DataSaveException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * This controller is responsible for filling up the Film table as well as the Screenings Table.
 * 
 * 
 * @author David Rudolf
 * @author Joseph Courtley
 *
 */
public class EmployeeTableController implements Initializable {
	
	
	private HashMap<String, Integer> rowComparison = new HashMap<String, Integer>();
	private MainApplication main;
	private ObjectProperty<TableRow<Film>> selectedRow = new SimpleObjectProperty<>();
	private LocalDate selectedDate;

	private ObservableList<Film> filteredFilmData;
	private ObservableList<Seance> filteredSeanceData;
	
	@FXML private TableView<Film> filmTable;

	@FXML private TableColumn<Film, String> imageColumn;
	@FXML private TableColumn<Film, String> filmNameColumn;
	@FXML private TableColumn<Film, String> filmDescripColumn;

	@FXML private DatePicker datePicker;

	@FXML private TableView<Seance> screeningsTable;
	@FXML private TableColumn<Seance, String> filmNameColumn2;
	@FXML private TableColumn<Seance, LocalDate> dateColumn;
	@FXML private TableColumn<Seance, Integer> timeColumn;
	
	@FXML private Button screeningDetail;
	@FXML private ComboBox<String> genres;
	
	@FXML private Button addScreenings;
	@FXML private Button deleteScreenings;
	@FXML private Button clearDate;
	@FXML private Button clearGenre;
	
	/**
	 * 
	 * Gets called by ManageFilmsController when saving film details or deleting them, so that 
	 * the result is displayed automatically in the film table. 
	 * 
	 */
	@FXML
	public void refreshData() {
		filteredFilmData.clear();
		
		for (Film film : main.getFilmData1()) {
			filteredFilmData.add(film);
		}
		rowComparison.clear();
		refreshTable();
		placeHolderInitialize();
	}
	
	
	public void refreshTable() {
		rowComparison.clear();
		filmTable.refresh();
		
		screeningsTable.refresh();
		placeHolderInitialize();
	}
	
	@FXML 
	private void clearDate() {
		datePicker.setValue(null);
	}
	
	@FXML
	private void clearGenre() {
		genres.valueProperty().set(null);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/**
	 * Sets up the placeholder to be used in the film tables.
	 */
	private void placeHolderInitialize() {
		
		
		File file = new File("resources/images/rodin.png");
		Image image1 = new Image(file.toURI().toString());
		ImageView imVu = new ImageView(image1);
		imVu.setPreserveRatio(true);
		imVu.setFitWidth(70);
		
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(20);
		
		vbox.setStyle("-fx-border-color: blue;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: dashed;\n");
		
		vbox.getChildren().addAll(imVu, PlaceHolderUtil.getMessage(), PlaceHolderUtil.getQuote());
		
		
		screeningsTable.setPlaceholder(vbox);
		filmTable.setPlaceholder(vbox);
	}
	
	
	
	
	
	/**
	 * This method filters the data that appears in the film table based on genres, calling dateChanged to 
	 * further filter the data if a date has been selected.
	 */
	@FXML
	private void chooseGenre() {
		
		if (genres.getValue() == null) {
			
			if (datePicker.getValue() == null) {
			filteredSeanceData.clear();
			filteredFilmData.clear();
			filmTable.getSelectionModel().select(null);
			
			for (Film film : main.getFilmData1()) {
				filteredFilmData.add(film);
			}
			refreshTable();
			} else {
				dateChanged();
			}

			return;
		} else {
			
			if (datePicker.getValue() == null) {
				
				filteredFilmData.clear();
				for (Film film : main.getFilmData1()) {
					if (film.getMainGenre() == null) {
						continue;
					}
					if (film.getMainGenre().equals(genres.getValue())) {
						filteredFilmData.add(film);
					}
				}
				
				refreshTable();
				
			} else {
				dateChanged();
			}
			
			
			
		}

		
	}
	
	
	/**
	 * This method filters the data that appears in the film table based on dates, calling chooseGenre to 
	 * further filter the data if a genre has been selected.
	 */
	@FXML
	private void dateChanged() {
			
			LocalDate dateChosen = datePicker.getValue();
			
			if (dateChosen == null) {
				
				if (genres.getValue() == null) {
				filteredSeanceData.clear();
				filteredFilmData.clear();
				filmTable.getSelectionModel().select(null);
				
				for (Film film : main.getFilmData1()) {
					filteredFilmData.add(film);
				}
				refreshTable();
				} else {
					chooseGenre();
				}

				return;
			}
			
			
			if (dateChosen.isBefore(LocalDate.now())) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("You can't pick a time before today");

				alert.showAndWait();
				return;
			}
			
			
			
			Film chosenFilm = null;
			
			if (! filmTable.getSelectionModel().isEmpty()) {
				chosenFilm = filmTable.getSelectionModel().getSelectedItem();
			}
			
			filteredSeanceData.clear();
			filteredFilmData.clear();

				ArrayList<String> filmNames = new ArrayList<String>();
				
				for (Seance seance : main.getSeanceData1()) {

					if (seance.getDay().equals(dateChosen)) {
						filteredSeanceData.add(seance);

						String filmName = seance.getFilm();
						if (!(filmNames.contains(filmName))) {

							filmNames.add(filmName);
							
						}
					}
				}
				
				String genre = genres.getValue();
				
				for (Film film : main.getFilmData1()) {
					if (filmNames.contains(film.getName())) {
						if (genre == null || genre.equals(film.getMainGenre())) {
							filteredFilmData.add(film);
						} else {
							
							for (Seance seance : filteredSeanceData) {
								if (seance.getFilm() == film.getName()) {
									filteredSeanceData.remove(seance);
								}
							}
							
						}
						
					}
				}
				
				if (chosenFilm != null) {
					filmTable.getSelectionModel().selectFirst();
					for (int i = 0; i < filmTable.getItems().size(); i++) {
				        if (filmTable.getItems().get(i).getName() == chosenFilm.getName()) {
				            filmTable.getSelectionModel().select(i);
				        }
				    }

				}
				
				refreshTable();
	}
	
	
	
	/**
	 * This method requires the user to select a screening. The employee is then presented with a screen with
	 * details relating to that particular screening.
	 */
	@FXML
	private void goToScreeningDetails() {
		
		if (screeningsTable.getSelectionModel().isEmpty()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Select a screening first.");

			alert.showAndWait();
			return;
		}
		
		Seance seance = screeningsTable.getSelectionModel().getSelectedItem();
		
		try {

			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("views/plan/ScreeningDetails.fxml"));
            BorderPane newPage = (BorderPane) loader.load();
            
            
            
            Stage tempStage = new Stage();
            tempStage.setTitle("Manage Films");
            tempStage.initModality(Modality.WINDOW_MODAL);
            tempStage.initOwner(main.getPrimaryStage());
            
             
            
            ScreeningDetailsController controller = loader.getController();
            controller.setMain(this.main, seance);
            controller.setTheStage(tempStage);
            
            Scene scene = new Scene(newPage);
            tempStage.setScene(scene);
            
            tempStage.showAndWait();
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * This method is called when the user selects a row in the screenings table.
	 * It changes the variable selectedDate, which is involved in the cell factory which renders the 
	 * Date Column. This allows the date of the screening to be highlighted even when the user clicks on 
	 * rows where the date was hidden (Only the date in the first row for each date is visible).
	 */
	private void highlightDate() {
		
//		TableColumn<Seance, LocalDate> dCol = (TableColumn<Seance, LocalDate>) screeningsTable.getColumns().get(0);
//		for (TableRow<LocalDate> row : dCol) {
//			
//		}
		
		selectedDate = screeningsTable.getSelectionModel().getSelectedItem().getDay();
		
		screeningsTable.refresh();
		
	}

	/**
	 * Takes the user to the add screenings page. If a film has been selected, it is automatically loaded into 
	 * the new scene.
	 */
	@FXML
	private void goToAddScreenings() {
		try {

			FXMLLoader loader = new FXMLLoader();

			loader.setLocation(MainApplication.class.getResource("views/plan/AddScreenings.fxml"));

			String filmToBeAutomaticallyLoaded = "";
			if (!filmTable.getSelectionModel().isEmpty()) {
				filmToBeAutomaticallyLoaded = filmTable.getSelectionModel().getSelectedItem().getName();
			}
			
			AnchorPane newPage = (AnchorPane) loader.load();

			Stage tempStage = new Stage();
			tempStage.setTitle("Add Screening");
			tempStage.initModality(Modality.WINDOW_MODAL);
			tempStage.initOwner(main.getPrimaryStage());

			AddScreeningsController controller = loader.getController();
			controller.setMain(this.main, tempStage, filmToBeAutomaticallyLoaded);

			Scene scene = new Scene(newPage);
			tempStage.setScene(scene);

			tempStage.showAndWait();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Displays information to help the employee use the add screening function.
	 */
	@FXML
	private void giveInfoAboutAdd() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText("To add screenings, click the plus button below and fill in the details of the film and date of the screening. You can choose multiple times from the list, if they are available.");

		alert.showAndWait();
	}
	
	/**
	 * In order to delete a screening, the employee must select one and click the "-" minus button.
	 * 
	 * An alert appears asking them to confirm their decision.
	 */
	@FXML
	private void deleteScreenings() {
		
		Seance screening = screeningsTable.getSelectionModel().getSelectedItem();
		
		if (screening == null) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("No screening selected");

			alert.showAndWait();
			return;
		}
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(filmTable.getScene().getWindow());
		alert.setTitle("Delete Screenings");
		alert.setHeaderText(String.format("Delete Existing Screenings?"));
		Optional<ButtonType> result = alert.showAndWait();
		try {
			if(!result.isPresent()) {
				throw new DataSaveException("You need to confirm the save");

			} else if(result.get() == ButtonType.OK) {
				
				main.getSeanceData1().remove(screening);
				File file = new File("SeanceData.xml");
				main.saveData(file);
				filteredSeanceData.remove(screening);
				
				refreshTable();
				
				
			} else if(result.get() == ButtonType.CANCEL) {
				throw new DataSaveException("Cancel selected");
			} 
		} catch (DataSaveException e) {
			e.toString();
		}
	}
	
	/**
	 * Displays information to help the employee use the delete screening function.
	 */
	@FXML
	private void giveInfoAboutDelete() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText("To delete screenings, simply highlight them and click on the minus button below.");

		alert.showAndWait();
	}
	
	/**
	 * Method called in the selection listener on the film table, populating the screenings based on the value
	 * of the filmName property of the selected film.
	 * 
	 * @param filmName
	 */
	private void populateScreenings(String filmName) {
		
		for (Seance seance : main.getSeanceData1()) {
			if (seance.getFilm().equals(filmName)) {

				if (datePicker.getValue() != null) {
					if (datePicker.getValue().equals(seance.getDay())) {
						filteredSeanceData.add(seance);
					}
				} else {
					filteredSeanceData.add(seance);
				}
			}
		}
		screeningsTable.refresh();
		
	}
	
	
	/**
	 * Overloaded version of the method which populates screenings for all films in filtered film data.
	 * 
	 */
	private void populateScreenings() {
		
		if (datePicker.getValue() != null) {
			ArrayList<String> filmNames = new ArrayList<String>();
			for (Film film : filteredFilmData) {
				filmNames.add(film.getName());
			}
			for (Seance seance : main.getSeanceData1()) {
				if (filmNames.contains(seance.getFilm())) {
					if (datePicker.getValue().equals(seance.getDay())) {
						filteredSeanceData.add(seance);
					}
				}
			}
		}
	}
	
	
	/**
	 * 
	 * To keep things object oriented and to avoid transforming everything in static data and methods, the controller needs to be given access to an object main
	 * of class "MainApplication". 
	 * 
	 * Since, in the setup of the controller, we can only call this method AFTER having created a controller object, this method
	 * can only be called after the controller has been initialized. For this reason, any setup that requires access to data has to take place in setMain, and not in Initialize.
	 * 
	 * This method therefore acts as a second Initialize method.
	 * 
	 * @param main
	 */
	public void setMain(MainApplication main) {
		this.main = main;
		
		initializeDataAndSetInTables();
		initializeGenres();
		setUpFilmTable();
		setUpScreeningsTable();
		
	}
	
	
	
	
	/**
	 * 
	 * This method sets the observable lists of films and screenings into their respective tables.
	 * It gets film data from main for the film table, but at this stage the screenings list is left empty.
	 * 
	 * However, the screenings table is given a sort order to make sure that data is displayed correctly later.
	 * 
	 */
	private void initializeDataAndSetInTables() {
		
		placeHolderInitialize();
		filteredFilmData = FXCollections.observableArrayList(main.getFilmData1());
		filmTable.setItems(filteredFilmData);
		
		filteredSeanceData = FXCollections.observableArrayList();
		dateColumn.setSortType(TableColumn.SortType.ASCENDING);
		
		SortedList<Seance> sortedScreenings = new SortedList<>(filteredSeanceData);
		sortedScreenings.comparatorProperty().bind(screeningsTable.comparatorProperty());
		screeningsTable.getSortOrder().addAll(dateColumn, timeColumn);
		
		screeningsTable.getSortOrder().addListener( (ListChangeListener<? super TableColumn<?,?>>)observable -> {
		    if( !screeningsTable.getSortOrder().contains(dateColumn) ){
		    	dateColumn.setSortType(SortType.ASCENDING);
		    	screeningsTable.getSortOrder().add(dateColumn);
		    }
		    if( !screeningsTable.getSortOrder().contains(timeColumn) ){
		    	timeColumn.setSortType(SortType.ASCENDING);
		    	screeningsTable.getSortOrder().add(timeColumn);
		    }
		});
		
		screeningsTable.setItems(sortedScreenings);
		screeningsTable.sort();
	}
	
	
	/**
	 * 
	 * The ComboBox containing the genres that can be used to filter the information in the film table
	 * is initialized with this method.
	 */
	private void initializeGenres() {
		ObservableList<String> genresOfFilm = FXCollections.observableArrayList();

		for (Film film: main.getFilmData1()) {
			
			String genre = "";
			if (film.getMainGenre() != null) {
				genre = film.getMainGenre();
			}
			
			if (!genresOfFilm.contains(genre) && !genre.equals("")) {
				genresOfFilm.add(genre);
			}
		}
		genres.setItems(genresOfFilm);
	}
	
	
	/**
	 * The cell value factories and cell factories for rendering the data of the film table are set up here.
	 * 
	 * There is a listener to allow the user to display information in the screenings table by clicking
	 * on rows in the film table.
	 * 
	 * There is also one which allows the user to deselect rows that have been selected (not part of TableView's
	 * built in functionality).
	 * 
	 */
	
	public void setUpFilmTable() {
		
		filmNameColumn.setCellValueFactory(new PropertyValueFactory<Film, String>("name"));
		filmDescripColumn.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
		imageColumn.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
		
		
		
		//setting how to render the Film Description column
		
		filmDescripColumn.setCellFactory(new Callback<TableColumn<Film, String>, TableCell<Film, String>>() {

			@Override
			public TableCell<Film, String> call(
					TableColumn<Film, String> param) {
				TableCell<Film, String> cell = new TableCell<Film, String>();
				
				if (cell.itemProperty() != null) {	
				
				Text text = new Text();
				cell.setGraphic(text);
				cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
				text.wrappingWidthProperty().bind(cell.widthProperty());
				text.textProperty().bind(cell.itemProperty());
				text.setFill(Color.web("#ffdda3"));
				} else {
				}
				
				
				
				return cell ;
			}

		});
		
		//setting the rendering of images in the table
		
		imageColumn.setCellFactory(new Callback<TableColumn<Film,String>,TableCell<Film,String>>(){        
			@Override
			public TableCell<Film,String> call(TableColumn<Film,String> param) {                
				TableCell<Film,String> cell = new TableCell<Film,String>(){
					ImageView imageview = new ImageView();
					@Override
					public void updateItem(String item, boolean empty) {                        
						if(item!=null){
							File file = new File(item);
							Image image = new Image(file.toURI().toString());

							HBox box= new HBox();
							box.setSpacing(10) ;
							imageview.setPreserveRatio(true);
							imageview.setImage(image); 
							imageview.setFitHeight(130);
//							imageview.setFitWidth(70);
							


							box.getChildren().addAll(imageview);
							setGraphic(box);
						}
					}
				};              
				return cell;
			}

		});
		
		
		// setting a listener on the selected item in the film table to update the screenings table

		filmTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			filteredSeanceData.clear();
//			screeningsTable.refresh();
			rowComparison.clear();
			
			
			if (newSelection == null) {
				screeningsTable.getSelectionModel().clearSelection();
			} else {

				String filmName = newSelection.getName();
				
				populateScreenings(filmName);
			}
			
			screeningsTable.refresh();
		});

		// deselect rows if you click on them when they are already selected

		filmTable.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
			Node source = evt.getPickResult().getIntersectedNode();
			
			
			//look for the TableRow that is the source of this mouse click event
			while (source != null && !(source instanceof TableRow)) {
				source = source.getParent();
			}
			
			// if the row is the currently selected row, deselect it
			if (source == null || (source instanceof TableRow && ((TableRow) source).equals(selectedRow.get()))) {
				
				filmTable.getSelectionModel().clearSelection();
				selectedRow = new SimpleObjectProperty<>();
				
				populateScreenings();
				
				return;
			}

			selectedRow.set((TableRow) source);

		});
		
	}
	
	int i =0;
	/**
	 * Setting up the cell value factories of the columns of the screenings table.
	 * 
	 * There is also a custom cell factory for the date column, so that only the first row of a particular 
	 * date displays the value, making the table a bit prettier and easier to interpret.
	 * 
	 */
	private void setUpScreeningsTable() {
		// setting what goes in the screenings table

				filmNameColumn2.setCellValueFactory(cellData -> cellData.getValue().filmProperty());
				dateColumn.setCellValueFactory(new PropertyValueFactory<Seance, LocalDate>("day"));
				timeColumn.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("time"));

				// setting how to render the dates
				
				dateColumn.setCellFactory(tc -> new TableCell<Seance, LocalDate>() {
					@Override
					protected void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);
						
						boolean dateShouldBeDisplayed = false;
						
						if (empty) {
							//setText(null);
						} else {
							String str = date.toString();
							setStyle(null);
							
							// if the hashtable is empty, then it's the first row, so store the date and row index to the hashmap and display date in table
							if (rowComparison.isEmpty()) {
//								setText(DateConversion.format(date));
								rowComparison.put(str, 1);
								
								dateShouldBeDisplayed = true;
								
							} 
							// if the date of the current row is in the hashmap and there is only 1 date in it, if the row is greater than or
							//equal to the current row, display the date.
							else if ((rowComparison.keySet().contains(str) && rowComparison.size() == 1)){
								
								if (rowComparison.get(str) >= getIndex() + 1) {
									dateShouldBeDisplayed = true;
								}
							} 
							// if the date is in the hashmap, print the date only if the row is the same
							// (necessary when the display is out of synch with the hasmap's data)
							else if (rowComparison.keySet().contains(str)) {
								
								if (rowComparison.get(str) == getIndex() + 1) {

									dateShouldBeDisplayed = true;
								}
							// if not, that means the date isn't in the hashmap, so store it and display it in table
							} else {
								rowComparison.put(str, getIndex() + 1);
								dateShouldBeDisplayed = true;
							}
							
							if (date.equals(selectedDate)) {
								setStyle("-fx-text-fill: #ff953f !important;");
							}
							
							if (dateShouldBeDisplayed) {
								setText(DateConversion.format(date));
								
							}
//							System.out.println(rowComparison.toString());
							
							
						}
					}
				});
				
				screeningsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

					
					if (newSelection == null) {
						selectedDate = null;
					} else {
						
						highlightDate();
					}
				});
				
	}
	
	
	
	
	
	
	
	

}
