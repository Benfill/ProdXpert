package model;

import java.util.Date;

public class OrderDto {
    private Long id;
    private String userName;
    private Double total;
    private String status;
    private Date dateOrder;
    // Constructor
    public OrderDto(Long id, String userName, Double total, String status, Date dateOrder) {
        this.id = id;
        this.userName = userName;
        this.total = total;
        this.status = status;
        this.dateOrder = dateOrder;
    }

    // Default constructor
    public OrderDto() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }
}
