package models;

import javafx.collections.ObservableList;

import java.util.Date;

public class Accommodation {
    private Room room;
    private Date dateFrom;
    private Date dateTo;
    private double price;
    private boolean isPayed;
    private ObservableList<UsedService> usedServices;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public ObservableList<UsedService> getUsedServices() {
        return usedServices;
    }

    public void setUsedServices(ObservableList<UsedService> usedServices) {
        this.usedServices = usedServices;
    }
}