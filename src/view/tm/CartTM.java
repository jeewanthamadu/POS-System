package view.tm;

public class CartTM {
    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;
    private int discount;
    private double total;
    private String orderId;

    public CartTM() {
    }

    public CartTM(String itemCode, String description, int qty, double unitPrice, int discount, double total) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setDiscount(discount);
        this.setTotal(total);
    }

    public CartTM(String itemCode, int qty, double unitPrice, int discount, double total, String orderId) {
        this.setItemCode(itemCode);
        this.setQty(qty);
        this.setUnitPrice(unitPrice);
        this.setDiscount(discount);
        this.setTotal(total);
        this.setOrderId(orderId);
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", total=" + total +
                ", orderId='" + orderId + '\'' +
                '}';
    }


}
