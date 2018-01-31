package application.models.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/***************************************************************************************
 * This class is based on the following source:
 * Title: JavaFX 8 Tutorial - Part 5: Storing Data as XML
 * Author: Marco Jakob 
 * Date: 12/03/2015
 * Availability: http://code.makery.ch/library/javafx-8-tutorial/part5/
 *
 ***************************************************************************************/

/**
 * Model class for a client. This will be used in conjunction with the ClienWrapper.java class to read/write data to/from the Clients.xml files.
 * 
 *
 * @author David Rudolf
 */
public class Client {

    private final StringProperty UserName;
    private final StringProperty Password;
    private final StringProperty FirstName;
    private final StringProperty LastName;
    private final StringProperty Email;
 
    
    
    
    public Client() {
        this(null, null, null, null, null);
    }
    
/**
 * This is the constructor of the class. It will initialise the attributes when a new instance of this class is made. 
 * @param UserName
 * @param Password
 * @param FirstName
 * @param LastName
 * @param Email
 */
    public Client(String UserName, String Password, String FirstName, String LastName, String Email) {
        this.UserName = new SimpleStringProperty(UserName);
        this.Password = new SimpleStringProperty(Password);
        this.FirstName = new SimpleStringProperty(FirstName);
        this.LastName = new SimpleStringProperty(LastName);
        this.Email = new SimpleStringProperty(Email);
        
        
    }

    /**
     * This is the getter method for the UserName attribute.
     * @return String
     */
    public String getUserName() {
        return UserName.get();
    }

    /**
     * This is the setter method for the UserName attribute.
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
     * This is the getter method for the Password attribute.
     * @return String
     */
    public String getPassword() {
        return Password.get();
    }

    /**
     * This is the setter method for the Password attribute.
     * @param Password
     */
    public void setPassword(String Password) {
        this.Password.set(Password);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty PasswordProperty() {
        return Password;
    }
    
    
    /**
     * This is the getter method for the FirstName attribute.
     * @return String
     */
    public String getFirstName() {
        return FirstName.get();
    }
    /**
     * This is the setter method for the FirtName attribute.
     * @param FirstName
     */
    public void setFirstName(String FirstName) {
        this.FirstName.set(FirstName);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty 
     */
    public StringProperty FirstNameProperty() {
        return FirstName;
    }
    


    /**
     * This is the getter method for the LastName attribute.
     * @return String
     */
    public String getLastName() {
        return LastName.get();
    }

    /**
     * This is the setter method for the LastName attribute.
     * @param LastName
     */
    public void setLastName(String LastName) {
        this.LastName.set(LastName);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty LastNameProperty
     */
    public StringProperty LastNameProperty() {
        return LastName;
    }
    
    
    
    
    /**
     * This is the getter method for the Email attribute.
     * @return String
     */
    public String getEmail() {
        return Email.get();
    }
    /**
     * This is the setter method for the Email attribute.
     * @param Email
     */
    public void setEmail(String Email) {
        this.Email.set(Email);
    }
    /**
     * This method is used in the storage of the attribute.
     * @return StringProperty EmailProperty
     */
    public StringProperty EmailProperty() {
        return Email;
    }
    

    

}