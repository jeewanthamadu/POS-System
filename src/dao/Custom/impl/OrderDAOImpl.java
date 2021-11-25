package dao.Custom.impl;

import dao.CrudUtil;
import dao.Custom.OrderDAO;
import entity.Order;
import javafx.collections.ObservableList;
import view.tm.CartTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` Values(?,?,?)", order.getOrderId(), order.getOrderDate(), order.getcId());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(CartTM t) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ObservableList<CartTM> getList() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` ORDER BY orderId DESC LIMIT 1");
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;

            if (tempId < 10) {
                return "0-00" + tempId;
            } else if (tempId < 100) {
                return "0-0" + tempId;
            } else {
                return "0-" + tempId;
            }
        } else {
            return "0-001";
        }
    }

    @Override
    public List<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` ORDER BY (orderId) DESC");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

}
