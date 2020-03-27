package com.example.torti_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app.Models.Customer;
import com.example.torti_app.Models.Delivery;
import com.example.torti_app.R;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    private List<Delivery> deliveryList = null;

    public DeliveryAdapter(List<Delivery> deliveries) {
        this.deliveryList = deliveries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_deliveries, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery delivery = this.deliveryList.get(position);
        Customer customer = delivery.getCustomer();
        holder.txvCustomerName.setText(String.format("Nombre: %s", customer.getName()));
        holder.txvCustomerLastName.setText(String.format(
                "Apellidos: %s %s", customer.getMaternalName(), customer.getPaternalName()));
        holder.txvDate.setText(String.format("Fecha: %s", delivery.getDate()));
    }

    @Override
    public int getItemCount() {
        return this.deliveryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvCustomerName = null, txvCustomerLastName = null,
                txvDate = null;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvCustomerName = itemView.findViewById(R.id.txvCustomerName);
            this.txvCustomerLastName = itemView.findViewById(R.id.txvCustomerLastName);
            this.txvDate = itemView.findViewById(R.id.txvDate);
        }
    }
}
