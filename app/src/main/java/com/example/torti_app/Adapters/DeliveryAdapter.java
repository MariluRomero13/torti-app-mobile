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

    public interface OnDeliveryClickListener {
        void onDeliveryClick(Delivery delivery);
    }

    private OnDeliveryClickListener listener;

    private List<Delivery> deliveryList;

    public DeliveryAdapter(List<Delivery> deliveries, OnDeliveryClickListener listener) {
        this.deliveryList = deliveries;
        this.listener = listener;
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
        holder.bind(delivery, this.listener);
    }

    @Override
    public int getItemCount() {
        return this.deliveryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvCustomerName, txvCustomerLastName,
                txvDate;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvCustomerName = itemView.findViewById(R.id.txvCustomerName);
            this.txvCustomerLastName = itemView.findViewById(R.id.txvCustomerLastName);
            this.txvDate = itemView.findViewById(R.id.txvDate);
        }

        private void bind (final Delivery delivery, final OnDeliveryClickListener listener) {
            Customer customer = delivery.getCustomer();
            this.txvCustomerName.setText(String.format("Nombre: %s", customer.getName()));
            this.txvCustomerLastName.setText(String.format(
                    "Apellidos: %s %s", customer.getMaternalName(), customer.getPaternalName()));
            this.txvDate.setText(String.format("Fecha: %s", delivery.getDate()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeliveryClick(delivery);
                }
            });
        }
    }
}
