package com.example.torti_app.Adapters;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.torti_app.Models.Product;
import com.example.torti_app.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public List<Product> products = null;

    public ProductAdapter (List<Product> products){
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_products, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Product product = this.products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public void clear () {
        for (Product product: this.products) {
            product.setQuantity(0);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvName = null, txvUnityPrice = null;
        private EditText edtQuantity = null;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txvName = itemView.findViewById(R.id.txvProductName);
            this.txvUnityPrice = itemView.findViewById(R.id.txvUnityPrice);
            this.edtQuantity = itemView.findViewById(R.id.edtQuantity);
        }

        @SuppressLint("DefaultLocale")
        private void bind (final Product product) {
            this.txvName.setText(product.getName());
            this.txvUnityPrice.setText(String.format("Precio unitario: $%.2f", product.getUnitPrice()));
            this.edtQuantity.getText().clear();
            this.edtQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.toString().trim().length() == 0)
                        s = "0";
                    product.setQuantity(Integer.parseInt(s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }
}
