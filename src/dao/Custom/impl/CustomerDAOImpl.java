package dao.Custom.impl;

import dao.CrudUtil;
import dao.Custom.CustomerDAO;
import entity.Customer;
import javafx.collections.ObservableList;
import dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public boolean add(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUES(?,?,?,?,?,?,?)",c.getId(),c.getCustTitle(),c.getName(),c.getAddress(),c.getCity(),c.getProvince(),c.getPostalCode());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Object t) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public ObservableList<Object> getList() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public CustomerDto TransferCustomerDetails(String id) throws SQLException, ClassNotFoundException {
        final ResultSet rst = CrudUtil.executeQuery("SELECT * FROM CUSTOMER WHERE id =?", id);
        if(rst.next()){
            return new CustomerDto(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        }else {
            return null;
        }
    }

    @Override
    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM CUSTOMER");
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1)
            );
        }
        return ids;
    }
}
