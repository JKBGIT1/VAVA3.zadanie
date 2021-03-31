package design_patterns;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;
import models.RoomCategory;

/*
 * This class works as a Singleton, which serialize data
 * Thanks to this class our program can access needed data anywhere without passing them into controllers constructors
 */
public class Serialization {
    private static Serialization INSTANCE = null;
    // needed lists for program
    private ObservableList<Customer> allCustomers;
    private ObservableList<RoomCategory> allCategories;

    private Serialization() {
        this.allCustomers = FXCollections.observableArrayList();
        this.allCategories = FXCollections.observableArrayList();
    }

    // this method makes sure, that there is only one instance of this class in whole program
    public static Serialization getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Serialization();
        }

        return INSTANCE;
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
