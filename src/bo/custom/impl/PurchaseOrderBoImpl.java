package bo.custom.impl;

import bo.custom.PurchaseOrderBo;
import dao.Custom.*;
import dao.DAOFactory;
import db.DbConnection;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDto;
import entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PurchaseOrderBoImpl implements PurchaseOrderBo {

    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

/*
    private final CustomerDAO customerDAO =new CustomerDAOImpl();
    private final ItemDAO itemDAO =new ItemDAOImpl();
    private final OrderDAO orderDAO =new OrderDAOImpl();
    private final OrderDetailDAO orderDetailDAO =new OrderDetailDAOImpl();*/

    @Override
    public boolean purchaseOrder (OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection con = null;
        con = DbConnection.getInstance().getConnection();
        try {
            con.setAutoCommit(false);
            if (orderDAO.add(new Order(orderDto.getOrderId(),orderDto.getOrderDate(),orderDto.getCustomerId()))) {
                if (orderDetailDAO.saveOrderDetails(orderDto.getOrderId(), orderDto.getItems())) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            {
                con.setAutoCommit(true);
            }
        }
        return false;
    }

    @Override
    public List<String> getAllItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllItemId();
    }

    @Override
    public ItemDto getItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.getItem(itemCode);
    }

    @Override
    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllCustomerIds();
    }

    @Override
    public CustomerDto TransferCustomerDetails(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.TransferCustomerDetails(id);
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderId();
    }


}
