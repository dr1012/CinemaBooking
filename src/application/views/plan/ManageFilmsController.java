package application.views.plan;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import application.MainApplication;
import application.models.films.Film;
import application.models.films.Seance;
import application.views.plan.util.DataSaveException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;


/**
 * 
 * The controller responsible for allowing the employee to add, delete and modify films in the XML data file.
 * 
 * @author Joseph Courtley
 *
 */
public class ManageFilmsController implements Initializable {
	
	String oldTrailerPath;
	private ObservableList<Film> films;
	private MainApplication main;
	
	@FXML private TextField trailerPathText;
	@FXML private TextField filmName;
	@FXML private TextArea description;
	@FXML private ComboBox<Integer> ticketCost;
	@FXML private ComboBox<String> genreBox;
	@FXML private Button uploadImage;
	@FXML private ImageView pic;
	@FXML private Button saveDeets;
	@FXML private Button exit;
	@FXML private Button clear;
	@FXML private Button delete;
	
	@FXML private RadioButton clickToEditFilm;
	@FXML private ComboBox<Film> chooseExistingFilm;
	@FXML private Label storeOldPicPath;
	
	private EmployeeTableController viewByDateController;

	private Stage stage;
	
	/**
	 * When the employee selects the radio button to edit an existing film,
	 * the combobox containing existing films is unlocked with this method.
	 */
	@FXML
	private void enableCombo() {
		
		boolean bool = !(clickToEditFilm.isSelected()) ? true:false;
		
		chooseExistingFilm.setDisable(bool);
		
		if (bool) {
			clearFields();
			File file = new File("resources/images/placeholder.png");
			Image image = new Image(file.toURI().toString());
			pic.setImage(image);
		}   
	}
	
	/**
	 * Clears the fields that are filled in.
	 */
	@FXML
	private void clearFields() {
		chooseExistingFilm.valueProperty().set(null);
		pic.setImage(null);
		
//		try {
			File file = new File("resources/images/placeholder.png");
			Image image = new Image(file.toURI().toString());
			pic.setImage(image);
//		} catch (NullPointerException e) {
//			System.out.println("Picture missing");
//		}
//		
		
		filmName.setText(null);
		description.setText(null);
		storeOldPicPath.setText(null);
		ticketCost.valueProperty().set(null);
		genreBox.valueProperty().set(null);
	}
	
	/**
	 * Method used to delete pre-existing films.
	 */
	@FXML
	private void deleteFilm() {
		
		String filmName = chooseExistingFilm.getSelectionModel().getSelectedItem().getName();
		
		File picToDelete = new File("resources/images/" + filmName + ".png");
		
		if (picToDelete.exists()) {
			picToDelete.delete();
		}
		
		Iterator<Film> films = main.getFilmData1().iterator();
		while (films.hasNext()) {
			Film f = films.next();
			if (f.getName().equals(filmName)) {
				films.remove();
				File file = new File("FilmData.xml");
				main.saveData(file);
			}
			
		}
		
		
		Iterator<Seance> seances = main.getSeanceData1().iterator();
		while (seances.hasNext()) {
			Seance s = seances.next();
			if (s.getFilm().equals(filmName)) {
				seances.remove();
			}
			
		}
		File file2 = new File("SeanceData.xml");
		main.saveData(file2);
		
		viewByDateController.refreshData();
		
	}
	
	
	
	/**
	 * Called when the employee selects an existing film from the combobox, and fills in all the fields 
	 * with their current values.
	 * 
	 */
	@FXML
	private void existingFilmPicked(){
		
		if (chooseExistingFilm.getValue() != null) {
		Film film = chooseExistingFilm.getValue();
		storeOldPicPath.setText(film.getPath());
		filmName.setText(film.getName());
		description.setText(film.getDescription());
		ticketCost.getSelectionModel().select(film.getTicketPrice());
		genreBox.getSelectionModel().select(film.getMainGenre());
		
    	Image image = new Image((new File(film.getPath()).toURI().toString()));
        pic.setImage(image);
        
        
        
        
		}
		
	}
	
	
	/**
	 * Method called to upload a picture. Stores the path of the image so that it can be processed and
	 * saved later when the employee hits save.
	 * 
	 */
	@FXML
	private void uploadPic() {
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        
        try {
        	File file = fileChooser.showOpenDialog(main.getPrimaryStage());
            
            Image image = new Image(file.toURI().toString());
            pic.setImage(image);
            storeOldPicPath.setText(file.getPath());
        } catch (Exception e) {
        	
        }
        
	}
	
	/**
	 * Uploads the path of a trailer, which will then be used by the save method to copy the file into
	 * the resources folder and set the trailer path property of the film being saved.
	 */
	@FXML
	private void uploadTrailer() {
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "mp4 files (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(extFilter);
        
        try {
        	File file = fileChooser.showOpenDialog(main.getPrimaryStage());
        	oldTrailerPath = file.getPath();
            trailerPathText.setText("Film uploaded");
        } catch (Exception e) {
        	
        }
	}
	
