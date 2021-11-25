package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Validation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class AddCustomerFormController {
    public AnchorPane AddCustomerContext;
    public JFXTextField txtId;
    public JFXTextField txtCT;
    public JFXTextField txtCity;
    public JFXTextField txtAddress;
    public JFXTextField txtName;
    public JFXTextField txtPostalCode;
    public JFXTextField txtProvince;
    public JFXButton btnAddCustomer;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern customerIdPattern = Pattern.compile("^(C)[-]?[0-9]{3}$");
    Pattern customerTitlePattern = Pattern.compile("^(Mr.|Miss.|Mrs.)$");
    Pattern customerNamePattern = Pattern.compile("^[A-z ]{5,30}$");
    Pattern addressPattern = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern cityPattern = Pattern.compile("^[A-z ]{5,20}$");
    Pattern provincePattern = Pattern.compile("^[A-z ]{5,10}$");
    Pattern postalCodePattern = Pattern.compile("^[0-9]{5}$");


    private final CustomerBo customerBo = (CustomerBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);

    public void initialize(){
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtId,customerIdPattern);
        map.put(txtCT,customerTitlePattern);
        map.put(txtName,customerNamePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtCity,cityPattern);
        map.put(txtProvince,provincePattern);
        map.put(txtPostalCode,postalCodePattern);
    }
    
    public void BtnAddCustomerOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        CustomerDto c = new CustomerDto(txtId.getText(),
                txtCT.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
        if (customerBo.addCustomer(c)) {
            new Alert(Alert.AlertType.CONFIRMATION, "SAVED").show();
            Stage stage = (Stage) AddCustomerContext.getScene().getWindow(); stage.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "SAVED").show();
        }
    }

    public void txtCustomerKeyRelease(KeyEvent keyEvent) {
        btnAddCustomer.setDisable(true);
        Object response = Validation.validate(map,btnAddCustomer,"Green");
        if (keyEvent.getCode()== KeyCode.ENTER) {
            if (response instanceof TextField){
                TextField error  = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
                new Alert(Alert.AlertType.CONFIRMATION, "Done").show();
            }
        }
    }

}
