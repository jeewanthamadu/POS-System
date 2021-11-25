package view.tm;

public class OrderDetailTM {
    private String itemCode;
    private String orderId;
    private int soldQty;
    private double total;

    public OrderDetailTM(String itemCode, String orderId, int soldQty, double total) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setSoldQty(soldQty);
        this.setTotal(total);
    }

    public OrderDetailTM() {
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(int soldQty) {
        this.soldQty = soldQty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", soldQty=" + soldQty +
                ", total=" + total +
                '}';
    }
}
