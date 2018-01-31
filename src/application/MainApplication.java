package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import application.models.films.Booking;
import application.models.films.BookingWrapper;
import application.models.films.Film;
import application.models.films.FilmListWrapper;
import application.models.films.Seance;
import application.models.films.SeanceListWrapper;
import application.models.login.Client;
import application.models.login.ClientWrapper;
import application.models.login.Employee;
import application.models.login.EmployeeWrapper;
import application.views.client.MainControl;
import application.views.plan.BaseEmployeeController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;





/**
 *
 * This class is the Main class of the program.
 * It launches the first (login-in) window when the program is run.
 * It contains the methods and variables to load and save data from the xml files.
 * The data is loaded into the Observable Lists.
 *
 *
 * @author David Rudolf
 *
 *
 */
public class MainApplication extends Application {

	private Stage primaryStage;
	private Parent root;

	/**
	 * This ObservableList contains the  sessions (wievings) that we refer to as "Seance".
	 * These are loaded from the SeanceData.xml file.
	 *
	 */
	private static ObservableList<Seance> seanceData = FXCollections.observableArrayList();
	/**
	 * This JavaFX ObservableList contains the film objects that are loaded from the FilmData.xml file.
	 */
	private static ObservableList<Film> filmData = FXCollections.observableArrayList();
	/**
	 * This JavaFX ObservableList contains the client objects that are loaded from the Clients.xml file.
	 */
	private static ObservableList<Client> clientData = FXCollections.observableArrayList();
	/**
	 * This JavaFX ObservableList contains the employee objects that are loaded from the Employees.xml file.
	 */
	private static ObservableList<Employee> employeeData = FXCollections.observableArrayList();
	/**
	 * This JavaFX ObservableList contains the booking objects that are loaded from the Bookings.xml file.
	 */
	private static ObservableList<Booking> bookingData = FXCollections.observableArrayList();


	private ObservableList<Seance> seanceData1 = FXCollections.observableArrayList();
	private ObservableList<Film> filmData1 = FXCollections.observableArrayList();
	private ObservableList<Booking> bookingsData1 = FXCollections.observableArrayList();

/**
 * This is the getter method for the Seance list.
 *
 * @return ObservableList
 */
    public static ObservableList<Seance> getSeanceData() {
        return seanceData;
    }

    /**
     * This is the getter method for the Film list.
     *
     * @return ObservableList
     */

    public static ObservableList<Film> getFilmData() {
        return filmData;
    }

    /**
     * This is the getter method for the Client list.
     *
     * @return ObservableList
     */

    public static ObservableList<Client> getClientData() {
        return clientData;
    }

    /**
     * This is the getter method for the Employee list.
     *
     * @return ObservableList
     */
    public static ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    /**
     * This is the getter method for the Booking list.
     *
     * @return ObservableList
     */

    public static ObservableList<Booking> getBookingData() {
        return bookingData;
    }

		public ObservableList<Seance> getSeanceData1() {
        return seanceData1;
    }

    public ObservableList<Film> getFilmData1() {
        return filmData1;
    }

    public ObservableList<Booking> getBookingData1() {
        return bookingsData1;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }





