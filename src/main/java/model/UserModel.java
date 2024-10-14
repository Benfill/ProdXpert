package model;

import entity.User;

public class UserModel {
    private User connectedUser;
    private String error;
    private String success;

    public User getUSer(){
        return this.connectedUser;
    }

    public void setUser(User user){
        this.connectedUser = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
