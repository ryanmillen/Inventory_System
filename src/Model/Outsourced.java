/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Ryan
 */
public class Outsourced extends Part {
    
    String companyName;
    
    public Outsourced(){
        setPartID();
        setName("");
        setInStock(0);
        setPrice(0);
        setMax(0);
        setMin(0);
        setCompanyName("");
    }
    
    public Outsourced(String name, int inventory, double price, int max, 
                    int min, String companyName) {
        setPartID();
        setName(name);
        setInStock(inventory);
        setPrice(price);
        setMax(max);
        setMin(min);
        setCompanyName(companyName);
    }
    
    private void setCompanyName(String z){
        companyName = z;
    }
    
    public String getCompanyName(){
        return companyName;
    }
}
