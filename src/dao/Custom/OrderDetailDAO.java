package dao.Custom;

import dao.CrudDAO;
import dto.OrderDetailsDto;
import entity.OrderDetails;
import view.tm.OrderDetailTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetails,String, OrderDetailTM> {
    boolean saveOrderDetails(String orderId, ArrayList<OrderDetails> items) throws SQLException, ClassNotFoundException;
}
