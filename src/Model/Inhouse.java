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
public class Inhouse extends Part {
    
    int machineID;
    
    public Inhouse(){
        setPartID();
        setName("");
        setInStock(0);
        setPrice(0);
        setMax(0);
        setMin(0);
        setMachineID(0);
        
    }

    public Inhouse(String name, int inventory, double price, int max, 
                    int min, int machineID) {
        setPartID();
        setName(name);
        setInStock(inventory);
        setPrice(price);
        setMax(max);
        setMin(min);
        setMachineID(machineID);
    }
    
    public void setMachineID(int z){
        machineID = z;
    }
    
    public int getMachineID(){
        return machineID;
    }
}
