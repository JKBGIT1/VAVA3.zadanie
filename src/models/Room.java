package models;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Room implements Serializable {
    private String label;
    private String note;
    private String category;
    private ArrayList<Image> gallery;
    private Date takenFrom;
    private Date takenTo;
    private boolean isFree;

    public Room(String label, String note, String category, ArrayList<Image> gallery) {
        this.label = label;
        this.note = note;
        this.category = category;
        this.gallery = gallery;
        this.takenFrom = null;
        this.takenTo = null;
        this.isFree = false;
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

    public ArrayList<Image> getGallery() {
        return gallery;
    }

    public void setGallery(ArrayList<Image> gallery) {
        this.gallery = gallery;
    }

    public Date getTakenFrom() {
        return takenFrom;
    }

    public void setTakenFrom(Date takenFrom) {
        this.takenFrom = takenFrom;
    }

    public Date getTakenTo() {
        return takenTo;
    }

    public void setTakenTo(Date takenTo) {
        this.takenTo = takenTo;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
