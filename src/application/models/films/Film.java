package application.models.films;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;


/***************************************************************************************
 * This class is based on the following source:
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob 
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 ***************************************************************************************/

/**
 * Model class for a film. This will be used in conjunction with the FilmWrapper.java class to read/write data to/from the FilmData.xml files.
 * 
 *@author Joseph Courtley
 * 
 * 
 */
public class Film {

    private final StringProperty name;
    private final StringProperty path;
    private final StringProperty description;
    private final IntegerProperty ticketPrice;
    private final StringProperty trailerPath;
    private final StringProperty mainGenre;
    
    
    public Film() {
        this(null, null, null, 0, null, null);
    }
    
/**
 * This is the constructor of the class. It will initialise the attributes when a new instance of this class is made. 
 * @param name
 * @param path
 * @param description
 * @param ticketPrice
 * @param trailerPath
 */
    public Film(String name, String path, String description, Integer ticketPrice, String trailerPath, String mainGenre) {
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty(path);
        this.description = new SimpleStringProperty(description);
        this.ticketPrice = new SimpleIntegerProperty(ticketPrice);
        this.trailerPath = new SimpleStringProperty(trailerPath);
        this.mainGenre = new SimpleStringProperty(mainGenre);
    }

    /**
     * This is the getter method for the name attribute.
     * @return String
     */
    public String getName() {
        return name.get();
    }
    /**
     * This is the setter method for the name attribute.
     * @param name
     */
    public void setName(String name) {
        this.name.set(name);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty nameProperty() {
        return name;
    }
    
    
    
    
    /**
     * This is the getter method for the path attribute.
     * @return String
     */
    public String getPath() {
        return path.get();
    }
    /**
     * This is the setter method for the path attribute.
     * @param path
     */
    public void setPath(String path) {
        this.path.set(path);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty pathProperty() {
        return path;
    }


    /**
     * This is the getter method for the description attribute.
     * @return String
     */
	public String getDescription() {
		return description.get();
	}
	/**
     * This is the setter method for the description attribute.
     * @param description
     */
	public void setDescription(String description) {
		this.description.set(description);;
	}
	/**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
	public StringProperty descriptionProperty() {
		return description;
	}
	
	/**
     * This is the getter method for the ticketPrice attribute.
     * @return Integer
     */
	public Integer getTicketPrice() {
		return ticketPrice.get();
	}
	/**
     * This is the setter method for the ticketPrice attribute.
     * @param ticketPrice
     */
	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice.set(ticketPrice);;
	}
	/**
     * This method is used in the storage of the attribute.
     * @return IntegerProperty 
     */
	public IntegerProperty ticketPriceProperty() {
		return ticketPrice;
	}
	
	/**
     * This is the getter method for the trailerPath attribute.
     * @return String
     */
    public String gettrailerPath() {
        return trailerPath.get();
    }
    /**
     * This is the setter method for the trailerPath attribute.
     * @param trailerPath
     */
    public void settrailerPath(String trailerPath) {
        this.trailerPath.set(trailerPath);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty trailerPathProperty() {
        return trailerPath;
    }

    
    /**
     * This is the getter method for the MainGenre attribute.
     * @return String
     */
    public String getMainGenre() {
        return mainGenre.get();
    }
    /**
     * This is the setter method for the MainGenre attribute.
     * @param mainGenre
     */
    public void setMainGenre(String mainGenre) {
        this.mainGenre.set(mainGenre);
    }
    
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty mainGenreProperty() {
        return mainGenre;
    }
    
    
    
    
    
    
    
}
    
    