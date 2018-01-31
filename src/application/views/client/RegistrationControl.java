package application.views.client;

import java.io.File;
import java.io.IOException;
import application.MainApplication;
import application.models.login.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/***************************************************************************************
 * This class is based on the following source:
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob 
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 ***************************************************************************************/

/**
 * This is the controller class for the Registration.fxml file. 
 * It's purpose is to save new Client data to the Clients.xml file.
 * @author David Rudolf
 *
 */
public class RegistrationControl  {
	
	private MainApplication main;
	
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;
	
	@FXML
	private TextField txtFirstName;
	
	@FXML
	private TextField txtLastName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML 
	private Button backButton;
	
	
	/**
	 * This method is called when the 'Register' button is pressed. 
	 * It's purpose is to save the data that has been input into the text fields of the Registration.fxml window into the Clients.xml file.
	 * 
	 * First it checks that all  textFields have been filled. If this is not the case, and alert window will be shown. 
	 * If all the text fields have been filled. The client data is added to the ClienData ObservableList in MainApplication.java and the elements of the ObservableList are 
	 * saved to the XML file. An alert window then informs the user that his details have been correctly saved.
	 * 
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void Register(ActionEvent event) throws Exception {
		

				
					
					if (txtUsername.getText().isEmpty()==false && txtPassword.getText().isEmpty()==false && txtFirstName.getText().isEmpty()==false && txtLastName.getText().isEmpty()==false && txtEmail.getText().isEmpty()==false) {
					
		           Client client = new Client(txtUsername.getText(),txtPassword.getText(), txtFirstName.getText(), txtLastName.getText(),txtEmail.getText());
		            
		           MainApplication.getClientData().add(client);
		    		File file = new File("Clients.xml");
		    		MainApplication.saveClientDataToFile(file);
		    		
		    		Alert alert = new Alert (AlertType.INFORMATION);
		    		alert.setTitle("New registration");
		    		alert.setHeaderText(null);
		    		alert.setContentText("Thank you. Your details have been added");
		    		alert.showAndWait();
		    		 File file1 = new File("Clients.xml");
		    			
		    			MainApplication.loadClientDataFromFile(file1);
					}
				
	
		
		else {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please fill out all the fields");

			alert.showAndWait();
			
		}
					
				
	}
		
	
	/**
	 * This method is called when the 'Back' button is pressed.
	 * It navigates back to the Login window.
	 * @throws Exception
	 */
	 public void Back() throws Exception {
		Stage stage;
		Parent root;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApplication.class.getResource("views/client/Login.fxml"));

		root = loader.load();
		MainControl controller = loader.getController();
		controller.setMain(this.main);
		stage = (Stage) backButton.getScene().getWindow();
		Scene scene = new Scene(root,800,600);

		stage.setScene(scene);
		stage.show();
		}
	
	 public void setMain(MainApplication main) {
			
			this.main = main;

	 }
	 
	 /**
	  * This is the main method of the Registration.fxml window. 
	  * It loads the client data from the Clients.fxml file when this window is opened. This is in preparation of adding new details to the Clients.fxml file.
	  * @throws IOException
	  */
	 @FXML
		void initialize() throws IOException {	
		 
		 File file1 = new File("Clients.xml");
		
		MainApplication.loadClientDataFromFile(file1);
		
		
		 
	 }
	 
			
}
	
	
	
	