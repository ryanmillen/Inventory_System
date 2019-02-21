/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Ryan
 */
public abstract class Part {
   
    static int counter = 0;
    int partID;
    SimpleStringProperty name = new SimpleStringProperty();
    double price;
    int inStock;
    int min;
    int max;
    
    public void setName(String z){
        name.setValue(z);
    }
    
    public String getName(){
        return name.get();
    }
    
    public void setPrice(double z){
        price = z;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setInStock(int z){
        inStock = z;
    }
    
    public int getInStock(){
        return inStock;
    }
    
    public void setMin(int z){
        min = z;
    }
    
    public int getMin(){
        return min;
    }
    
    public void setMax(int z){
        max = z;
    }
    
    public int getMax(){
        return max;
    }
    
    public void setPartID(){
        partID = counter + 1;
        counter++;
    }
    
    public int getPartID(){
        return partID;
    }
}
