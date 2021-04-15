package controllers.customers;

import controllers.HomepageController;
import design_patterns.Serialization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ReservationAccommodationController extends HomepageController {

    private static final Logger LOGGER = Logger.getLogger(ReservationAccommodationController.class.getName());

    @FXML
    private TableView<Room> roomsTableView;
    @FXML
    private TableColumn<Room, String> roomLabelCol, categoryCol;
    @FXML
    private TableColumn<Room, Double> priceCol;
    @FXML
    private TableColumn<Room, String> takenFromCol, takenToCol;
    @FXML
    private TableColumn<Room, Boolean> freeCol;
    @FXML
    private TextField tfDateFrom, tfDateTo;

    private final Customer selectedCustomer;

    public ReservationAccommodationController(Customer customer) {
        this.selectedCustomer = customer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (roomsTableView != null) {
            // map columns in table to room attributes and methods
            mapRooms(roomLabelCol, categoryCol, priceCol, takenFromCol, takenToCol, freeCol);
            // create observable list for all rooms, which will be displayed in roomsTableView
            ObservableList<Room> rooms = FXCollections.observableArrayList();
            // get observable list of all room categories in hotel
            ObservableList<RoomCategory> roomsCategory = Serialization.getInstance().getAllCategories();

            // get all rooms from each category and add them to rooms observable list
            for (RoomCategory roomCategory : roomsCategory) {
                rooms.addAll(roomCategory.getRoomsInCategory());
            }

            // display rooms observable list in TableView
            roomsTableView.setItems(rooms);
        }
    }

    // when user click on Reserve button, this method will make a Reservation
    public void makeReservation(MouseEvent event) {
        this.makeAccommodationOrReservation(event, true);
    }

    // when user click on Accommodate button, this method will make an Accommodation
    public void makeAccommodation(MouseEvent event) { this.makeAccommodationOrReservation(event, false); }

    public void makeAccommodationOrReservation(MouseEvent event, boolean reservationFlag) {
        // if user didn't select any room for accommodation or reservation show error popup
        if (roomsTableView.getSelectionModel().getSelectedItem() == null) {
            LOGGER.info("User didn't selected any room for reservation or accommodation process.");
            this.showErrorPopUp(
                    "Select room",
                    "You have to select room, before reservation or accommodation."
            );
        } else {
            // get selected room from TableView
            Room selectedRoom = roomsTableView.getSelectionModel().getSelectedItem();
            // check if room isn't occupied
            if (!this.checkIfRoomIsFree(selectedRoom)) {
                return;
            }

            try {
                // get entered dates from TextFields
                String dateFromString = tfDateFrom.getText();
                String dateToString = tfDateTo.getText();

                // check if TextFields with dates are filled
                if (!this.checkIfDatesAreFilled(dateFromString, dateToString)) {
                    return;
                }

                // convert strings to dates
                Date dateFrom = this.convertStringToDate(dateFromString);
                Date dateTo = this.convertStringToDate(dateToString);

                // Taken from https://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-java
                // calculate difference between days in milliseconds
                long diff = dateTo.getTime() - dateFrom.getTime();
                // convert milliseconds to days
                int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                // dateTo can't be before dateFrom
                if (days < 0) {
                    LOGGER.info("User entered wrong dates, because dateTo is latter than dateFrom.");
                    this.showErrorPopUp(
                            "Wrong dates",
                            "Date from can't be latter than Date to."
                    );
                    return;
                }

                // If reservationFlag is true create new reservation
                // otherwise create new accommodation
                if (reservationFlag) {
                    if (!this.createReservation(selectedRoom, days, dateFrom, dateTo)) {
                        return;
                    }
                } else {
                    if (!this.createAccommodation(selectedRoom, days, dateFrom, dateTo)) {
                        return;
                    }
                }
                // after reservation or accommodation go back to customers Scene
                this.customersScene(event);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("Something went wrong during reservation, here is the exception message: " + e.getMessage());
                this.showErrorPopUp(
                        "Wrong date format",
                        "Try another date format for example: dd.mm.yyyy"
                );
            }
        }
    }

    public boolean checkIfRoomIsFree(Room room) {
        if (!room.getIsFree()) {
            LOGGER.info("User selected room, which isn't free.");
            this.showErrorPopUp(
                    "Select free room",
                    "You have to select room, which isn't occupied."
            );

            return false;
        }

        return true;
    }

    public boolean checkIfDatesAreFilled(String dateFromString, String dateToString) {
        if (dateFromString.equals("") || dateToString.equals("")) {
            LOGGER.info("User didn't fill TextFields for dates.");
            this.showErrorPopUp(
                    "Missing dates",
                    "Enter start of the accommodation date and end of the accommodation date."
            );

            return false;
        }

        return true;
    }

    public boolean createReservation(Room selectedRoom, int days, Date dateFrom, Date dateTo) {
        // calculate price with discount for reservation,
        // based on number of accommodated days and price for night in selected room
        LOGGER.info("Creating reservation.");
        double totalPrice = days * selectedRoom.getPrice();
        double priceWithDiscount = Reservation.calculateDiscount(days, selectedRoom.getPrice());

        // convert java.util.Date to understandable format
        // because they will be displayed in confirmation window
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String dateFromFormatted = formatter.format(dateFrom);
        String dateToFormatted = formatter.format(dateTo);

        // create reservation only if user confirms it
        if (!this.showConfirmationPopUp(
                "Confirm reservation from " + dateFromFormatted + " to " + dateToFormatted,
                "Do you want to confirm reservation with discounted price " + priceWithDiscount +
                        ", where total price for accommodation was " + totalPrice)
        ) {
            LOGGER.info("User didn't confirm reservation");
            return false; // user doesn't confirm reservation
        }

        // Change room status from free to occupied and set start of accommodation and end of accommodation
        this.changeRoomData(selectedRoom, dateFrom, dateTo);
        // create new reservation
        Reservation reservation = new Reservation(
                dateFrom,
                dateTo,
                priceWithDiscount,
                selectedRoom
        );
        // set reservation to selected customer
        LOGGER.info("Reservation was created.");
        this.selectedCustomer.setReservation(reservation);
        // serialize changed data
        Serialization.getInstance().serializeData();
        this.showSuccessPopUp(
                "Success",
                "Reservation was successfully created."
        );
        return true;
    }

    public boolean createAccommodation(Room selectedRoom, int days, Date dateFrom, Date dateTo) {
        // calculate price with discount for accommodation,
        // based on number of accommodated days and price for night in selected room
        LOGGER.info("Creating accommodation.");
        double totalPrice = days * selectedRoom.getPrice();
        double priceWithDiscount = Accommodation.calculateDiscount(days, selectedRoom.getPrice());

        // convert java.util.Date to understandable format
        // because they will be displayed in confirmation window
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String dateFromFormatted = formatter.format(dateFrom);
        String dateToFormatted = formatter.format(dateTo);

        // create accommodation only if user confirms it
        if (!this.showConfirmationPopUp(
                "Confirm accommodation from " + dateFromFormatted + " to " + dateToFormatted,
                "Do you want to confirm accommodation with discount " + priceWithDiscount +
                        ", where total price for accommodation was " + totalPrice)
        ) {
            LOGGER.info("User didn't confirm accommodation");
            return false; // user doesn't confirm accommodation
        }

        // Change room status from free to occupied and set start of accommodation and end of accommodation
        this.changeRoomData(selectedRoom, dateFrom, dateTo);
        // create new accommodation
        Accommodation accommodation = new Accommodation(
                dateFrom,
                dateTo,
                priceWithDiscount,
                selectedRoom
        );
        // add accommodation to history of all accommodation for room
        selectedRoom.getHistoryAccommodations().add(accommodation);
        // remove selected customer from all customers observable list
        Serialization.getInstance().getAllCustomers().remove(this.selectedCustomer);
        // set current accommodation to selected customer
        this.selectedCustomer.setCurrentAccommodation(accommodation);
        // add newly created accommodation to customer's history of accommodations
        this.selectedCustomer.getAccommodations().add(accommodation);
        // serialize selectedCustomer with changed data
        Serialization.getInstance().addCustomerAndSerialize(this.selectedCustomer);
        this.showSuccessPopUp(
                "Success",
                "Accommodation was successfully created."
        );
        return true;
    }

    public void changeRoomData(Room selectedRoom, Date dateFrom, Date dateTo) {
        // room is reserved or already occupied so change isFree attribute
        selectedRoom.setIsFree(false);
        selectedRoom.setTakenFrom(dateFrom);
        selectedRoom.setTakenTo(dateTo);
    }
}
