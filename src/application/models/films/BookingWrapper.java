package application.models.films;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import application.models.films.Booking;

/***************************************************************************************
 * This class is based on the following source:
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob 
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 ***************************************************************************************/

/**
 * This is a Wrapper Class that enables the reading/writing of Booking data to/from the Bookings.xml file. 
 * 
 * @author David Rudolf
 *
 */
@XmlRootElement(name = "bookings")
public class BookingWrapper {
	
	private List<Booking> bookings;
	/**
	 * This is a getter method for the bookings Java List.
	 * @return List
	 */
	@XmlElement(name = "booking")
	public List<Booking> getBookings() {
		return bookings;
	}
	
	/**
	 * This is a setter method for the bookings Java List.
	 * @param bookings
	 */
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
}
