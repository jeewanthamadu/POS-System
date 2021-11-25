package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;

import java.sql.SQLException;

public interface CustomerBo extends SuperBo {
    boolean addCustomer(CustomerDto c) throws SQLException, ClassNotFoundException;

}
