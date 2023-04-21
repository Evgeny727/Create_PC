package com.example.createpc.fragments.dataclasses;

public class PcCardData {
    private static String currency_icon;
    private int id;
    private String typeName;
    private String pcPartName;
    private String pathToImage;
    private int price;
    private String[] specificationNames;
    private String[] specificationValues;

    //Default constructor
    public PcCardData() {
        id = 0;
        typeName = "";
        pcPartName = "";
        pathToImage = "";
        price = 0;
        specificationNames = new String[5];
        specificationValues = new String[5];
    }

    public PcCardData(int id, String typeName, String pcPartName, String pathToImage, int price, String[] specificationNames, String[] specificationValues) {
        this.id = id;
        this.typeName = typeName;
        this.pcPartName = pcPartName;
        this.pathToImage = pathToImage;
        this.price = price;
        this.specificationNames = specificationNames;
        this.specificationValues = specificationValues;
    }

    public void setDefaultValues() {
        new PcCardData();
    }

    public static String getCurrency_icon() {
        return currency_icon;
    }

    public static void setCurrency_icon(String currency_icon) {
        PcCardData.currency_icon = currency_icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPcPartName() {
        return pcPartName;
    }

    public void setPcPartName(String pcPartName) {
        this.pcPartName = pcPartName;
    }

    public String getCardName() {
        if (typeName.equals("")) {
            return getTypeName();
        }
        else return getTypeName() + " " + getPcPartName();
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStringPrice() {
        return getPrice() + " " + getCurrency_icon();
    }

    public String[] getSpecificationNames() {
        return specificationNames;
    }

    public void setSpecificationNames(String[] specificationNames) {
        this.specificationNames = specificationNames;
    }

    public String[] getSpecificationValues() {
        return specificationValues;
    }

    public void setSpecificationValues(String[] specificationValues) {
        this.specificationValues = specificationValues;
    }
}
