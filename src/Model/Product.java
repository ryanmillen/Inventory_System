/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ryan
 */
public class Product {
    
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    static int counter = 0;
    int productID;
    String name;
    double price;
    int inStock /*= 0*/;
    int min;
    int max;
    
    public Product(){
        setProductID();
        setName("");
        setPrice(0);
        setInStock(0);
        setMin(0);
        setMax(0);
    }
    
    public Product(String name, double price, int inStock, int min, int max){
        setProductID();
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
    }
    
    public void setName(String name){
        this.name = name;
    };
    
    public String getName(){
        return name;
    };
    
    public void setPrice(double price){
        this.price = price;
    };
    
    public double getPrice(){
        return price;
    };
    
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
    
    public void addAssociatedPart(Part z){
        associatedParts.add(z);
    }
    
    public boolean removeAssociatedPart(int z){
        boolean result;
        try{
            associatedParts.remove(z);
            result = true;
        }catch(IndexOutOfBoundsException e){
            System.out.println("Part " + z + " is not associated with this product");
            result = false;
        }
        return result;
    }
    
    public Part lookupAssociatedPart(int z){
        return associatedParts.get(z);
    }
    
    public void setProductID(){
        productID = counter + 1;
        counter++;
    }
    
    public int getProductID(){
        return productID;
    }
}
