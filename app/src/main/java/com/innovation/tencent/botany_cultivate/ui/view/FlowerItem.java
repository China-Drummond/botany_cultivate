package com.innovation.tencent.botany_cultivate.ui.view;

/**
 * Created by Drummond on 2015/10/27.
 */
public class FlowerItem {

    private String name;
    private double price;
    private String area;
    private int flowerImageID;
    private int accountImageID;

    public FlowerItem(String name,double price,String area,int flowerImageID,int accountImageID){
        this.name = name;
        this.price = price;
        this.area = area;
        this.flowerImageID = flowerImageID;
        this.accountImageID = accountImageID;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public String getArea(){
        return area;
    }

    public int getFlowerImageID(){
        return flowerImageID;
    }

    public int getAccountImageID(){
        return accountImageID;
    }
}
