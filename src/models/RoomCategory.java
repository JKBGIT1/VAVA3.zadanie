package models;

import java.util.ArrayList;

public class RoomCategory {
    private String categoryName;
    private String description;
    private double price;
    private ArrayList<Room> roomsInCategory;

    public RoomCategory(String categoryName, String description, double price) {
        this.categoryName = categoryName;
        this.description = description;
        this.price = price;
        this.roomsInCategory = new ArrayList<>();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Room> getRoomsInCategory() {
        return roomsInCategory;
    }

    public void setRoomsInCategory(ArrayList<Room> roomsInCategory) {
        this.roomsInCategory = roomsInCategory;
    }
}
