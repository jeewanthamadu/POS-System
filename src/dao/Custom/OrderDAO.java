package dao.Custom;

import dao.CrudDAO;
import dto.OrderDto;
import entity.Order;
import view.tm.CartTM;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order,String, CartTM> {
    String getOrderId() throws SQLException, ClassNotFoundException;
    List<String> getAllOrderIds() throws SQLException, ClassNotFoundException;


}
