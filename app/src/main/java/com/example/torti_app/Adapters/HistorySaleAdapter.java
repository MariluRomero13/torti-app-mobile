package com.example.torti_app.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app.Models.HistorySale;
import com.example.torti_app.R;

import java.util.List;

public class HistorySaleAdapter extends RecyclerView.Adapter<HistorySaleAdapter.ViewHolder> {
    private List<HistorySale> historySaleList;

    public HistorySaleAdapter(List<HistorySale> historySaleList) {
        this.historySaleList = historySaleList;
    }

    @NonNull
    @Override
    public HistorySaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_history_sale, viewGroup, false);
        HistorySaleAdapter.ViewHolder vh = new HistorySaleAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorySaleAdapter.ViewHolder holder, int i) {
        holder.txtProducto.setText(historySaleList.get(i).getProduct());
        holder.txtCantidad.setText(String.valueOf(historySaleList.get(i).getQuantity()));
        holder.txtTotal.setText(String.format("$ %s", historySaleList.get(i).getPrice()));
    }

    @Override
    public int getItemCount() {
        return historySaleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtProducto, txtCantidad, txtTotal;
        public ViewHolder(View itemView){
            super(itemView);
            txtProducto = itemView.findViewById(R.id.txt_producto);
            txtCantidad = itemView.findViewById(R.id.txt_cantidad);
            txtTotal = itemView.findViewById(R.id.txt_totalPagar);
        }
    }
}
