package controllers.rooms;

import controllers.HomepageController;
import javafx.collections.ObservableList;
import models.Customer;

public class RoomsController extends HomepageController {
    public RoomsController(ObservableList<Customer> allCustomers) {
        super(allCustomers);
    }
}
