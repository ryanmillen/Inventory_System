package View_Controller;

import Model.Inhouse;
import Model.Inventory;
import static Model.Inventory.allParts;
import Model.Part;
import static View_Controller.MainScreenController.modified;
import static View_Controller.MainScreenController.partsStage;
import static View_Controller.MainScreenController.selected;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import ryanmillen_inventorysystem.RyanMillen_InventorySystem;

public class AddModifyInhousePartController {
    
    final ToggleGroup radios = new ToggleGroup();

    @FXML
    private AnchorPane AddModifyInhousePart;

    @FXML
    private RadioButton inhouseRadio;

    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField inventoryField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField maxField;

    @FXML
    private TextField minField;

    @FXML
    private TextField machineIdField;
    
    public void initialize() throws ClassCastException, IOException {
        inhouseRadio.setToggleGroup(radios);
        inhouseRadio.setSelected(true);
        outsourcedRadio.setToggleGroup(radios);
        if(selected != null){
            idField.setText(Integer.toString(selected.getPartID()));
            nameField.setText(selected.getName());
            inventoryField.setText(Integer.toString(selected.getInStock()));
            priceField.setText(Double.toString(selected.getPrice()));
            maxField.setText(Integer.toString(selected.getMax()));
            minField.setText(Integer.toString(selected.getMin()));
            try{
                Inhouse mID = (Inhouse) selected;
                machineIdField.setText(Integer.toString(mID.getMachineID()));
            }catch (ClassCastException e){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RyanMillen_InventorySystem.class.getResource(
                    "/View_Controller/AddModifyOutsourcedPart.fxml"));
                Scene scene = new Scene(loader.load());
                partsStage.setScene(scene);
                partsStage.show();
            }
        }
    }
    
    @FXML
    void inhousePart(ActionEvent event) {
        
    }

    @FXML
    void outsourcedPart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RyanMillen_InventorySystem.class.getResource(
            "/View_Controller/AddModifyOutsourcedPart.fxml"));
        Scene scene = new Scene(loader.load());
        partsStage.setScene(scene);
        partsStage.show();
    }

    
    @FXML
    void cancelInhousePart(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK ){
            partsStage.close();
        }else{
            alert.close();
        }
        
        
    }
    
    @FXML
    void saveInhousePart(ActionEvent event) throws NumberFormatException {
        //gets data from fields 
        try {
        String id = idField.getText();
        String name = nameField.getText();
        String inv = inventoryField.getText();
        String price = priceField.getText();
        String max = maxField.getText();
        String min = minField.getText();
        String machineID = machineIdField.getText();

        //checks to see if part already exists
        for(Part p: allParts){
            String name2 = p.getName();
            String inv2 = Integer.toString(p.getInStock());
            String price2 = Double.toString(p.getPrice());
            String max2 = Integer.toString(p.getMax());
            String min2 = Integer.toString(p.getMin());
            if(name.equals(name2)){
                if(inv.equals(inv2)){
                    if(price.equals(price2)){
                        if(max.equals(max2)){
                            if(min.equals(min2)){
                                try{
                                    Inhouse p2 = (Inhouse) p;
                                    String machineID2 = Integer.toString(p2.getMachineID());
                                        if(machineID.equals(machineID2)){
                                            Alert alert = new Alert(AlertType.INFORMATION);
                                            alert.setTitle("Part exists");
                                            alert.setHeaderText("This part already exists. To save a new part or modify an existing part"
                                                    + " change one of the fields.");
                                            alert.showAndWait();
                                            return;
                                        }
                                }catch(ClassCastException e){}
                            }
                        }
                    }
                }
            }
        }
        //inventory check
        int _max = Integer.parseInt(maxField.getText());
        int _min = Integer.parseInt(minField.getText());
        int _inv = Integer.parseInt(inventoryField.getText());
        boolean x =(_inv <= _max) & (_inv >= _min) & (_max>_min);
        if(x == false){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Min must be less than Max.\nInv must be between Min and Max.");
            alert.showAndWait();  
            return;
        }
        
        //modifies existing part
        if(modified == true){
            int location = allParts.indexOf(selected);
            allParts.remove(location);
            selected.setName(name);
            selected.setInStock(Integer.parseInt(inv));
            selected.setPrice(Double.parseDouble(price));
            selected.setMax(Integer.parseInt(max));
            selected.setMin(Integer.parseInt(max));
            try{
            Inhouse selected2 = (Inhouse) selected;
            selected2.setMachineID(Integer.parseInt(machineID));
            allParts.add(selected2);
            System.out.println("modified part");
            }catch(ClassCastException e){
            }
        }else{
        //adds new part to inventory
            Model.Inventory.allParts.add(new Inhouse(name,Integer.parseInt(inv),
                 Double.parseDouble(price),Integer.parseInt(max),Integer.parseInt(min),
                         Integer.parseInt(machineID)));
        }
            //alerts user if all the fields are not filled out
        }catch (NumberFormatException e){
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("ERROR");
             alert.setHeaderText("All text fields must be filled out and in correct format.");
             alert.showAndWait();
             return;
        }
        //resets all the text fields before closing the window
        nameField.setText("");
        inventoryField.setText("");
        priceField.setText("");
        maxField.setText("");
        minField.setText("");
        machineIdField.setText("");

        partsStage.close();
    }
   
}
