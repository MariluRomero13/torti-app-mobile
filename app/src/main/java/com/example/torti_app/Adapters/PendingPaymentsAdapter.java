package com.example.torti_app.Adapters;

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

    public PendingPaymentsAdapter(List<PendingPayments> pendingPaymentsList){
       this.pendingPaymentsList = pendingPaymentsList;
    }

    @NonNull
    @Override
    public PendingPaymentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_pending_payments, viewGroup, false);
        PendingPaymentsAdapter.ViewHolder vh = new PendingPaymentsAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PendingPaymentsAdapter.ViewHolder holder, final int i) {
        holder.txtProduct.setText(pendingPaymentsList.get(i).getProduct());
        holder.txtQuantity.setText(String.valueOf(pendingPaymentsList.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return pendingPaymentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtProduct, txtQuantity;
        public ViewHolder(View itemView){
            super(itemView);
            txtProduct = itemView.findViewById(R.id.txt_producto);
            txtQuantity = itemView.findViewById(R.id.txt_cantidad);
        }
    }
}