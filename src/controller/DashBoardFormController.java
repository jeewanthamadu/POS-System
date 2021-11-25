package controller;

import bo.BoFactory;
import bo.custom.PurchaseOrderBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.OrderDetails;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import dto.CustomerDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.CartTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DashBoardFormController {
    public AnchorPane DashBoardFormContext;
    public Label lblTime;
    public Label lblDate;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TextField txtPacketSize;
    public TextField txtDiscount;
    public ComboBox<String> CmbItemCode;
    public TextField txtDescription;
    public TableView<CartTM> tblCashier;
    public TableColumn ColItemCode;
    public TableColumn ColDescription;
    public TableColumn ColQty;
    public TableColumn ColUnitPrice;
    public TableColumn ColDiscount;
    public TableColumn ColTotal;
    public Label LblTotal;

    public static ObservableList<CartTM> obList= FXCollections.observableArrayList();
    public Label lblOrderId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtProvince;
    public JFXTextField txtCity;
    public JFXComboBox<String> CmbCustomerId;
    private int cartSelectedRowForRemove = -1;

    private final PurchaseOrderBo purchaseOrderBo = (PurchaseOrderBo) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PURCHASE_ORDER);

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

    private void LoadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = purchaseOrderBo.getAllItemId();
        CmbItemCode.getItems().addAll(itemIds);
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();

        ColItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        ColDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        ColUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        ColQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        ColTotal.setCellValueFactory(new PropertyValueFactory<>("total"));


        loadCustomerIds();
        setOrderId();
        try {
            LoadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblCashier.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
        cartSelectedRowForRemove = (int) newValue;
        });

        CmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCustomerData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        CustomerDto c1 = purchaseOrderBo.TransferCustomerDetails(id);
        if(c1 == null){
            //new Alert(Alert.AlertType.WARNING,"EMPTY RESULT SET").show();
        }else{
            txtCustomerName.setText(c1.getCustomerName());
            txtCity.setText(c1.getCustomerCity());
            txtProvince.setText(c1.getProvince());
        }
    }

    public void ClearField(){
        CmbItemCode.setValue("");
        txtDescription.clear();
        txtUnitPrice.clear();
        txtPacketSize.clear();
        txtQtyOnHand.clear();
        txtDiscount.clear();
        txtQty.clear();
        CmbItemCode.requestFocus();
        CmbItemCode.setValue("");
        tblCashier.getSelectionModel().clearSelection();

    }

    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        ItemDto i1 = purchaseOrderBo.getItem(itemCode);
        if(i1==null){
            new Alert(Alert.AlertType.WARNING,"EMPTY RESULT SET");
        }else {
            txtDescription.setText(i1.getDescription());
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtPacketSize.setText(i1.getPackSize());
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtDiscount.setText(String.valueOf(i1.getDiscount()));
        }
    }

    public void BtnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) DashBoardFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void BtnManageCusOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageCustomOrder.fxml ");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage CustomerDto Form");
        stage.show();
        stage.centerOnScreen();
    }

    public void BtnOrderOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, JRException {

        String cusId;
        if (txtCustomerName.getText().isEmpty()) {
            cusId = null;
        } else {
            cusId = CmbCustomerId.getValue();
        }

        ArrayList<OrderDetails> orderDetailDtos = new ArrayList<>();

        for (CartTM tempTm:obList
        ) {
            orderDetailDtos.add(new OrderDetails(tempTm.getItemCode(),lblOrderId.getText(),tempTm.getQty(),tempTm.getDiscount()
            ));
        }
        OrderDto orderDto = new OrderDto(
                lblOrderId.getText(),
                lblDate.getText(),
                //CmbCustomerId.getValue(),
                cusId,
                orderDetailDtos

        );
        if (purchaseOrderBo.purchaseOrder(orderDto)){
            new Alert(Alert.AlertType.CONFIRMATION,"SUCCESS").show();

            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/report/Bill.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                ObservableList<CartTM> items = tblCashier.getItems();

                String oId = lblOrderId.getText();
                String subTotal = LblTotal.getText();
                String cusID = CmbCustomerId.getValue();
                String cusName = txtCustomerName.getText();

                HashMap map = new HashMap();
                map.put("OrderId", oId);
                map.put("SubTotal", subTotal);
                map.put("customerID", cusID);
                map.put("customerName", cusName);

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map , new JRBeanArrayDataSource(items.toArray()));
                JasperViewer.viewReport(jasperPrint, false);

            }catch (JRException e) {
                e.printStackTrace();
            }

            setOrderId();

            tblCashier.getItems().clear();
            LblTotal.setText("");
            CmbCustomerId.setValue("");
            txtCity.clear();
            txtCustomerName.clear();
            txtProvince.clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"TRY AGAIN").show();
        }

    }

    public void BtnCancelOrder(ActionEvent actionEvent) {
        ClearField();
        LblTotal.setText("");
        obList.clear();
    }

    public void BtnRemoveItemOnAction(ActionEvent actionEvent) {
        if(cartSelectedRowForRemove==-1){
            LblTotal.setText("0.00");
            new Alert(Alert.AlertType.WARNING,"PLEASE SELECT THE RAW").show();
        }else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCashier.refresh();
        }
        ClearField();
    }

    private double calDiscount(int qtyForCustomer, double unitPrice, int discount) {
        double total = qtyForCustomer * unitPrice;
        double s = 100 - discount;
        double price = (s * total) / 100;
        return price;
    }

    public void BtnAddToCartOnAction(ActionEvent actionEvent) {
        String description = txtDescription.getText();
        int packetSize = Integer.parseInt(txtPacketSize.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        int discount = Integer.parseInt(txtDiscount.getText());
        double total = calDiscount(qtyForCustomer,unitPrice,discount);   /*qtyForCustomer * unitPrice-qtyForCustomer*unitPrice * discount/100;*/

        if(qtyOnHand< qtyForCustomer){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();//
            txtQty.clear();
            return;
        }

        CartTM tm = new CartTM(
                CmbItemCode.getValue(),
                description,
                qtyForCustomer,
                unitPrice,
                discount,
                total
        );

        int rowNumber = isExists(tm);

        if (rowNumber==-1) {
            obList.add(tm);

        }else {
            CartTM temp = obList.get(rowNumber);
            CartTM newTm = new CartTM(
                    temp.getItemCode(),
                    temp.getDescription(),
                    temp.getQty()+qtyForCustomer,
                    unitPrice,
                    temp.getDiscount(),
                    total+ temp.getTotal()

            );

            if(qtyOnHand< temp.getQty()) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();//
            }

            obList.remove(rowNumber);
            obList.add(newTm);
        }

        tblCashier.setItems(obList);
        calculateCost();
        ClearField();
    }

    private int isExists(CartTM tm){
        for (int i = 0; i < obList.size(); i++){
            if (tm.getItemCode().equals(obList.get(i).getItemCode())){
                return i;
            }
        }
        return -1;
    }

    void calculateCost(){
        double ttl = 0;
        for (CartTM tm:obList
        ) {
            ttl+= tm.getTotal();
        }
        String formatTotal = new DecimalFormat("0.00").format(ttl);
        LblTotal.setText(formatTotal+" /=");
    }

    private void setOrderId(){
        try {
            lblOrderId.setText(purchaseOrderBo.getOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddNewCustomer(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddCustomerForm.fxml ");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Add CustomerDto");
        stage.show();
        stage.centerOnScreen();
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String>id = purchaseOrderBo.getAllCustomerIds();
        CmbCustomerId.getItems().addAll(id);
    }

    public void btnCmbRefresh(MouseEvent mouseEvent) {
        CmbCustomerId.getItems().clear();
        try {
            loadCustomerIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
