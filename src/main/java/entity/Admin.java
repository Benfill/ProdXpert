package entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    private int accessLevel;

    public Admin(){
        super();
    }
    public Admin(String fName, String sName, String email, String pwd, int accessLevel) {
        super(fName, sName, email, pwd);
        this.accessLevel = accessLevel;
    }
    public Admin(Long id, String fName, String sName, String email, String pwd, int accessLevel) {
        super(id, fName, sName, email, pwd);
        this.accessLevel = accessLevel;
    }
    public void setAccessLevel(int accessLevel){ this.accessLevel = accessLevel; }
    public int getAccessLevel(){ return this.accessLevel; }
}
