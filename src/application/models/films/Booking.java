package application.models.films;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import application.util.LocalDateAdapter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;

/***************************************************************************************
 * This class is based on the following source:
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob 
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 ***************************************************************************************/

/**
 * Model class for a booking. This will be used in conjunction with the BookingWrapper.java class to read/write data to/from the Bookings.xml files.
 * 
 *
 * @author David Rudolf
 */
public class Booking {

    private final StringProperty UserName;
    private final StringProperty Title;
    private final ObjectProperty<LocalDate> ScreeningDate;
    private final IntegerProperty ScreeningTime;
    private final ObjectProperty<LocalDate> BookingDate;
    private final StringProperty Seat;

    
    
    public Booking() {
        this(null, null, null,0, null, null);
    }
    
/**
 * his is the constructor of the class. It will initialise the attributes when a new instance of this class is made. 
 * @param UserName
 * @param Title
 * @param ScreeningDate
 * @param ScreeningTime
 * @param BookingDate
 * @param Seat
 */
    public Booking(String UserName, String Title, LocalDate ScreeningDate , int ScreeningTime, LocalDate BookingDate, String Seat) {
        this.UserName = new SimpleStringProperty(UserName);
        this.Title = new SimpleStringProperty(Title);
        this.ScreeningDate = new SimpleObjectProperty<LocalDate>(ScreeningDate);
        this.ScreeningTime = new SimpleIntegerProperty(ScreeningTime);
        this.BookingDate = new SimpleObjectProperty<LocalDate>(BookingDate);
        this.Seat = new SimpleStringProperty(Seat);
    
        
        
    }
    
    
   
  
    
    /**
     * This is the getter method for the UserName attribute.
     * @return String
     */
    public String getUserName() {
        return UserName.get();
    }
    
    /**
     * 
     * @param UserName
     */
    public void setUserName(String UserName) {
        this.UserName.set(UserName);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty UserNameProperty() {
        return UserName;
    }
    
    
    
    /**
     * This is the getter method for the Title attribute.
     * @return Title.get()
     */
    public String getTitle() {
        return Title.get();
    }
    /**
     * This is the setter method for the Title attribute.
     * @param Title
     */
    public void setTitle(String Title) {
        this.Title.set(Title);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty TitleProperty() {
        return Title;
    }
    
    
    
    
    /**
     * This is the getter method for the ScreeningDate attribute.
     * @return LocalDate
     */
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getScreeningDate(){
		return ScreeningDate.get();
	}
    /**
     * This is the setter method for the ScreeningDate attribute.
     * @param screeningdate
     */
	public void setScreeningDate(LocalDate screeningdate) {
        this.ScreeningDate.set(screeningdate);
    }
	 /**
     * This method is used in the storage of the attribute.
     * @return ObjectProperty
     */
    public ObjectProperty<LocalDate> screeningDateProperty() {
        return ScreeningDate;
    }
    


    
    
    /**
     * This is the getter method for the ScreeningTime attribute.
     * @return int
     */
    public int getScreeningTime() {
        return ScreeningTime.get();
    }
    /**
     * This is the setter method for the ScreeningTime attribute.
     * @param time
     */
    public void setScreeningTime(int time) {
        this.ScreeningTime.set(time);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return IntegerProperty 
     */
    public IntegerProperty ScreeningTimeProperty() {
        return ScreeningTime;
    }
    
    
    
    
    
    /**
     * This is the getter method for the BookingDate attribute.
     * @return LocalDate
     */
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
   	public LocalDate getBookingDate(){
   		return BookingDate.get();
   	}
    /**
     * This is the setter method for the BookingDate attribute.
     * @param bookingdate
     */
   	public void setBookingDate(LocalDate bookingdate) {
           this.BookingDate.set(bookingdate);
       }
    /**
     * This method is used in the storage of the attribute.
     * @return ObjectProperty 
     */
       public ObjectProperty<LocalDate> bookingDateProperty() {
           return BookingDate;
       }
    
    
    
    
       /**
        * This is the getter method for the Seat attribute.
        * @return String
        */
    public String getSeat() {
        return Seat.get();
    }
    /**
     * This is the setter method for the Seat attribute.
     * @param Seat
     */
    public void setSeat(String Seat) {
        this.Seat.set(Seat);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty SeatProperty() {
        return Seat;
    }
    

    

}
