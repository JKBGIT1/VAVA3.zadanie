package models;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Room implements Serializable {
    private String label;
    private String note;
    private String category;
    private double price;
    private ArrayList<byte[]> gallery;
    private Date takenFrom;
    private Date takenTo;
    private boolean isFree;
    private ArrayList<Accommodation> historyAccommodations;

    public Room(String label, String note, String category, double price, ArrayList<byte[]> gallery) {
        this.label = label;
        this.note = note;
        this.category = category;
        this.price = price;
        this.gallery = gallery;
        this.takenFrom = null;
        this.takenTo = null;
        this.isFree = true;
        this.historyAccommodations = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<byte[]> getGallery() {
        return gallery;
    }

    public void setGallery(ArrayList<byte[]> gallery) {
        this.gallery = gallery;
    }

    public Date getTakenFrom() {
        return takenFrom;
    }

    public String getTakenFromString() {
        // program can display takenFrom date only if room isn't free
        if (!this.isFree) {
            // Inspiration from https://www.javatpoint.com/java-date-to-string
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return dateFormat.format(this.takenFrom);
        }
        // if room is free, program display None in TableColumn Taken From
        return "None";
    }

    public void setTakenFrom(Date takenFrom) {
        this.takenFrom = takenFrom;
    }

    public Date getTakenTo() {
        return takenTo;
    }

    public String getTakenToString() {
        // program can display takenTo date only if room isn't free
        if (!this.isFree) {
            // Inspiration from https://www.javatpoint.com/java-date-to-string
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return dateFormat.format(this.takenTo);
        }
        // if room is free, program display None in TableColumn Taken To
        return "None";
    }

    public void setTakenTo(Date takenTo) {
        this.takenTo = takenTo;
    }

    public boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(boolean free) {
        isFree = free;
    }

    public ArrayList<Accommodation> getHistoryAccommodations() {
        return historyAccommodations;
    }

    public void setHistoryAccommodations(ArrayList<Accommodation> historyAccommodations) {
        this.historyAccommodations = historyAccommodations;
    }
}
