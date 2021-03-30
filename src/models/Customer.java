package models;

import javafx.collections.ObservableList;

public class Customer {
    private String firstName;
    private String secondName;
    private String identificationNumber;
    private Reservation reservation;
    private Accommodation currentAccommodation;
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

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getRoomLabelFromReservation() {
        // If customer doesn't have reservation display None
        if (this.reservation == null) {
            return "None";
        } else if (this.reservation.getReservedRoom() == null) {
            return "None";
        }

        // otherwise display room labels
        return this.reservation.getReservedRoom().getLabel();
    }

    public Accommodation getCurrentAccommodation() {
        return currentAccommodation;
    }

    public void setCurrentAccommodation(Accommodation currentAccommodation) {
        this.currentAccommodation = currentAccommodation;
    }

    public String getRoomLabelFromCurrentAccommodation() {
        // If customer isn't accommodated, then display None
        if (this.currentAccommodation == null) {
            return "None";
        } else if (this.currentAccommodation.getRoom() == null) {
            return "None";
        }

        // otherwise display room label
        return this.currentAccommodation.getRoom().getLabel();
    }

    public ObservableList<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(ObservableList<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }
}

