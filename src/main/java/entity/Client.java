package entity;

import enums.UserRole;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {
    private String deliveryAddress;
    private String paymentMethod;

    public Client(){
        super();
    }

    public Client(String fName, String sName, String email, String pwd) {
        super(fName, sName, email, pwd);
        // this.setRole(UserRole.CLIENT);
        // super(fName, sName, email, pwd, UserRole.CLIENT);
    }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public void setPaymentMethod(String paymentMethod){ this.paymentMethod = paymentMethod;}
    public String getPaymentMethod() { return this.paymentMethod; }

}
