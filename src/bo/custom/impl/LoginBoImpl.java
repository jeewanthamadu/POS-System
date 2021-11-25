package bo.custom.impl;

import bo.custom.LoginBo;
import dao.Custom.UserDAO;
import dao.DAOFactory;
import dto.UserDto;
import entity.User;

import java.sql.SQLException;

public class LoginBoImpl implements LoginBo {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public String login(UserDto user) throws SQLException, ClassNotFoundException {
        return userDAO.login(new User(user.getUserName(),user.getName(),user.getAddress(),user.getTeleNumber(),user.getRole(),user.getUserPassword()));
    }
}
