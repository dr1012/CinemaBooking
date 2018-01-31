package application.views.plan;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.MainApplication;
import application.models.films.Booking;
import application.models.films.Seance;
import application.util.DateConversion;
import application.views.client.MainControl;
import application.views.plan.util.DataSaveException;
import application.views.plan.util.Fader;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * This controller is in charge of the anchorpane at the root of the employee view.
 * The more complicated functionality pertaining to tables is delegated to the 
 * EmployeeTableController. The BaseEmployeeController has methods allowing the employee to 
 * manage films in the system, to export data to a csv file and to logout.
 * 
 * @author josephcourtley
 *
 */
public class BaseEmployeeController implements Initializable{
	

	private MainApplication main;
	private EmployeeTableController viewByDateController;
	
	@FXML private Button exportData;
	@FXML private Button manageFilms;
	@FXML private Button logOut;
	@FXML private BorderPane viewByWindow;
	@FXML private Label userNameLbl;
	private AnchorPane root;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
       
		
	}
	
	
	/**
	 * 
	 * This method is called when the BaseEmployee FXML file is loaded.
	 * 
	 * To keep things object oriented, a reference to main is passed through to the BaseEmployeeController.
	 * This can only happen after initialize() has been called, so setMain effectively does the job of initialize().
	 * 
	 * @param main
	 * @param root
	 */
	public void setMain(MainApplication main, AnchorPane root) {
        this.root = root;
        this.main = main;
        this.main.getPrimaryStage().sizeToScene();
        
        root.setOpacity(0);
		Fader.fadeIn(root);
		
		userNameLbl.setText("Welcome, " + MainControl.getCurrentUsername());
        
        displayContent();
    }
	
	 /**
	 * 	
	 * Exports the screenings data to a csv file situated in the root directory of the project.
	 *  
	 * This method is partly based on the following source: https://stackoverflow.com/questions/17273806/printing-importing-and-exporting-data-inside-tableview-in-javafx
	 *
	 * @throws Exception
	 */
	@FXML
	private void exportToCSV() throws Exception {
		
		
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(main.getPrimaryStage());
		alert.setTitle("Make changes to a film");
		alert.setHeaderText(String.format("Export screenings data to csv file? \n (You will find the file in the root directory of this project)"));
		
		alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(!result.isPresent()) {

		} else if(result.get() == ButtonType.OK) {
			
			Writer writer = null;
		    try {
		        File file = new File("filmData.csv");
		        writer = new BufferedWriter(new FileWriter(file));
		        
		        writer.write("Date, Time, Film, Booked Seats, Available Seats \n");
		        for (Seance seance : main.getSeanceData1()) {
		        	
		        	int bookedSeats = 0;
		        	int availableSeats = 36;
		        	
		        	for (Booking booking : main.getBookingData1()) {
		        		
		        		if (booking.getScreeningDate().equals(seance.getDay()) && (booking.getScreeningTime() + ":00").equals(seance.getTime())) {
		        			bookedSeats++;
		        			availableSeats--;
		        		}
		        	}
		        	
		        	
		        	

		            String text = DateConversion.format(seance.getDay()) + "," + seance.getTime() + "," + seance.getFilm() + "," + bookedSeats + "," + availableSeats + "\n";
		            
		            writer.write(text);
		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    finally {

		        writer.flush();
		         writer.close();
		    } 
			
			
			
			return;

		} else if(result.get() == ButtonType.CANCEL) {
			
		}
		
	}

	/**
	 * method responsible for displaying the content in the film table.
	 */
	private void displayContent() {
		String path = "views/plan/viewByDate.fxml";
		
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(path));
        
        try {
            viewByWindow.getChildren().clear();
            viewByWindow.setCenter((AnchorPane) loader.load());
//            viewByWindow.getChildren().add((AnchorPane) loader.load());
            
            } catch (Exception e) {
            	e.printStackTrace();
            }
        
        viewByDateController = loader.getController();
        viewByDateController.setMain(this.main);
        
        Fader.fadeIn(viewByWindow);
        
	}
	
	/**
	 * Loads ManageFilms FXML file and displays in another window.
	 */
	@FXML
	private void goToManageFilms() {
		try {

			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("views/plan/ManageFilms.fxml"));
            BorderPane newPage = (BorderPane) loader.load();
            
            
            
            Stage tempStage = new Stage();
            tempStage.setTitle("Manage Films");
            tempStage.initModality(Modality.WINDOW_MODAL);
            tempStage.initOwner(main.getPrimaryStage());
            
            
            
            ManageFilmsController controller = loader.getController();
            controller.setMain(this.main, viewByDateController);
            controller.setTheStage(tempStage);
            
            Scene scene = new Scene(newPage);
            tempStage.setScene(scene);
            
            tempStage.showAndWait();
            
//            displayContent();
            
//            viewByDateController.refreshData();
//            tempStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Logs the employee out, using a fade transition.
	 */
	@FXML
	private void logOut() {
		
		FadeTransition fadeTrans = new FadeTransition();
		fadeTrans.setDuration(Duration.millis(500));
		fadeTrans.setNode(viewByWindow.getParent());
		fadeTrans.setFromValue(1);
		fadeTrans.setToValue(0);
		
		fadeTrans.setOnFinished(e -> main.initLogin());
		
		fadeTrans.play();
	}
	
	
	
}
