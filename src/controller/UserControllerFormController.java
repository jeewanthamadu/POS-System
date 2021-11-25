package controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.Validation;
import view.tm.UserTM;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class UserControllerFormController {

    public JFXTextField txtUserName;
    public JFXTextField txtName;
    public JFXTextField txtUserPassword;
    public JFXTextField txtTeleNo;
    public JFXTextField txtAddress;
    public JFXComboBox<String> cmbRole;
    public JFXButton btnAdd;
    public TableColumn colUserName;
    public TableView<UserTM> tblUser;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTelePhoneNumber;
    public TableColumn colRole;
    String getRole;
    private final UserBo userBo = (UserBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.USER);

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern name = Pattern.compile("^[A-z ]{3,20}$");
    Pattern address = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern teleNumber = Pattern.compile("^[0-9]{10}$");
    Pattern username = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern password = Pattern.compile("[A-Za-z0-9]{5,15}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        addTable();
        cmbRole.getItems().setAll("Admin", "Cashier");
        cmbRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getRole = newValue;
        });
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtName,name);
        map.put(txtAddress,address);
        map.put(txtTeleNo,teleNumber);
        map.put(txtUserName,username);
        map.put(txtUserPassword,password);
    }

    public void txtUserKeyRelease(KeyEvent keyEvent) {
        btnAdd.setDisable(true);
        Object response = Validation.validate(map,btnAdd,"Green");
        if (keyEvent.getCode()== KeyCode.ENTER) {
            if (response instanceof TextField){
                TextField error  = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
                new Alert(Alert.AlertType.CONFIRMATION, "Done").show();
            }
        }
    }

    public void addTable() throws SQLException, ClassNotFoundException {

        ObservableList<UserTM> userTMS = userBo.getList();
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colTelePhoneNumber.setCellValueFactory(new PropertyValueFactory<>("teleNumber"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        tblUser.setItems(userTMS);
    }

    public void clearField() {
        txtUserName.clear();
        txtAddress.clear();
        cmbRole.setValue(" ");
        txtName.clear();
        txtTeleNo.clear();
        txtUserPassword.clear();
        txtUserName.requestFocus();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        UserTM userTM = new UserTM(
                txtUserName.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtTeleNo.getText(),
                getRole
        );
        if (userBo.updateUser(userTM)) {
            addTable();
            clearField();
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "something went wrong").show();
        }

    }

    public void btnRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        UserTM getSelectRow = tblUser.getSelectionModel().getSelectedItem();
        String userName = getSelectRow.getUserName();
        if (userBo.deleteUser(userName)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully").show();
            addTable();
            clearField();
        } else {
            new Alert(Alert.AlertType.WARNING, "something went wrong").show();
        }
    }

    public void btnAddonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        UserDto userDto = new UserDto(
                txtUserName.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtTeleNo.getText(),
                getRole,
                txtUserPassword.getText()
        );

        if (userBo.addUser(userDto)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully").show();
            addTable();
        } else {
            new Alert(Alert.AlertType.WARNING, "something went wrong").show();
        }
        clearField();
    }

    public void tblOnMouseClick(MouseEvent mouseEvent) {
        UserTM userTM = tblUser.getSelectionModel().getSelectedItem();
        txtUserName.setText(userTM.getUserName());
        txtName.setText(userTM.getName());
        txtAddress.setText(userTM.getAddress());
        txtTeleNo.setText(userTM.getTeleNumber());
        cmbRole.setValue(userTM.getRole());
    }
}
