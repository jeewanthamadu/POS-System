package bo.custom;

import bo.SuperBo;
import dto.UserDto;
import javafx.collections.ObservableList;
import view.tm.UserTM;

import java.sql.SQLException;

public interface UserBo extends SuperBo {
    ObservableList<UserTM> getList() throws SQLException, ClassNotFoundException;

    boolean updateUser(UserTM userTM) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String userName) throws SQLException, ClassNotFoundException;

    boolean addUser(UserDto userDto) throws SQLException, ClassNotFoundException;


}
