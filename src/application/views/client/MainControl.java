package application.views.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.MainApplication;
import application.models.login.Client;
import application.models.login.Employee;
import application.views.plan.BaseEmployeeController;
import application.views.plan.util.Fader;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
 * This class is the controller of the Login.fxml and Registration.fxml files. 
 * 
 * 
 * @author David
 * @author Joseph Courtley
 */
public class MainControl {
	
	private static String currentUsername;
	
	@FXML
	private Label lblStatus;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;
	
	@FXML private BorderPane bPane;
	
	@FXML
	private TextField confirmPassword;
	
	@FXML
	private TextField staffID;
	
	@FXML 
	private Button regButton;
	
	@FXML 
	private Button backButton;
	
	private MainApplication main;
	
	   private ArrayList<String> clientUsername = new ArrayList<String>();
	   private ArrayList<String> clientPassword = new ArrayList<String>();
	   private ArrayList<String> employeeUsername = new ArrayList<String>();
	   private ArrayList<String> employeePassword = new ArrayList<String>();
	   
	   
	/**
	 * This method will look for a match of between the input username and the Client database. If a match is found it will return all of the client's
	 * details as a Client object. Refer to Client.java file for the explanation of the Client class. 
	 * @param username
	 * @return Client
	 */
	public static Client getCurrentClientDetails(String username) {
		Client dummyclient = new Client();
		for(Client client : MainApplication.getClientData()) {
		if(client.getUserName().equals(username)) {
			dummyclient = client;
		}
		}
		return dummyclient;
	
	}
	
	/**
	 * This method will return the index of the client with the input username within the ClientData ObservableList in MainApplication.
	 * If the input username is not in the client database then  a default value of -1 will be returned.
	 * @param username
	 * @return int
	 */
	public static int getClientIndex(String username) {
		int index = -1;
		for(Client client : MainApplication.getClientData()) {
		if(client.getUserName().equals(username)) {
			 index = MainApplication.getClientData().indexOf(client);
			break;
		
		}
		else {
			index = -1;
		
		}
		
		
	}
		return index;
	}
	   
	/**
	 * This method is called when the "login" button is pressed. It first checks if the user name and password combination  that have been input
	 * are in the Client database. If they are, then the Main_Client.fxml window (client part of the program) will open which is the main menu for a client user.
	 * If the details are not in the Client database, then  the program will check if the user name/password combination is in the Employee, 
	 * if this is  the case, then the baseEmployee  window (employee part of the program) will open. This is the main menu for an employee.
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void Login(ActionEvent event) throws Exception {
		
		

		
		File file1 = new File("Clients.xml");
        
        MainApplication.loadClientDataFromFile(file1);
        
        File file2 = new File("Employees.xml");
        
        MainApplication.loadEmployeeDataFromFile(file2);
        
      
		for(Client client : MainApplication.getClientData()) {
			clientUsername.add(client.getUserName());
			clientPassword.add(client.getPassword());
			
		}
		
		for(Employee employee : MainApplication.getEmployeeData()) {
			employeeUsername.add(employee.getUserName());
			employeePassword.add(employee.getPassword());
		
		}
		
		
		
		
			if(clientUsername.contains(txtUsername.getText())&&clientPassword.get(clientUsername.indexOf(txtUsername.getText())).equals(txtPassword.getText())) {
				
				try {
					currentUsername = txtUsername.getText();
					
					
					
					
					
					
					FadeTransition fadeTrans = new FadeTransition();
					fadeTrans.setDuration(Duration.millis(1000));
					fadeTrans.setNode(txtUsername.getParent());
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
							
							stage = (Stage) regButton.getScene().getWindow();
							
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
					
					e.printStackTrace();
				}
					
				}
				
				
		
			
			
			
			
			
			
			else if(employeeUsername.contains(txtUsername.getText())&&employeePassword.get(employeeUsername.indexOf(txtUsername.getText())).equals(txtPassword.getText())) {
				
				currentUsername = txtUsername.getText();

				try {
		    		File file3 = new File("FilmData.xml");
		    		main.loadData(file3);

		            File file4 = new File("SeanceData.xml");
		            main.loadData(file4);

		    	} catch (Exception e) {

		    		System.out.println("can't load data");
		    		System.out.println(e);
		    		e.printStackTrace();
		    		return;
		    	}

				FadeTransition fadeTrans = new FadeTransition();
				fadeTrans.setDuration(Duration.millis(1000));
				fadeTrans.setNode(txtUsername.getParent());
				fadeTrans.setFromValue(1);
				fadeTrans.setToValue(0);

				fadeTrans.setOnFinished(e -> showBase_Employee());

				fadeTrans.play();




			}

			else {

				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Your username/password combination is incorrect");

				alert.showAndWait();
			}
		}
	
	
	/**
	 * This method is incorporated within the Login method and is used to open the Employee main menu window (baseEmployee.fxml).
	 */
	public void showBase_Employee(){


				try {
				FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("views/plan/baseEmployee.fxml"));
			AnchorPane baseEmployeeView = (AnchorPane) loader.load();

			Scene scene = new Scene(baseEmployeeView);

			main.getPrimaryStage().setScene(scene);
			baseEmployeeView.setOpacity(0);
			main.getPrimaryStage().setScene(scene);


			BaseEmployeeController controller = loader.getController();
			controller.setMain(this.main, baseEmployeeView);
				} catch (IOException e) {

					e.printStackTrace();
				}

		}
	
	
	/**
	 * This method is called when the "Register" button is pressed. It takes the user to the Registration.fxml window.
	 * @param event 
	 * @throws Exception
	 */
	@FXML 
	public void GoToReg(ActionEvent event) throws Exception{
		
		Stage stage;
		Parent root;
		
		
		if (event.getSource()==regButton) {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("views/client/Registration.fxml"));
			
			root = loader.load();
			RegistrationControl controller = loader.getController();
            controller.setMain(this.main);
			stage = (Stage) regButton.getScene().getWindow();
			Scene scene = new Scene(root,800,600);
		
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	
	
	/**
	 * This method is called when the "Back" button is pressed in the Register.fxml window. This takes the user back to the Login page. 
	 * A  user who ccompleted the registration form is not automaticaly logged in, so he will have to go through the log in process to access the client main menu.
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void Back(ActionEvent event) throws Exception {
		Stage stage;
		Parent root;
		
		if (event.getSource()==backButton) {
			stage = (Stage) backButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
	}
	

	
	public void setMain(MainApplication main) {
        this.main = main;
    }
	
	/**
	 * This method is the getter method for the private currentUsername variable. It will return this variable when called.
	 * @return String
	 */
	public static String getCurrentUsername() {
		return currentUsername;
	}
	
	/**
	 * This method is the setter method for the private currenUsername variable.
	 * @param name 
	 */
	public static void setCurrentUsername(String name) {
		currentUsername = name;
	}
	
	
	
	
	

}