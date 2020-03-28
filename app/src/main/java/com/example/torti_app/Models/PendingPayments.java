package com.example.torti_app.Models;

public class PendingPayments {
    private String Producto;
    private int Cantidad;
    private Double Total;

    public PendingPayments(String producto, int cantidad, Double total) {
        Producto = producto;
        Cantidad = cantidad;
        Total = total;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

}
