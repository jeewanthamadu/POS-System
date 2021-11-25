package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import dto.ItemDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.Validation;
import view.tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminDashBoardFormController {
    public AnchorPane AdminDashBoardFormContext;
    public AnchorPane DashBoardFormContext;
    public Label lblTime;
    public Label lblDate;
    public TextField txtDescription;
    public TextField txtPacketSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TextField txtItemCode;
    public TableView <ItemTM>TblAdmin;
    public TableColumn ColItemCode;
    public TableColumn ColDescription;
    public TableColumn ColPacketSize;
    public TableColumn ColUnitPrice;
    public TableColumn ColQtyOnHand;
    public TableColumn ColDiscount;
    public TextField txtDiscount;
    public JFXButton updateBtn;
    public JFXButton addItemBtn;
    public JFXButton deleteItemBtn;

    int SelectedRow =-1;
    private final ItemBo itemBo = (ItemBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern itemCodePattern = Pattern.compile("^(I)[-]?[0-9]{3}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]{5,30}$");
    Pattern packSizePattern = Pattern.compile("^[0-9]{1,4}[A-z]{1,9}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]{2,4}$");
    Pattern QTYPattern = Pattern.compile("^[0-9]{1,10}$");
    Pattern discountPattern = Pattern.compile("^[0-9]{2}$");



    private void storeValidations() {
        map.put(txtItemCode,itemCodePattern);
        map.put(txtDescription,descriptionPattern);
        map.put(txtPacketSize,packSizePattern);
        map.put(txtUnitPrice,unitPricePattern);
        map.put(txtQtyOnHand,QTYPattern);
        map.put(txtDiscount,discountPattern);
    }

    public void BtnSystemReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/systemReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage CustomerDto Form");
        stage.show();
        stage.centerOnScreen();
    }

    public void BtnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window   = (Stage) AdminDashBoardFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
        window.centerOnScreen();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+ " : "+currentTime.getMinute()+ " : "+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void initialize() {
        loadDateAndTime();
        try {
            showTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TblAdmin.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            SelectedRow = (int) newValue;
            if (SelectedRow == -1) {
                addItemBtn.setDisable(false);
                updateBtn.setDisable(true);
                deleteItemBtn.setDisable(true);
                txtItemCode.setEditable(true);
            }else{
                addItemBtn.setDisable(true);
                updateBtn.setDisable(false);
                deleteItemBtn.setDisable(false);
                txtItemCode.setEditable(false);
            }
        });
    storeValidations();

    }

    public void showTable () throws SQLException, ClassNotFoundException {
        ObservableList<ItemTM> list = itemBo.getList();

        ColItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        ColPacketSize.setCellValueFactory(new PropertyValueFactory<>("packetSize"));
        ColUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        ColQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        ColDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        TblAdmin.setItems(list);
    }

    public void BtnAddItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String itemCode = txtItemCode .getText();
        String description = txtDescription.getText();
        String packetSize = txtPacketSize.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand =Integer.parseInt(txtQtyOnHand.getText());
        int discount = Integer.parseInt(txtDiscount.getText());

        ItemTM i1=new ItemTM(itemCode,description,packetSize,unitPrice,qtyOnHand,discount);

       itemBo.getList().add(i1);
        TblAdmin.setItems(itemBo.getList());

        ItemDto i= new ItemDto(txtItemCode.getText(),txtDescription.getText(),txtPacketSize.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnHand.getText()),Integer.parseInt(txtDiscount.getText()));
        if(itemBo.addItem(i)) {
            new Alert(Alert.AlertType.CONFIRMATION, "SAVED").show();
            showTable();
        }else{
            new Alert(Alert.AlertType.WARNING, "PLEASE TRY AGAIN").show();
        }
        ClearField();

    }

    public void ClearField(){
        txtItemCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtPacketSize.clear();
        txtQtyOnHand.clear();
        txtDiscount.clear();
        txtItemCode.requestFocus();
        addItemBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteItemBtn.setDisable(true);
        txtItemCode.setEditable(true);

    }

    public void BtnClearOnAction(ActionEvent actionEvent) {
       ClearField();
    }

    public void BtnDeleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemTM item = TblAdmin .getSelectionModel().getSelectedItem();
        String itemCode = item .getItemCode();

        if (itemBo.deleteItem(itemCode)){
            new Alert(Alert.AlertType.CONFIRMATION,"DO YOU WANT TO DELETE SELECTED ITEM").show();
            showTable();
            TblAdmin.refresh();
        }else {
            new Alert(Alert.AlertType.WARNING,"TRY AGAIN").show();
        }
        ClearField();
    }

    public void HandleMouseOnAction(MouseEvent mouseEvent) {
        ItemTM itemTM = null;
        try {
            ItemTM item = TblAdmin.getSelectionModel().getSelectedItem();
            txtItemCode.setText(item.getItemCode());
            txtUnitPrice.setText("" + item.getUnitPrice());
            txtDescription.setText(item.getDescription());
            txtQtyOnHand.setText("" + item.getQtyOnHand());
            txtDiscount.setText("" + item.getDiscount());
            txtPacketSize.setText("" + item.getPacketSize());
        } catch(Exception e){

        }
    }

    public void BtnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemTM i1 = new ItemTM(
          txtItemCode.getText(),
          txtDescription.getText(),
          txtPacketSize.getText(),
          Double.parseDouble(txtUnitPrice.getText()),
          Integer.parseInt(txtQtyOnHand.getText()),
          Integer.parseInt( txtDiscount.getText())
        );
        if(itemBo.updateItem(i1)){
            new Alert(Alert.AlertType.CONFIRMATION,"UPDATE SUCCESSFULLY.....").show();
            showTable();
        }else {
            new Alert(Alert.AlertType.WARNING,"PLEASE TRY AGAIN").show();
        }
    }

    public void BtnUserControlOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/UserControllerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    public void txtItemKeyRelease(KeyEvent keyEvent) {
        addItemBtn.setDisable(true);
        Object response = Validation.validate(map,addItemBtn,"Green");
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
