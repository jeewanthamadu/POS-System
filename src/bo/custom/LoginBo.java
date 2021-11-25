package bo.custom;

import bo.SuperBo;
import dto.UserDto;

import java.sql.SQLException;

public interface LoginBo extends SuperBo {
    String login(UserDto userDto) throws SQLException, ClassNotFoundException;
}