    /*
     * This method initiates the first window in the program - the login window.
     *
     * @throws IOException
     */
    public void initLogin() {
    	try {
    		
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("views/client/Login.fxml"));
    		
			Parent root = loader.load();
			Scene scene = new Scene(root,800,600);
			primaryStage.setScene(scene);
			
			MainControl controller = loader.getController();
            controller.setMain(this);
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    






    /**
     * This is the main method that will start the program. It is the main entry point for the JavaFX application.
     * @param pStage
     */

	@Override
	public void start(Stage pStage) {
		primaryStage = pStage;
		primaryStage.setTitle("Cinema Booking System");

		initLogin();
	}

	public static void main(String[] args) {
		launch(args);
	}



/**
 * This method loads the film viewings data from the SeanceData.xml file into the seanceData ObservableList via the SeanceListWrapper.java class.
 * This method clears the seanceData ObservableList before loading the data.  
 * @param file
 */
	public static void loadSeanceDataFromFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(SeanceListWrapper.class);
    		Unmarshaller unmarshalleur = context.createUnmarshaller();

    		SeanceListWrapper wrap = (SeanceListWrapper) unmarshalleur.unmarshal(file);

    		seanceData.clear();
    		seanceData.addAll(wrap.getSeances());

    	} catch (Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not load: " + file.getPath());

            alert.showAndWait();
    	}
    }

	/**
	 * This method saves all the data contained within the seanceData ObservableList into  the SeanceData.xml file via the SeanceListWrapper.java class.
	 * This overrides any previously stored data in the XML file.
	 * @param file
	 */
    public void saveSeanceDataToFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(SeanceListWrapper.class);
    		Marshaller marshalleur = context.createMarshaller();
    		marshalleur.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    		SeanceListWrapper wrap = new SeanceListWrapper();
    		wrap.setSeances(seanceData);

    		//Save XML to the file
    		marshalleur.marshal(wrap, file);

    	} catch (Exception e) {
    		e.printStackTrace();
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not save data in file: " + file.getPath());
    		System.out.println(e);
            alert.showAndWait();
    	}
    }

    /**
     * This method loads the film  data from the FilmData.xml file into the filmData ObservableList via the FilmListWrapper.java class.
     * This method clears the filmData ObservableList before loading the data.  
     * @param file
     */
    public static void  loadFilmDataFromFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(FilmListWrapper.class);
    		Unmarshaller unmarshalleur = context.createUnmarshaller();

    		FilmListWrapper wrap = (FilmListWrapper) unmarshalleur.unmarshal(file);

    		filmData.clear();
    		filmData.addAll(wrap.getFilms());

    	} catch (Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not load: " + file.getPath());
    		System.out.println(e);
            alert.showAndWait();
    	}
    }

	/**
	 * This method saves all the data contained within the filmData ObservableList into  the FilmData.xml file via the FilmListWrapper.java class.
	 * This overrides any previously stored data in the XML file.
	 * @param file
	 */
    public static void saveFilmDataToFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(FilmListWrapper.class);
    		Marshaller marshalleur = context.createMarshaller();
    		marshalleur.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    		FilmListWrapper wrap = new FilmListWrapper();
    		wrap.setFilms(filmData);

    		//Save XML to the file
    		marshalleur.marshal(wrap, file);

    	} catch (Exception e) {
    		e.printStackTrace();
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not save data in file: " + file.getPath());
    		System.out.println(e);
            alert.showAndWait();
    	}
    }


    /**
     * This method loads the client data from the Clients.xml file into the clientData ObservableList via the ClientWrapper.java class.
     * This method clears the clientData ObservableList before loading the data.  
     * @param file
     */
    public static void loadClientDataFromFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(ClientWrapper.class);
    		Unmarshaller unmarshalleur = context.createUnmarshaller();

    		ClientWrapper wrap = (ClientWrapper) unmarshalleur.unmarshal(file);

    		clientData.clear();
    		clientData.addAll(wrap.getClients());

    	} catch (Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not load: " + file.getPath());
    		System.out.println(e);
            alert.showAndWait();
    	}
    }





	/**
	 * This method saves all the data contained within the clientData ObservableList into  the Clients.xml file via the ClientWrapper.java class.
	 * This overrides any previously stored data in the XML file.
	 * @param file
	 */
    public static void saveClientDataToFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(ClientWrapper.class);
    		Marshaller marshalleur = context.createMarshaller();
    		marshalleur.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    		ClientWrapper wrap = new ClientWrapper();
    		wrap.setClients(clientData);

    		//Save XML to the file
    		marshalleur.marshal(wrap, file);

    	} catch (Exception e) {
    		e.printStackTrace();
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not save data in file: " + file.getPath());
    		System.out.println(e);
            alert.showAndWait();
    	}
    }




    /**
     * This method loads the employee  data from the Employees.xml file into the employeeData ObservableList via the EmployeeWrapper.java class.
     * This method clears the employeeData ObservableList before loading the data.  
     * @param file
     */
    public static void loadEmployeeDataFromFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(EmployeeWrapper.class);
    		Unmarshaller unmarshalleur = context.createUnmarshaller();

    		EmployeeWrapper wrap = (EmployeeWrapper) unmarshalleur.unmarshal(file);

    		employeeData.clear();
    		employeeData.addAll(wrap.getEmployees());

    	} catch (Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not load: " + file.getPath());
    		System.out.println(e);
            alert.showAndWait();
    	}
    }



    /**
     * This method loads the booking data from the Bookings.xml file into the bookingData ObservableList via the BookingWrapper.java class.
     * This method clears the bookingData ObservableList before loading the data.  
     * @param file
     */
    public static void loadBookingDataFromFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(BookingWrapper.class);
    		Unmarshaller unmarshalleur = context.createUnmarshaller();

    		BookingWrapper wrap = (BookingWrapper) unmarshalleur.unmarshal(file);

    		bookingData.clear();
    		bookingData.addAll(wrap.getBookings());

    	} catch (Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not load: " + file.getPath());
    		System.out.println(e);
            alert.showAndWait();
    	}
    }





    /**
	 * This method saves all the data contained within the bookingData ObservableList into the Bookings.xml file via the BookingWrapper.java class.
	 * This overrides any previously stored data in the XML file.
	 * @param file
	 */
    public static void saveBookingDataToFile(File file) {
    	try {

    		JAXBContext context = JAXBContext.newInstance(BookingWrapper.class);
    		Marshaller marshalleur = context.createMarshaller();
    		marshalleur.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    		BookingWrapper wrap = new BookingWrapper();
    		wrap.setBookings(bookingData);

    		//Save XML to the file
    		marshalleur.marshal(wrap, file);

    	} catch (Exception e) {
    		e.printStackTrace();
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("Could not save data in file: " + file.getPath());
    		System.out.println(e);

            alert.showAndWait();
    	}
    }


	

	public void loadData(File file) {
	try {


		switch (file.toString()) {
			case "FilmData.xml":
				JAXBContext context = JAXBContext.newInstance(FilmListWrapper.class);
					Unmarshaller unmarshalleur = context.createUnmarshaller();
					FilmListWrapper wrap = (FilmListWrapper) unmarshalleur.unmarshal(file);
					filmData1.clear();
					filmData1.addAll(wrap.getFilms());
				break;
			case "SeanceData.xml":
				JAXBContext context2 = JAXBContext.newInstance(SeanceListWrapper.class);
				Unmarshaller unmarshalleur2 = context2.createUnmarshaller();
				SeanceListWrapper wrap2 = (SeanceListWrapper) unmarshalleur2.unmarshal(file);
				seanceData1.clear();
				seanceData1.addAll(wrap2.getSeances());
				break;
			case "Bookings.xml":
				JAXBContext context3 = JAXBContext.newInstance(BookingWrapper.class);
				Unmarshaller unmarshalleur3 = context3.createUnmarshaller();
				BookingWrapper wrap3 = (BookingWrapper) unmarshalleur3.unmarshal(file);
				bookingsData1.clear();
				bookingsData1.addAll(wrap3.getBookings());
				break;
		}

	} catch (Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Could not load: " + file.getPath());

		alert.showAndWait();
	}

}




	public void saveData(File file) {
	try {


		switch (file.toString()) {
			case "FilmData.xml":
				JAXBContext context = JAXBContext.newInstance(FilmListWrapper.class);
					Marshaller marshalleur = context.createMarshaller();
					marshalleur.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

					FilmListWrapper wrap = new FilmListWrapper();
					wrap.setFilms(filmData1);

					//Save XML to the file
					marshalleur.marshal(wrap, file);
				break;
			case "SeanceData.xml":
				JAXBContext context2 = JAXBContext.newInstance(SeanceListWrapper.class);
					Marshaller marshalleur2 = context2.createMarshaller();
					marshalleur2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

					SeanceListWrapper wrap2 = new SeanceListWrapper();
					wrap2.setSeances(seanceData1);

					marshalleur2.marshal(wrap2, file);
				break;
			case "Bookings.xml":
				JAXBContext context3 = JAXBContext.newInstance(BookingWrapper.class);
					Marshaller marshalleur3 = context3.createMarshaller();
					marshalleur3.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

					BookingWrapper wrap3 = new BookingWrapper();
					wrap3.setBookings(bookingsData1);

					marshalleur3.marshal(wrap3, file);
				break;
		}

	} catch (Exception e) {
		e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Could not save data in file: " + file.getPath());

					alert.showAndWait();
	}

}
}
