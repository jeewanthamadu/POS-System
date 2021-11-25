package bo.custom.impl;

import bo.custom.CustomerBo;
import dao.Custom.CustomerDAO;
import dao.DAOFactory;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;

public class CustomerBoImpl implements CustomerBo{
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDto c) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(c.getCustomerId(), c.getCustomerTitle(), c.getCustomerName(), c.getCustomerAddress(), c.getCustomerCity(), c.getProvince(), c.getPostalCode()));
    }

}
