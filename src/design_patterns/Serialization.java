package design_patterns;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;
import models.Room;
import models.RoomCategory;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class works as a Singleton, which serialize data
 * Thanks to this class our program can access needed data anywhere without passing them into controllers constructors
 * Inspiration for Singleton https://www.geeksforgeeks.org/singleton-class-java/
 * Inspiration for serialization from https://www.tutorialspoint.com/java/java_serialization.htm
 */
public class Serialization {
    private static final Logger LOGGER = Logger.getLogger(Serialization.class.getName());

    private static Serialization INSTANCE = null;

    // NOTE: Can't serialize observable list, so program converts them into arraylists
    // and store them as attributes in one object
    private static class ObjectForSerialization implements Serializable {
        ArrayList<Customer> arrayListCustomers;
        ArrayList<RoomCategory> arrayListCategories;
    }

    // needed lists for program
    private ObservableList<Customer> allCustomers;
    private ObservableList<RoomCategory> allCategories;

    // when unique Serialization object is created, program deserialize it's data
    private Serialization() {
        try (FileInputStream fileIn = new FileInputStream("booking_data.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn);)
        {
            LOGGER.info("Getting serialized data from /booking_data.ser");
            ObjectForSerialization objectForSerialization = (ObjectForSerialization) in.readObject();

            // cast arraylists to observable lists
            // casting taken from https://stackoverflow.com/questions/43162261/java-lang-classcastexception-java-util-arraylist-cannot-be-cast-to-javafx-colle
            this.allCustomers = FXCollections.observableArrayList(objectForSerialization.arrayListCustomers);
            this.allCategories = FXCollections.observableArrayList(objectForSerialization.arrayListCategories);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            // if there was an exception, then create new observable list for program
            LOGGER.info("Creating new observable lists\n" +
                        "Something might went wrong\n" +
                        "or there wasn't any data in file booking_data.ser,\n" +
                        "which is used for serialization."
            );
            this.allCustomers = FXCollections.observableArrayList();
            this.allCategories = FXCollections.observableArrayList();
        }
    }

    // this method makes sure, that there is only one instance of this class in whole program
    public static Serialization getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Serialization();
        }
        return INSTANCE;
    }

    // this method stores newly created customer and serialize whole object of using data for this program
    public void addCustomerAndSerialize(Customer newCustomer) {
        this.getAllCustomers().add(newCustomer);
        this.serializeData();
    }

    // this method stores newly created room category and serialize whole object of using data for this program
    public void addRoomCategoryAndSerialize(RoomCategory newRoomCategory) {
        this.getAllCategories().add(newRoomCategory);
        this.serializeData();
    }

    public void serializeData() {
        // get serialized data from /booking_data.ser
        try (FileOutputStream fileOut = new FileOutputStream("booking_data.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut);)
        {
            LOGGER.info("Serializing data to /booking_data.ser");

            ObjectForSerialization objectForSerialization = new ObjectForSerialization();
            // casting taken from https://stackoverflow.com/questions/39872697/how-to-convert-an-observable-list-to-an-array-list-java
            objectForSerialization.arrayListCustomers = new ArrayList<>(this.getAllCustomers());
            objectForSerialization.arrayListCategories = new ArrayList<>(this.getAllCategories());

            out.writeObject(objectForSerialization);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public ObservableList<RoomCategory> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(ObservableList<RoomCategory> allCategories) {
        this.allCategories = allCategories;
    }
}
