package dao.Custom.impl;

import dao.CrudUtil;
import dao.Custom.ItemDAO;
import dto.ItemDto;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.tm.ItemTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public List<String> getAllItemId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        List<String> ids = new ArrayList<>();
        while(rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    @Override
    public ItemDto getItem(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?",id);
        if (rst.next()){
            return new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6)
            );
        }
        return null;
    }

    @Override
    public boolean updateReturnQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET QtyOnHand=(QtyOnHand+'" + qty + "')WHERE ItemCode='" + itemCode + "'");
    }

    @Override
    public boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET QtyOnHand=(QtyOnHand-'" + qty + "')WHERE ItemCode='" + itemCode + "'");
    }

    @Override
    public boolean add(Item i) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?,?,?)",i.getItemCode(),i.getDescription(),i.getPackSize(),i.getUnitPrice(),i.getQtyOnHand(),i.getDiscount());

    }

    @Override
    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE ItemCode=?",itemCode);
    }

    @Override
    public boolean update(ItemTM item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET Discription =?,packSize=?,unitPrice=?,qtyOnHand=?,discount=? WHERE itemCode=?",item.getDescription(),item.getPacketSize(),item.getUnitPrice(),item.getQtyOnHand(),item.getDiscount(),item.getItemCode());
    }

    @Override
    public ObservableList<ItemTM> getList() throws SQLException, ClassNotFoundException {
        ObservableList <ItemTM>obList= FXCollections.observableArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()){
            obList.add(new ItemTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getInt(6)
            ));
        }
        return obList;
    }


}
