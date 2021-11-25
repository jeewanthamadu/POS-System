package bo.custom.impl;

import bo.custom.ItemBo;
import dao.Custom.ItemDAO;
import dao.DAOFactory;
import entity.Item;
import javafx.collections.ObservableList;
import dto.ItemDto;
import view.tm.ItemTM;

import java.sql.SQLException;

public class ItemBoImpl implements ItemBo {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);


    @Override
    public ObservableList<ItemTM> getList() throws SQLException, ClassNotFoundException {
        return itemDAO.getList();
    }

    @Override
    public boolean addItem(ItemDto i) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(i.getItemCode(),i.getDescription(),i.getPackSize(),i.getUnitPrice(),i.getQtyOnHand(),i.getDiscount()));
    }

    @Override
    public boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemCode);
    }

    @Override
    public boolean updateItem(ItemTM i1) throws SQLException, ClassNotFoundException {
        return itemDAO.update(i1);
    }
}
