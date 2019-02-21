package View_Controller;

import Model.Inhouse;
import static Model.Inventory.allParts;
import static Model.Inventory.products;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import ryanmillen_inventorysystem.RyanMillen_InventorySystem;
import static ryanmillen_inventorysystem.RyanMillen_InventorySystem.mainStage;
import View_Controller.AddModifyInhousePartController.*;
import java.awt.Button;
import java.util.Optional;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SingleSelectionModel;

public class MainScreenController {
    
    ObservableList<Part> result = FXCollections.observableArrayList();
    
    ObservableList<Product> result2 = FXCollections.observableArrayList();

    @FXML
    private AnchorPane MainScreen;
    
    @FXML
    private TextField partsSearchField;
    
    @FXML
    public TableView<Part> partsTable;
   
    @FXML
    TableColumn<Part, Integer> partIdCol;

    @FXML
    TableColumn<Part, String> partNameCol;

    @FXML
    TableColumn<Part, Integer> partInvCol;

    @FXML
    TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField productsSearchField;
    
    @FXML
    TableView<Product> productsTable;
    
    @FXML
    TableColumn<Product, Integer> productIdCol;

    @FXML
    TableColumn<Product, String> productNameCol;

    @FXML
    TableColumn<Product, Integer> productInvCol;

    @FXML
    TableColumn<Product, Double> productPriceCol;
    
    public static Stage partsStage = new Stage();
    
    public static Stage productStage = new Stage();
    
    public static Part selected;
    
    public static Product selectedProduct;
    
    public static boolean modified;
    
    public void initialize(){
        //Add sample Data to the parts tableview
        allParts.add(new Inhouse("Tubing", 50, 1.95, 500, 0, 100));
        allParts.add(new Inhouse("Wheel", 30, 10.95, 100, 0, 200));
        allParts.add(new Inhouse("Seat", 30, 10.95, 100, 0, 300));
        allParts.add(new Outsourced("Tire", 30, 5.95, 100, 0, "Tires Inc."));
        allParts.add(new Outsourced("Bearing", 100, 5.95, 500, 0, "Bearings Inc."));
        allParts.add(new Outsourced("Chain", 100, 2.95, 200, 0, "Chains Inc."));
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partIdCol.cellFactoryProperty().addListener((z, oldValue, newValue) -> partsTable.refresh());
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partNameCol.cellFactoryProperty().addListener((z, oldValue, newValue) -> partsTable.refresh());
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partInvCol.cellFactoryProperty().addListener((z, oldValue, newValue) -> partsTable.refresh());
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partPriceCol.cellFactoryProperty().addListener((z, oldValue, newValue) -> partsTable.refresh());
        partsTable.setItems(allParts);
        
        
        
        Product unicycle = new Product("Unicyle", 100, 5, 0, 10);
        products.add(unicycle);
        unicycle.addAssociatedPart(allParts.get(0));
        unicycle.addAssociatedPart(allParts.get(1));
        unicycle.addAssociatedPart(allParts.get(2));
        unicycle.addAssociatedPart(allParts.get(3));
        
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.setItems(products);
        
        partsStage.setTitle("Inventory System");
        productStage.setTitle("Inventory System");
    }

