package bo.custom.impl;

import bo.custom.OrderManageBo;
import dao.Custom.ItemDAO;
import dao.Custom.OrderDAO;
import dao.Custom.OrderDetailDAO;
import dao.Custom.QueryDAO;
import dao.Custom.impl.ItemDAOImpl;
import dao.Custom.impl.OrderDAOImpl;
import dao.Custom.impl.OrderDetailDAOImpl;
import dao.Custom.impl.QueryDAOImpl;
import dao.DAOFactory;
import db.DbConnection;
import javafx.collections.ObservableList;
import view.tm.CartTM;
import view.tm.OrderDetailTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderManageBoImpl implements OrderManageBo {
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.QUERY);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ObservableList<CartTM> getOrderList1(String oId) throws SQLException, ClassNotFoundException {
        return queryDAO.getOrderList1(oId);
    }

    @Override
    public List<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        return orderDAO.getAllOrderIds();
    }

    @Override
    public boolean updateOrder(OrderDetailTM o1) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            if (orderDetailDAO.update(o1)) {

                if (itemDAO.updateReturnQty(o1.getItemCode(), o1.getSoldQty())) {
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            {
                con.setAutoCommit(true);
            }
        }
        return false;
    }


}
