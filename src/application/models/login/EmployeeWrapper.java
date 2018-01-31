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
 * This is a Wrapper Class that enables the reading/writing of Employee data to/from the Employees.xml file. 
 * 
 * @author David Rudolf
 *
 */
@XmlRootElement(name = "employees")
public class EmployeeWrapper {
	
	private List<Employee> employees;
	
	/**
	 * This is a getter method for the employees Java List.
	 * @return List
	 */
	@XmlElement(name = "employee")
	public List<Employee> getEmployees() {
		return employees;
	}
	
	/**
	 * This is a setter method for the employees Java List.
	 * @param employees
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}