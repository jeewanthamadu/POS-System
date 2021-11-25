package dao.Custom;

import dao.CrudDAO;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer, String, Object> {
    CustomerDto TransferCustomerDetails(String id) throws SQLException, ClassNotFoundException;

    List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
}