	/**
	 * Method used to save the details of a film.
	 * 
	 * As well as saving the new film object, the relevant picture and trailer are also added to the 
	 * resources folder with a name based on the chosen name of the film.
	 * 
	 * If the film exists already, the employee is presented with an alert asking
	 * if they want to overwrite the previous details. 
	 */
	@FXML
	private void saveDetails() {

		String newFilmN = filmName.getText();
		String oldPicPath = storeOldPicPath.getText();

		String newPath = "";
		String descrip = description.getText();
		String genre = genreBox.getValue();
		String trailerPath = "";

		Integer ticketPrice = ticketCost.getValue();
		
		
		BufferedImage image;
		
		if (oldPicPath != null) {
			File imageFile = new File(oldPicPath);
			newPath = "resources/images/" + newFilmN + ".png";
			try {
				image = ImageIO.read(imageFile);
				ImageIO.write(image, "png",new File(newPath));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (oldTrailerPath != null) {
			Path oldTrailer = Paths.get(oldTrailerPath);
			trailerPath = "resources/videos/" + newFilmN + ".mp4";
			Path newTrailer = Paths.get(trailerPath);
			
			try {
				Files.copy(oldTrailer, newTrailer, StandardCopyOption.REPLACE_EXISTING);
				
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		
		
		
		if (clickToEditFilm.isSelected() && chooseExistingFilm.getValue() != null) {

			try {
				String oldName = chooseExistingFilm.getSelectionModel().getSelectedItem().getName();

				for (Film film : main.getFilmData1()) {
					if (film.getName().equals(oldName)) {
						//                	if (film.getName().equals(filmN)) {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.initOwner(stage);
						alert.setTitle("Make changes to a film");
						alert.setHeaderText(String.format("Overwrite existing film details?"));
						alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
						Optional<ButtonType> result = alert.showAndWait();
						if(!result.isPresent()) {
							throw new DataSaveException("You need to confirm the save");

						} else if(result.get() == ButtonType.OK) {
							film.setName(newFilmN);
							film.setDescription(descrip);
							film.setPath(newPath);
							film.setTicketPrice(ticketPrice);
							film.setMainGenre(genre);
							film.settrailerPath(trailerPath);

							for (Seance seance: main.getSeanceData1()) {
								if (seance.getFilm().equals(oldName)) {
									seance.setFilm(newFilmN);
								}
							}
							File file = new File("FilmData.xml");
							main.saveData(file);
							File file2 = new File("SeanceData.xml");
							main.saveData(file2);
							
							viewByDateController.refreshData();
							
							
							return;

						} else if(result.get() == ButtonType.CANCEL)
							throw new DataSaveException("Cancel selected");
					}
				}
			} catch (DataSaveException e) {
				e.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			

		}
		
		

		Film newFilm = new Film(newFilmN, newPath, descrip, ticketPrice, trailerPath, genre);

		main.getFilmData1().add(newFilm);
		File file = new File("FilmData.xml");
		main.saveData(file);
		viewByDateController.refreshData();
		
	}
	
	
	@FXML
	private void exitModify() {
		stage.close();
	}
	
	public void setTheStage(Stage stage) {
		this.stage = stage;
	}
	
	
	
	/**
	 * Initialises parts of the controller which do not require data from MainApplication.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		description.setWrapText(true);
		ObservableList<String> genresOfFilm = FXCollections.observableArrayList();
		genresOfFilm.addAll("Comedy", "Horror", "Action", "Romantic Comedy", "Fantasy", "Thriller");
		genreBox.setItems(genresOfFilm);
		

		ObservableList<Integer> prices = FXCollections.observableArrayList();
		prices.addAll(5, 8, 10);
		ticketCost.setItems(prices);
		
		ticketCost.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
			@Override public ListCell<Integer> call(ListView<Integer> p) {
				return new ListCell<Integer>() {
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (empty || item == null) {
							setText(null);
							setGraphic(null);
						} else {
							setText("£" + item.toString());
						}
					}
				};
			}

		});
		
		ticketCost.setConverter(new StringConverter<Integer>() {
			@Override
			public String toString(Integer integer) {
				if (integer == null) {
					return null;
				} else {
					return "£" + integer.toString();
				}
			}
			
			@Override
			public Integer fromString(String integerString) {
				return null;
			}
		});
		
		
//		chooseExistingFilm.setDisable(true);
//		storeOldPicPath.setVisible(false);
		
		
		
		
	}
	
	/**
	 * 
	 * Passes in a reference to the MainApplication object with its methods for accessing the XML data.
	 * Sets up any parts of the controller which involve this data.
	 * 
	 * @param main
	 * @param viewByDateController
	 */
	public void setMain(MainApplication main, EmployeeTableController viewByDateController) {
		this.main = main;
		this.viewByDateController = viewByDateController;
	
	chooseExistingFilm.setItems(main.getFilmData1());
	chooseExistingFilm.setCellFactory((comboBox) -> {
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
	
	chooseExistingFilm.setConverter(new StringConverter<Film>() {
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
	
	chooseExistingFilm.setDisable(true);
	storeOldPicPath.setVisible(false);
	
	}

}
