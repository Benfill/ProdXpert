package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.User;

public class OrderModel {
    private User connectedUser;
    private String error;
    private Map<String, String> errors = new HashMap<>();
    private String successMessage;
    private String errorMessage;
    private List<OrderDto> orders;
    private Long orderTotal;
    private int page;
    private int totalPages;




    public void setTotalPages(int totalPages)
    {
        this.totalPages = totalPages;
    }
    public int getTotalPages(){
        return this.totalPages;
    }


    public void setPage(int page)
    {
        this.page = page;
    }
    public int getPage(){
        return this.page;
    }

    public void setOrderTotal(Long orderTotal){
        this.orderTotal = orderTotal;
    }

    public Long getOrderTotal(Long orderTotal){
       return this.orderTotal ;
    }

    public void setOrders(List<OrderDto> orders){
        this.orders = orders;
    }

    public List<OrderDto> getOrders(){
        return this.orders;
    }

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
