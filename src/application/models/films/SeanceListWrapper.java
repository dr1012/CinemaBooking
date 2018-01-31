package application.models.films;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/***************************************************************************************
 * This class is based on the following source:
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob 
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 ***************************************************************************************/

/**
 * This is a Wrapper Class that enables the reading/writing of film viewings data to/from the SeanceData.xml file. 
 * 
 * @author Joseph Courtley
 *
 */
@XmlRootElement(name = "seances")
public class SeanceListWrapper {
	
	private List<Seance> seances;
	
	/**
	 * This is a getter method for the viewing (seance) Java List.
	 * @return List
	 */
	@XmlElement(name = "seance")
	public List<Seance> getSeances() {
		return seances;
	}
	
	/**
	 * This is the setter method for the viewing (seance) Java List.
	 * @param seances
	 */
	public void setSeances(List<Seance> seances) {
		this.seances = seances;
	}
}
