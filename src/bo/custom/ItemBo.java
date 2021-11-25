package bo.custom;

import bo.SuperBo;
import dto.ItemDto;
import javafx.collections.ObservableList;
import view.tm.ItemTM;

import java.sql.SQLException;

public interface ItemBo extends SuperBo {
    ObservableList<ItemTM> getList() throws SQLException, ClassNotFoundException;

    boolean addItem(ItemDto i) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemTM i1) throws SQLException, ClassNotFoundException;
}
