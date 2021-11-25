package bo.custom;

import bo.SuperBo;
import javafx.collections.ObservableList;
import view.tm.CartTM;
import view.tm.OrderDetailTM;

import java.sql.SQLException;
import java.util.List;

public interface OrderManageBo extends SuperBo {
    ObservableList<CartTM> getOrderList1(String oId) throws SQLException, ClassNotFoundException;

    List<String> getAllOrderIds() throws SQLException, ClassNotFoundException;

    boolean updateOrder(OrderDetailTM o1) throws SQLException, ClassNotFoundException;
}
