package models;

import javafx.collections.ObservableList;

public class Customer {
    private String firstName;
    private String secondName;
    private Reservation reservation;
    private ObservableList<Accommodation> accommodations;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public ObservableList<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(ObservableList<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }
}

