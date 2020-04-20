package com.example.torti_app.Models;

public class History {
    private int id;
    private String customer;
    private Double total;

    public History(int id, String customer, Double total) {
        this.id = id;
        this.customer = customer;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
