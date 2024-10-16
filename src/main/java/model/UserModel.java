package model;

import entity.User;

public class UserModel {
    private User connectedUser;
    private String message;
    private boolean success;

    public User getUSer(){
        return this.connectedUser;
    }
    public void setUser(User user){
        this.connectedUser = user;
    }

    public boolean successful() {
        return success;
    }
    public void setSuccess(boolean success) { this.success = success; }

    public void setMessage(String message) { this.message = message; }
    public String message() { return message; }
}
