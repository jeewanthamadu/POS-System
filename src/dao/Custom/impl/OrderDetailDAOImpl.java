package dao.Custom.impl;

import dao.CrudUtil;
import dao.Custom.OrderDetailDAO;
import dto.OrderDetailsDto;
import entity.OrderDetails;
import javafx.collections.ObservableList;
import view.tm.OrderDetailTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {


    @Override
    public boolean add(OrderDetails orderDetailsDto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetailTM o) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("UPDATE `Order Detail` SET orderQTY=(orderQTY-?) WHERE orderId=? AND itemCode=?",o.getSoldQty(),o.getOrderId(),o.getItemCode());
    }

    @Override
    public ObservableList<OrderDetailTM> getList() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveOrderDetails(String orderId, ArrayList<OrderDetails> items) throws SQLException, ClassNotFoundException {
        for (OrderDetails temp : items) {
            if (CrudUtil.executeUpdate("INSERT INTO `Order Detail` VALUES(?,?,?,?)",temp.getItemCode(),orderId,temp.getOrderQty(),temp.getDiscount())) {
                if (new ItemDAOImpl().updateQty(temp.getItemCode(), temp.getOrderQty())) {

                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
