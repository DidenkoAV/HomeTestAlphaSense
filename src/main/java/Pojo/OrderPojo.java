package Pojo;

public class OrderPojo {

    private String id;
    private String petId;
    private String quantity;
    private String shipDate;
    private String status;
    private String complete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "OrderPojo{" +
                "id='" + id + '\'' +
                ", petId='" + petId + '\'' +
                ", quantity='" + quantity + '\'' +
                ", shipDate='" + shipDate + '\'' +
                ", status='" + status + '\'' +
                ", complete='" + complete + '\'' +
                '}';
    }
}
