package dto;

public class OrderDetailsDto {
    private String itemCode;
    private String orderId;
    private int soldQty;
    private double discount;


    public OrderDetailsDto(String itemCode, String orderId, int soldQty, double discount) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setSoldQty(soldQty);
        this.setDiscount(discount);
    }

    public OrderDetailsDto() {
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetailsDto{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", soldQty=" + soldQty +
                ", discount=" + discount +
                '}';
    }
}
