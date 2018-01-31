package application.views.plan.util;



/**
 * @author josephcourtley
 */
public class DataSaveException extends Exception {

	String str1;

	public DataSaveException(String str2) {
		str1=str2;
	}

	public String toString(){ 
		return ("There was a problem saving this data: "+str1) ;
	}

}
