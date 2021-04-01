package models;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {
    private Date dateFrom;
    private Date dateTo;
    private double price;
    private Room reservedRoom;

    public Reservation(Date dateFrom, Date dateTo, double price, Room reservedRoom) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.price = price;
        this.reservedRoom = reservedRoom;
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

    public Room getReservedRoom() {
        return reservedRoom;
    }

    public void setReservedRoom(Room reservedRoom) {
        this.reservedRoom = reservedRoom;
    }

    public static double calculateDiscount(int days, double price) {
        double calculatePrice = days * price;

        if (days > 10) { // 20% discount for more than 10 days accommodation with reservation
            calculatePrice *= 0.8;
        } else if (days > 7) { // 15% discount for more than 7 days accommodation with reservation
            calculatePrice *= 0.85;
        } else if (days > 5) { // 10% discount for more than 5 days accommodation with reservation
            calculatePrice *= 0.9;
        } else if (days > 3) { // 5% discount for more than 3 days accommodation with reservation
            calculatePrice *= 0.95;
        }

        // round price on two decimals
        // Taken from https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
        calculatePrice = calculatePrice * 100;
        calculatePrice = Math.round(calculatePrice);
        calculatePrice = calculatePrice /100;
        
        return calculatePrice;
    }
}
