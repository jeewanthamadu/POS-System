package bo.custom.impl;

import bo.custom.UserBo;
import dao.Custom.UserDAO;
import dao.DAOFactory;
import entity.User;
import javafx.collections.ObservableList;
import dto.UserDto;
import view.tm.UserTM;

import java.sql.SQLException;

public class UserBoImpl implements UserBo {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ObservableList<UserTM> getList() throws SQLException, ClassNotFoundException {
        return userDAO.getList();
    }

    @Override
    public boolean updateUser(UserTM userTM) throws SQLException, ClassNotFoundException {
        return userDAO.update(userTM);
    }

    @Override
    public boolean deleteUser(String userName) throws SQLException, ClassNotFoundException {
        return userDAO.delete(userName);
    }

    @Override
    public boolean addUser(UserDto user) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(user.getUserName(),user.getName(),user.getAddress(),user.getTeleNumber(),user.getRole(),user.getUserPassword()));
    }
}
