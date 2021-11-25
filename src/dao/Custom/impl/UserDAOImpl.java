package dao.Custom.impl;

import dao.CrudUtil;
import dao.Custom.UserDAO;
import dto.UserDto;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.tm.UserTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean add(User user) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `user` VALUES (?,?,?,?,?,md5(?))", user.getUser_Name(), user.getName(), user.getAddress(), user.getTeleNo(), user.getRole(), user.getUser_Password());
    }

    @Override
    public boolean delete(String userName) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `user` WHERE User_Name = ?",userName);
    }

    @Override
    public boolean update(UserTM userTM) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `User` SET Name=?, Address=?, Tele_No=?,Role =? WHERE User_Name=?",userTM.getName(),userTM.getAddress(),userTM.getTeleNumber(),userTM.getRole(),userTM.getUserName());
    }

    @Override
    public ObservableList<UserTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList<UserTM> observableList = FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `User` ");
        while (rst.next()) {
            observableList.add(new UserTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return observableList;
    }

    @Override
    public String login(User login) throws SQLException, ClassNotFoundException {
        String username = login.getUser_Name();
        String password = login.getUser_Password();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `User` WHERE User_Name = ? AND User_Password =md5(?)",username,password);
        if (rst.next()) {
            return rst.getString(5);
        } else {
            return "";
        }
    }

}
