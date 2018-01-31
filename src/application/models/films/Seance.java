package application.models.films;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import application.util.LocalDateAdapter;


/***************************************************************************************
 * This class is based on the following source:
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob 
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 ***************************************************************************************/

/**
 * Model class for a viewing (we call it seance in our code). This will be used in conjunction with the SeanceListWrapper.java class to read/write data to/from the FilmData.xml files.
 * 
 *@author Joseph Courtley
 * 
 * 
 */
public class Seance {
	
	
	private final ObjectProperty<LocalDate> day;
	private final StringProperty time;
	private final StringProperty film;
	
	
	public Seance() {
        this(null, null, null);
    }
	
	/**
	 * This is the constructor of the class. It will initialise the attributes when a new instance of this class is made. 
	 * @param date
	 * @param time
	 * @param film
	 */
	public Seance(LocalDate date, String time, String film){
		this.day = new SimpleObjectProperty<LocalDate>(date);
		this.time = new SimpleStringProperty(time);
		this.film = new SimpleStringProperty(film);
	}
	
	
	  /**
     * This is the getter method for the day attribute.
     * @return LocalDate
     */
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDay(){
		return day.get();
	}
	
	 /**
     * This is the setter method for the day attribute.
     * @param day
     */
	public void setDay(LocalDate day) {
        this.day.set(day);
    }

	 /**
     * This method is used in the storage of the attribute.
     * @return ObjectProperty
     */
    public ObjectProperty<LocalDate> dayProperty() {
        return day;
    }
	
	
	
	
	
    /**
     * This is the getter method for the film attribute.
     * @return String
     */
	public String getFilm() {
        return film.get();
    }
	
	 /**
     * This is the setter method for the film attribute.
     * @param film
     */
    public void setFilm(String film) {
        this.film.set(film);
    }
    
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty filmProperty() {
        return film;
    }
    
    
    /**
     * This is the getter method for the time attribute.
     * @return String
     */
    public String getTime() {
        return time.get();
    }

    /**
     * This is the setter method for the time attribute.
     * @param time
     */
    public void setTime(String time) {
        this.time.set(time);
    }

    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty timeProperty() {
        return time;
    }

	
	
	

}
