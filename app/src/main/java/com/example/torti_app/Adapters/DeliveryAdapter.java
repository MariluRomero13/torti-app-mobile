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
        holder.bind(delivery, this.listener);
    }

    @Override
    public int getItemCount() {
        return this.deliveryList.size();
    }

    public void setListener(OnDeliveryClickListener listener) {
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvCustomerName;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvCustomerName = itemView.findViewById(R.id.txvCustomerName);
        }

        private void bind (final Delivery delivery, final OnDeliveryClickListener listener) {
            Customer customer = delivery.getCustomer();
            this.txvCustomerName.setText(customer.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeliveryClick(delivery);
                }
            });
        }
    }
}