    @FXML
    void deletePart(ActionEvent event) {
        selected = partsTable.getSelectionModel().getSelectedItem();
        if(selected != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Are you sure you want to delete " + selected.getName() + "?");
            Optional<ButtonType> response = alert.showAndWait();
            if(response.get() == ButtonType.OK ){
                allParts.remove(selected);
            }else{
                alert.close();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection");
            alert.setHeaderText("Select a Part to delete it");
            alert.showAndWait();
        }

    }

    @FXML
    void deleteProduct(ActionEvent event) {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if(selectedProduct != null){
            boolean x = selectedProduct.associatedParts.isEmpty();
            if(x == false){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("Cannot delete Product until parts have been removed from the Product.\n"
                        + "Do you want to remove the Parts and delete the Product?");
                Optional<ButtonType> response = alert.showAndWait();
                if(response.get() == ButtonType.OK ){
                    selectedProduct.associatedParts.clear();
                    products.remove(selectedProduct);
                }else{
                    alert.close();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText("Are you sure you want to delete " + selectedProduct.getName() + "?");
                Optional<ButtonType> response = alert.showAndWait();
                if(response.get() == ButtonType.OK ){
                    products.remove(selectedProduct);
                }else{
                    alert.close();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection");
            alert.setHeaderText("Select a Product to delete it");
            alert.showAndWait(); 
        }
    }
    
    @FXML
    void exitProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void openAddInhousePart(ActionEvent event) throws IOException {
        modified = false;
        selected = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RyanMillen_InventorySystem.class.getResource(
            "/View_Controller/AddModifyInhousePart.fxml"));
        Scene scene = new Scene(loader.load());
        partsStage.setScene(scene);
        partsStage.show();
    }

    @FXML
    void openAddProduct(ActionEvent event) throws IOException {
        selectedProduct = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RyanMillen_InventorySystem.class.getResource(
            "/View_Controller/AddModifyProduct.fxml"));
        
        Scene scene = new Scene(loader.load());
        productStage.setScene(scene);
        productStage.show();
        AddModifyProductController controller = loader.getController();
        controller.setMainApp(this);
    }

    @FXML
    void openModifyInhousePart(ActionEvent event) throws IOException, InterruptedException {
        modified = true;
        selected = partsTable.getSelectionModel().getSelectedItem();
//        partsTable.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> System.out.println("v: " + v + "\noldValue: " + oldValue + "\nnewValue: " + newValue));
        partNameCol.cellValueFactoryProperty().addListener((v, oldValue, newValue) -> System.out.println("v: " + v + "\noldValue: " + oldValue + "\nnewValue: " + newValue));
      
        try{
            Inhouse var = (Inhouse) selected;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RyanMillen_InventorySystem.class.getResource(
                "/View_Controller/AddModifyInhousePart.fxml"));
            Scene scene = new Scene(loader.load());
            partsStage.setScene(scene);
            partsStage.show();
        }catch(ClassCastException e){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RyanMillen_InventorySystem.class.getResource(
                "/View_Controller/AddModifyOutsourcedPart.fxml"));
            Scene scene = new Scene(loader.load());
            partsStage.setScene(scene);
            partsStage.show();
        }
        
    }

    @FXML
    void openModifyProduct(ActionEvent event) throws IOException {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RyanMillen_InventorySystem.class.getResource(
                "/View_Controller/AddModifyProduct.fxml"));
            Scene scene = new Scene(loader.load());
            productStage.setScene(scene);
            productStage.show();
    }

    @FXML
    void searchParts(ActionEvent event) throws NumberFormatException{
        boolean found = false;
        String search = partsSearchField.getText();
            try{
                for(Part s: allParts){
                    if(s.getPartID() == Integer.parseInt(search)){
                        result.add(s);
                        found = true;
                    }
                    if(s.getInStock() == Integer.parseInt(search)){
                        result.add(s);
                        found = true;
                    }
                }
                partsTable.setItems(result);
                if(found == true){return;}
            }catch(NumberFormatException e){
                found = false;
            }
            try{
                for(Part s: allParts){
                    if(s.getPrice() == Double.parseDouble(search)){
                        result.add(s);
                        found = true;
                    }
                }
                partsTable.setItems(result);
                if(found == true){return;}
            }catch(NumberFormatException e){
                found = false;
            }
             try{
                for(Part s: allParts){
                    if(s.getName().equalsIgnoreCase(search)){
                        result.add(s);
                        found = true;
                    }
                }
                partsTable.setItems(result);
                if(found == true){return;}
            }catch(NumberFormatException e){
                found = false;
            }
            if(found == false){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Error");
               alert.setHeaderText("Part " + search + " was not found");
               alert.showAndWait();
            }          
    }

    @FXML
    void searchProducts(ActionEvent event) throws NumberFormatException{
        boolean found = false;
        String search = productsSearchField.getText();
            try{
                for(Product s: products){
                    if(s.getProductID() == Integer.parseInt(search)){
                        result2.add(s);
                        found = true;
                    }
                    if(s.getInStock() == Integer.parseInt(search)){
                        result2.add(s);
                        found = true;
                    }
                }
                productsTable.setItems(result2);
                if(found == true){return;}
            }catch(NumberFormatException e){
                found = false;
            }
            try{
                for(Product s: products){
                    if(s.getPrice() == Double.parseDouble(search)){
                        result2.add(s);
                        found = true;
                    }
                }
                productsTable.setItems(result2);
                if(found == true){return;}
            }catch(NumberFormatException e){
                found = false;
            }
             try{
                for(Product s: products){
                    if(s.getName().equalsIgnoreCase(search)){
                        result2.add(s);
                        found = true;
                    }
                }
                productsTable.setItems(result2);
                if(found == true){return;}
            }catch(NumberFormatException e){
                found = false;
            }
            if(found == false){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Error");
               alert.setHeaderText("Product " + search + " was not found");
               alert.showAndWait();
            }          

    }

    @FXML
    void clearParts(ActionEvent event) {
        partsTable.setItems(allParts);
        partsSearchField.setText("");
        result.clear();
    }

    @FXML
    void clearProducts(ActionEvent event) {
        productsTable.setItems(products);
        productsSearchField.setText("");
        result2.clear();
    }
    
}