package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Accommodation implements Serializable {
    private Room reservedRoom;
    private Date dateFrom;
    private Date dateTo;
    private double price;
    private Payment payment;
    private ArrayList<UsedService> usedServices;

    public Accommodation(Date dateFrom, Date dateTo, double price, Room reservedRoom) {
        this.reservedRoom = reservedRoom;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.price = price;
        this.payment = null;
        this.usedServices = new ArrayList<>();
    }

    public Room getReservedRoom() {
        return reservedRoom;
    }

    public void setReservedRoom(Room reservedRoom) {
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

    public String getDateToAsString() {
        // Taken from https://www.javatpoint.com/java-simpledateformat
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(this.dateTo);
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ArrayList<UsedService> getUsedServices() {
        return usedServices;
    }

    public void setUsedServices(ArrayList<UsedService> usedServices) {
        this.usedServices = usedServices;
    }

    public static double calculateDiscount(int days, double price) {
        double calculatePrice = days * price;

        if (days > 10) { // 25% discount for more than 3 days accommodation without reservation
            return calculatePrice * 0.75;
        } else if (days > 7) { // 20% discount for more than 3 days accommodation without reservation
            return calculatePrice * 0.80;
        } else if (days > 5) { // 15% discount for more than 3 days accommodation without reservation
            return calculatePrice * 0.85;
        } else if (days > 3) { // 10% discount for more than 3 days accommodation without reservation
            return calculatePrice * 0.90;
        } else if (days > 2) { // 5% discount for more than 3 days accommodation without reservation
            return calculatePrice * 0.95;
        }

        // round price on two decimals
        // Taken from https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
        calculatePrice = calculatePrice * 100;
        calculatePrice = Math.round(calculatePrice);
        calculatePrice = calculatePrice / 100;

        return calculatePrice;
    }
}
