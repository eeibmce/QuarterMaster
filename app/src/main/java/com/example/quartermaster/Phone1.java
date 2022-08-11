package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Scanner;

public class Phone1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone1);
    }
    class Phone {
        private String model, storage, modelNumber, brand, colour, nameOwner;

        public Phone(String model, String storage, String modelNumber, String brand, String colour, String nameOwner) {
            this.model = model;
            this.storage = storage;
            this.modelNumber = modelNumber;
            this.brand = brand;
            this.colour = colour;
            this.nameOwner = nameOwner;

        }


        @Override
        public String toString() {
            return "Phones{" +
                    "Model='" + model + '\'' +
                    ", storage='" + storage + '\'' +
                    ", modelNumber='" + modelNumber + '\'' +
                    ", brand='" + brand + '\'' +
                    ", colour='" + colour + '\'' +
                    ", nameOwner='" + nameOwner + '\'' +
                    '}';
        }

        public String getStorage() {
            return storage;
        }

        public void setStorage(String storage) {
            this.storage = storage;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getModelNumber() {
            return modelNumber;
        }

        public void setModelNumber(String modelNumber) {
            this.modelNumber = modelNumber;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getNameOwner() {
            return nameOwner;
        }

        public void main(String[] args) {
            Scanner in = new Scanner(System.in);

            System.out.println("model=");
            String model = in.nextLine();

            System.out.println("storage=");
            String storage = in.next();

            System.out.println("Model number=");
            String modelNumber = in.next();

            System.out.println("Brand=");
            String brand = in.next();

            System.out.println("Colour=");
            String colour = in.next();

            System.out.println("NameOwner");
            String nameOwner = in.next();

            com.example.quartermaster.Phone iphone = new com.example.quartermaster.Phone(model, storage, modelNumber, brand, colour, nameOwner);
            System.out.print(iphone);
        }
    }}