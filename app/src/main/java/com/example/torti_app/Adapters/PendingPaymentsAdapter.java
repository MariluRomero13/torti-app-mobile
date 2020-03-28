package com.example.torti_app.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app.Models.PendingPayments;
import com.example.torti_app.R;

import java.util.List;

public class PendingPaymentsAdapter extends RecyclerView.Adapter<PendingPaymentsAdapter.ViewHolder>{

    private List<PendingPayments> pendingPaymentsList;

    public PendingPaymentsAdapter(List<PendingPayments> pendingPaymentsList) {
        this.pendingPaymentsList = pendingPaymentsList;
    }

    @NonNull
    @Override
    public PendingPaymentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_pending_payments, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingPaymentsAdapter.ViewHolder holder, int i) {
        PendingPayments pendingPayments = this.pendingPaymentsList.get(i);
        holder.bind(pendingPayments);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtProducto, txtCantidad, txtTotal;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProducto = itemView.findViewById(R.id.txt_producto);
            txtCantidad = itemView.findViewById(R.id.txt_cantidad);
            txtTotal = itemView.findViewById(R.id.txt_total);
        }

        private void bind (final PendingPayments pendingPayments) {
            txtProducto.setText(pendingPayments.getProducto());
            txtCantidad.setText(pendingPayments.getCantidad());
            txtTotal.setText(String.valueOf(pendingPayments.getTotal()));
        }
    }

}