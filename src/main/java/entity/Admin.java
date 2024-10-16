package entity;

import enums.UserRole;

import java.time.LocalDate;

public class Admin extends User {
    private String permission;

    public Admin(String firstName, String secondName, String email, String password) {
        super(firstName, secondName, email, password, UserRole.ADMIN);
    }
}
