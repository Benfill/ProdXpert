package entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

import enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dateCommande", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dateCommande;

    @NotEmpty(message = "Address is required.")
    @Column(name = "address", nullable = false)
    private String address;

    @NotEmpty(message = "Phone is required.")
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotEmpty(message = "City is required.")
    @Column(name = "city", nullable = false)
    private String city;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total", nullable = false)
    private Double total;

    public Order() {
        this.dateCommande = LocalDateTime.now();
    }

    public Order(LocalDateTime dateCommande, String address, String phone , String city, OrderStatus status, User user,Double total) {
        this.dateCommande = LocalDateTime.now();
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.status = status;
        this.user = user;
        this.total = total;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


    public User getUSer() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotal(){
        return this.total;
    }

    public void setTotal(Double total){
        this.total = total;
    }
}
