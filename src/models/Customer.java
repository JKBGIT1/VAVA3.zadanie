package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private Reservation reservation;
    private Accommodation currentAccommodation;
    private ArrayList<Accommodation> accommodations;

    public Customer(String firstName, String lastName, String identificationNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationNumber = identificationNumber;
        this.reservation = null;
        this.currentAccommodation = null;
        this.accommodations = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        } else if (this.currentAccommodation.getReservedRoom() == null) {
            return "None";
        }

        // otherwise display room label
        return this.currentAccommodation.getReservedRoom().getLabel();
    }

    public ArrayList<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(ArrayList<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }
}

