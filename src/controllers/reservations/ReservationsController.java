package controllers.reservations;

import controllers.HomepageController;
import javafx.collections.ObservableList;
import models.Customer;

public class ReservationsController extends HomepageController {
    public ReservationsController(ObservableList<Customer> allCustomers) {
        super(allCustomers);
    }
}
