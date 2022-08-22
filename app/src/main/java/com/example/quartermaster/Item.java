package com.example.quartermaster;

public class Item {
    private final String itemOwnerEmail;
    private final String itemType;

    //Declaring details of item
    public Item(String itemOwnerEmail, String itemType) {
        this.itemOwnerEmail = itemOwnerEmail;
        this.itemType = itemType;
    }
    public String getItemOwnerEmail() {
        return itemOwnerEmail;
    }
    public String getItemType() {
        return itemType;
    }
}
