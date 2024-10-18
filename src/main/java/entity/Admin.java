package entity;

import enums.UserRole;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    private int accessLevel;

    public Admin(String fName, String sName, String email, String pwd) {
        super(fName, sName, email, pwd);
        // this.setRole(UserRole.ADMIN);
        // super(fName, sName, email, pwd, UserRole.ADMIN);
    }

    public void setAccessLevel(int accessLevel){ this.accessLevel = accessLevel; }
    public int getAccessLevel(){ return this.accessLevel; }
}
