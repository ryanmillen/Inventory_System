package View_Controller;

import Model.Inventory;
import static Model.Inventory.allParts;
import static Model.Inventory.products;
import Model.Part;
import Model.Product;
import static View_Controller.MainScreenController.productStage;
import static View_Controller.MainScreenController.selectedProduct;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddModifyProductController {
    
    @FXML
    private TableView<Part> topProductTable;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;
    
    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> bottomProductTable;

    @FXML
    private TableColumn<Part, Integer> partIdCol2;

    @FXML
    private TableColumn<Part, String> partNameCol2;

    @FXML
    private TableColumn<Part, Integer> partInvCol2;

    @FXML
    private TableColumn<Part, Double> partPriceCol2;

    @FXML
    private TextField partSearch;

    @FXML
    private Label addProductLabel;

    @FXML
    private TextField productID;

    @FXML
    private TextField productName;

    @FXML
    private TextField productInv;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField productMax;

    @FXML
    private TextField productMin;
    
    Product product = new Product();
    
    ObservableList<Part> result = FXCollections.observableArrayList();
    
    MainScreenController mainApp;
    
    Inventory inventory;
    
    public void initialize(){
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        topProductTable.setItems(allParts);
        
        if(selectedProduct != null){
            productID.setText(Integer.toString(selectedProduct.getProductID()));
            productName.setText(selectedProduct.getName());
            productInv.setText(Integer.toString(selectedProduct.getInStock()));
            productPrice.setText(Double.toString(selectedProduct.getPrice()));
            productMax.setText(Integer.toString(selectedProduct.getMax()));
            productMin.setText(Integer.toString(selectedProduct.getMin()));
            partIdCol2.setCellValueFactory(new PropertyValueFactory<>("partID"));
            partNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol2.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            partPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
            bottomProductTable.setItems(selectedProduct.associatedParts);
        }else{
            partIdCol2.setCellValueFactory(new PropertyValueFactory<>("partID"));
            partNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInvCol2.setCellValueFactory(new PropertyValueFactory<>("inStock"));
            partPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
            bottomProductTable.setItems(product.associatedParts);
        }
    }

    @FXML
    void addPart(ActionEvent event) {
        if(selectedProduct != null){
            selectedProduct.associatedParts.add(topProductTable.getSelectionModel().getSelectedItem());
        }else{
            product.associatedParts.add(topProductTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void cancelProduct(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK ){
            productStage.close();
        }else{
            alert.close();
        }
    }

    @FXML
    void deletePart(ActionEvent event) {
        if(selectedProduct != null){
            selectedProduct.associatedParts.remove(bottomProductTable.getSelectionModel().getSelectedItem());
        }else{
            product.associatedParts.remove(bottomProductTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void saveProduct(ActionEvent event) {
        //gets data from fields 
       try {
//        String ID = productID.getText();
        String name = productName.getText();
        String inv = productInv.getText();
        String price = productPrice.getText();
        String max = productMax.getText();
        String min = productMin.getText();
       
       
        //checks to see if product already exists
//        for(Product p: products){
//            String name2 = p.getName();
//            String inv2 = Integer.toString(p.getInStock());
//            String price2 = Double.toString(p.getPrice());
//            String max2 = Integer.toString(p.getMax());
//            String min2 = Integer.toString(p.getMin());
//            String productID2 = Integer.toString(p.getProductID());
//            if(ID.equals(productID2))
//                if(name.equals(name2)){
//                    if(inv.equals(inv2)){
//                        if(price.equals(price2)){
//                            if(max.equals(max2)){
//                                if(min.equals(min2)){
//                                    {
//                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                                    alert.setTitle("Product exists");
//                                    alert.setHeaderText("This product already exists. To save a new product"
//                                            + " change one of the fields.");
//                                    alert.showAndWait();
//                                    return;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        // must have at least one Part
        boolean x = product.associatedParts == null;
        if(x == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Must have at least one Part associeated with a Product.");
            alert.showAndWait();  
            return;
        }
        //Price must be at least the cost of all the Parts
        result = bottomProductTable.getItems();
        double total = 0;
        for(Part p: result){
           total += p.getPrice();
        }
        boolean y = Double.parseDouble(price) < total;
        if(y == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Price must be at least the cost of the parts: $" + total);
            alert.showAndWait();  
            return;
        }
        //adds new product to inventory
        if(selectedProduct != null){
            int z = products.indexOf(selectedProduct);
            products.remove(z);
            selectedProduct.setName(name);
            selectedProduct.setPrice(Double.parseDouble(price));
            selectedProduct.setInStock(Integer.parseInt(inv));
            selectedProduct.setMin(Integer.parseInt(min));
            selectedProduct.setMax(Integer.parseInt(max));
            products.add(selectedProduct);
            
            
        }else{
            product.setName(name);
            product.setPrice(Double.parseDouble(price));
            product.setInStock(Integer.parseInt(inv));
            product.setMin(Integer.parseInt(min));
            product.setMax(Integer.parseInt(max));
            products.add(product);
        }

        //alerts user if all the fields are not filled out
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("All text fields must be filled out.");
            alert.showAndWait();
            return;
        }
        //resets all the text fields before closing the window
        productName.setText("");
        productInv.setText("");
        productPrice.setText("");
        productMax.setText("");
        productMin.setText("");
        productID.setText("");

        productStage.close();
     }

    @FXML
    void searchParts(ActionEvent event) throws NumberFormatException{
        MainScreenController msc = new MainScreenController();
        
        boolean found = false;
        String search = partSearch.getText();
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
                topProductTable.setItems(result);
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
                topProductTable.setItems(result);
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
                topProductTable.setItems(result);
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
    void clearParts(ActionEvent event) {
        topProductTable.setItems(allParts);
        partSearch.setText("");
        result.clear();
    }

    public void setMainApp(MainScreenController mainApp) {
        this.mainApp = mainApp;
        
}

}