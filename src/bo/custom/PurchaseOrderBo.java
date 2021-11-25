package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface PurchaseOrderBo extends SuperBo {
    boolean purchaseOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException;

    List<String> getAllItemId() throws SQLException, ClassNotFoundException;

    ItemDto getItem(String itemCode) throws SQLException, ClassNotFoundException;

    List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;

    CustomerDto TransferCustomerDetails(String id) throws SQLException, ClassNotFoundException;

    String getOrderId() throws SQLException, ClassNotFoundException;
}
