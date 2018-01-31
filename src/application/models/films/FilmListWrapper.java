package application.models.films;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import application.models.films.Film;

/***************************************************************************************
 * This class is based on the following source:
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob 
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 ***************************************************************************************/

/**
 * This is a Wrapper Class that enables the reading/writing of Film data to/from the FilmData.xml file. 
 * 
 * @author Joseph Courtley
 *
 */
@XmlRootElement(name = "films")
public class FilmListWrapper {
	
	private List<Film> films;
	
	/**
	 * This is a getter method for the films Java List.
	 * @return List
	 */
	@XmlElement(name = "film")
	public List<Film> getFilms() {
		return films;
	}

	/**
	 * This is a setter method for the films Java List.
	 * @param films
	 */
	public void setFilms(List<Film> films) {
		this.films = films;
	}
}