package com.example.torti_app.Models;

public class PendingPayments {

    private int quantity;
    private int product_id;
    private String product;

    public PendingPayments(int quantity, int product_id, String product) {
        this.quantity = quantity;
        this.product_id = product_id;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
