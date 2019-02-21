/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.*;

/**
 *
 * @author Ryan
 */
public class Inventory {
    
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    
//    void initialize(){
//    allParts.addListener((v, oldValue, newValue) -> );
//    }
    
    public void addProduct(Product z){
        products.add(z);
    }
    
    public boolean removeProduct(int z){
        boolean result;
        try{
            products.remove(z);
            result = true;
        }catch(IndexOutOfBoundsException e){
            System.out.println("Product " + z + " is not able to be removed.");
            result = false;
        }
        return result;
    }
    
    public Product lookupProduct(int z){
        return products.get(z);
    }
    
    public void updateProduct(int z){
        Product update = products.get(z);
        products.set(z, update);
    }
    
    public void addPart(Part z){
        allParts.add(z);
    }
    
    public boolean deletePart(Part z){
        boolean result;
        try{
            allParts.remove(z);
            result = true;
        }catch(IndexOutOfBoundsException e){
            System.out.println("Part " + z + " is not able to be removed.");
            result = false;
        }
        return result;
    }
    
    public Part lookupPart(int z){
        return allParts.get(z);
    }
    
    public void updatePart(int z){
        Part update = allParts.get(z);
        allParts.set(z, update);
    }
   
}
