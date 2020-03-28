package com.example.torti_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app.Models.Customer;
import com.example.torti_app.Models.History;
import com.example.torti_app.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public interface OnHistoryClickListener {
        void onHistoryClick(History history);
    }

    private List<History> historyList;
    private OnHistoryClickListener listener;

    public HistoryAdapter (List<History> historyList, OnHistoryClickListener listener) {
        this.historyList = historyList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_histories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = this.historyList.get(position);
        holder.bind(history, this.listener);
    }

    @Override
    public int getItemCount() {
        return this.historyList.size();
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

        private void bind (final History history, final OnHistoryClickListener listener) {
            Customer customer = history.getCustomer();
            this.txvCustomerName.setText(String.format("Nombre: %s", customer.getName()));
            this.txvCustomerLastName.setText(String.format(
                    "Apellidos: %s %s", customer.getMaternalName(), customer.getPaternalName()));
            this.txvDate.setText(String.format("Fecha: %s", history.getDate()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHistoryClick(history);
                }
            });
        }
    }
}
