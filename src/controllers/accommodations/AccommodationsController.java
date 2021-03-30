package controllers.accommodations;

import controllers.HomepageController;
import javafx.collections.ObservableList;
import models.Customer;

public class AccommodationsController extends HomepageController {
    public AccommodationsController(ObservableList<Customer> allCustomers) {
        super(allCustomers);
    }
}
