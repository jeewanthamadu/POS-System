package controller;

import bo.BoFactory;
import bo.custom.OrderManageBo;
import bo.custom.impl.OrderManageBoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validation;
import view.tm.CartTM;
import view.tm.OrderDetailTM;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageCustomOrderForm {

    public AnchorPane ManageCustomOrderFormId;
    public TableColumn colDiscount;
    public TableColumn colQty;
    public TableColumn colItemCode;
    public TableView tblOrderDetailsContext;
    public JFXComboBox<String> cmbID;
    public JFXTextField txtDiscount;
    public JFXTextField txtQty;
    public JFXTextField txtItemCode;
    public TableColumn colOrderId;
    public JFXTextField txtOrderId;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtTotal;
    public JFXButton btnEdit;
    public JFXTextField txtReturnQty;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern qty = Pattern.compile("^[0-9]+$");

    private String oId;

    private final OrderManageBo orderManageBo = (OrderManageBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.MANAGE_ORDER);

    private void storeValidations() {
        map.put(txtReturnQty, qty);
    }

    public void initialize() {
        btnEdit.setDisable(true);
        cmbID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            oId = newValue;
            storeValidations();

            try {
                AddTable(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        try {
            loadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void AddTable(String oId) throws SQLException, ClassNotFoundException {
        ObservableList<CartTM> observableList;
        observableList = orderManageBo.getOrderList1(oId);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrderDetailsContext.setItems(observableList);
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> allOrderIds = orderManageBo.getAllOrderIds();
        cmbID.getItems().addAll(allOrderIds);

    }

    public void btnConfirmEditOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {
        OrderDetailTM o1 = new OrderDetailTM(
                txtItemCode.getText(),
                txtOrderId.getText(),
                Integer.parseInt(txtReturnQty.getText()),
                Double.parseDouble(txtDiscount.getText())
        );
        if (orderManageBo.updateOrder(o1)) {
            AddTable(oId);
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();

            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/ReturnBill.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);

                String itemCode = txtItemCode.getText();
                String qty = txtReturnQty.getText();
                String unitPrice = txtUnitPrice.getText();
                String discount = txtDiscount.getText();
                String total = txtTotal.getText();
                String orderId = txtOrderId.getText();

                HashMap map = new HashMap();
                map.put("itemCode", itemCode);
                map.put("Qty", qty);
                map.put("unitPrice", unitPrice);
                map.put("Discount", discount);
                map.put("Total", total);
                map.put("OrderId", orderId);

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map , new JREmptyDataSource(1));
                JasperViewer.viewReport(jasperPrint, false);

            } catch (JRException e) {
                e.printStackTrace();
            }


        } else {
            new Alert(Alert.AlertType.WARNING, "TryAgain..").show();
        }

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtItemCode.clear();
        txtOrderId.clear();
        txtQty.clear();
        txtDiscount.clear();
        txtUnitPrice.clear();
        txtTotal.clear();
        tblOrderDetailsContext.getSelectionModel().clearSelection();
        cmbID.setValue("");
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ManageCustomOrderFormId.getScene().getWindow();
        stage.close();
    }

    public void BtnSearchOnAction(ActionEvent actionEvent) {

    }

    public void tblOnAction(MouseEvent mouseEvent) {

        CartTM order = null;
        try {
            order = (CartTM) tblOrderDetailsContext.getSelectionModel().getSelectedItem();
            txtItemCode.setText(order.getItemCode());
            txtOrderId.setText(order.getOrderId());
            txtQty.setText("" + order.getQty());
            txtDiscount.setText("" + order.getDiscount());
            txtUnitPrice.setText("" + order.getUnitPrice());
            txtTotal.setText("" + order.getTotal());
            txtReturnQty.setEditable(true);
            btnEdit.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cmbOnAction(ActionEvent actionEvent) {
        txtItemCode.clear();
        txtOrderId.clear();
        txtQty.clear();
        txtDiscount.clear();
        txtUnitPrice.clear();
        txtTotal.clear();
    }

    private double calDiscount(int qtyForCustomer, double unitPrice, int discount) {
        double total = qtyForCustomer * unitPrice;
        double s = 100 - discount;
        double price = (s * total) / 100;
        return price;
    }

    public void CheckReturnQty(KeyEvent keyEvent) {

        btnEdit.setDisable(true);
        CartTM selectedItem = (CartTM) tblOrderDetailsContext.getSelectionModel().getSelectedItem();
        try {
            if (Integer.parseInt(txtQty.getText()) < Integer.parseInt(txtReturnQty.getText()) || 0 > Integer.parseInt(txtReturnQty.getText())) {
                txtReturnQty.clear();
                new Alert(Alert.AlertType.ERROR,"Invalid Returned Qty").show();
            }
        } catch (NumberFormatException e) {
        }

        if (txtTotal.getText().isEmpty()) {
            return;
        }

        if (!txtReturnQty.getText().isEmpty()) {
            try {
                double discount = calDiscount(Integer.parseInt(txtReturnQty.getText()), selectedItem.getUnitPrice(), selectedItem.getDiscount());
                txtTotal.setText(discount + "");
            } catch (NumberFormatException e) {
            }
        } else {
            txtTotal.setText(selectedItem.getTotal() + "");
        }

        Object response = Validation.validate(map,btnEdit,"Green");
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
