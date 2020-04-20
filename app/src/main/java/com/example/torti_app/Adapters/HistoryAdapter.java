package com.example.torti_app.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app.Models.History;
import com.example.torti_app.R;
import com.example.torti_app.activities.HistoryDetailActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> historyList;

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_histories, viewGroup, false);
        HistoryAdapter.ViewHolder vh = new HistoryAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.ViewHolder holder, final int i) {
        holder.txtCliente.setText(historyList.get(i).getCustomer());
        holder.txtTotal.setText(String.format("Total: %s", historyList.get(i).getTotal()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("histories", historyList.get(i));

                Intent intent = new Intent(v.getContext(), HistoryDetailActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCliente, txtTotal;
        public ViewHolder(View itemView){
            super(itemView);
            txtCliente = itemView.findViewById(R.id.txt_cliente);
            txtTotal = itemView.findViewById(R.id.txt_totalPagar);
        }
    }
}
