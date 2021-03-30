package models;

import javafx.collections.ObservableList;

import java.util.Date;

public class Accommodation {
    private Customer customer;
    private Reservation reservation;
    private Room room;
    private Date dateFrom;
    private Date dateTo;
    private double price;
    private boolean isPayed;
    private ObservableList<UsedService> usedServices;
}
