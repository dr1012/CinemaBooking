package application.models.login;

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
 * This is a Wrapper Class that enables the reading/writing of Client data to/from the Clients.xml file. 
 * 
 * @author David Rudolf
 *
 */
@XmlRootElement(name = "clients")
public class ClientWrapper {
	
	private List<Client> clients;
	
	/**
	 * This is a getter method for the clients Java List.
	 * @return List
	 */
	@XmlElement(name = "client")
	public  List<Client> getClients() {
		return clients;
	}
	
	/**
	 * This is a setter method for the clients Java List.
	 * @param clients
	 */
	public  void setClients(List<Client> clients) {
		this.clients = clients;
	}
}