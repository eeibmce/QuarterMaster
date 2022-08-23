//package com.example.quartermaster;
//
//class Phone {
//    private final String model;
//    private final String storage;
//    private final String modelNumber;
//    private final String brand;
//    private final String colour;
//    private final String nameOwner;
//
//
//    public Phone(String model, String storage, String modelNumber, String brand, String colour, String nameOwner) {
//        this.model = model;
//        this.storage = storage;
//        this.modelNumber = modelNumber;
//        this.brand = brand;
//        this.colour = colour;
//        this.nameOwner = nameOwner;
//
//    }
//
//
//    @Override
//    public String toString() {
//        return "Phones{" +
//                "Model='" + model + '\'' +
//                ", storage='" + storage + '\'' +
//                ", modelNumber='" + modelNumber + '\'' +
//                ", brand='" + brand + '\'' +
//                ", colour='" + colour + '\'' +
//                ", nameOwner='" + nameOwner + '\'' +
//                '}';
//    }
//
//    public String getStorage() {
//        return storage;
//    }
//
//    public void setStorage(String storage) {
//        this.storage = storage;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public String getModelNumber() {
//        return modelNumber;
//    }
//
//    public void setModelNumber(String modelNumber) {
//        this.modelNumber = modelNumber;
//    }
//
//    public String getColour() {
//        return colour;
//    }
//
//    public void setColour(String colour) {
//        this.colour = colour;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    public String getNameOwner() {
//        return nameOwner;
//    }
//
//    public void setNameOwner(String nameOwner) {
//        this.nameOwner = nameOwner;
//    }
//    public  static void main(String [] args){
//        Scanner in = new Scanner(System.in);
//
//        System.out.println("model=");
//        String model = in.nextLine();
//
//        System.out.println("storage=");
//        String storage= in.next ();
//
//        System.out.println("Model number=");
//        String modelNumber=in.next();
//
//        System.out.println("Brand=");
//        String brand=in.next();
//
//        System.out.println("Colour=");
//        String colour=in.next();
//
//        System.out.println("NameOwner");
//        String nameOwner=in.next();
//
//        Phone iphone = new Phone(model,storage, modelNumber, brand, colour, nameOwner);
//        System.out.print(iphone);
//    }
//}