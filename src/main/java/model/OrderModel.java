package model;

import java.util.HashMap;
import java.util.Map;

import entity.User;

public class OrderModel {
    private User connectedUser;
    private String error;
    private Map<String, String> errors = new HashMap<>();
    private String successMessage;
    private String errorMessage;


    public String getErrorMessage(){
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    // Corrected Getter and Setter for successMessage
    public String getSuccessMessage(){
        return this.successMessage;
    }

    public void setSuccessMessage(String successMessage){
        this.successMessage = successMessage;
    }

   
    // Getters and Setters for error and success
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

   
   

    // Getters and Setters for errors map
    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

 
    // Getter and Setter for connectedUser if needed
    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

   
}
