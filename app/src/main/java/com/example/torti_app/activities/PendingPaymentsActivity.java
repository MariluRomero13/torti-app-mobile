package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.torti_app.Adapters.PendingPaymentsAdapter;
import com.example.torti_app.Models.PendingPayments;
import com.example.torti_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PendingPaymentsActivity extends AppCompatActivity {

    private FloatingActionButton btnPagar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_payments);
        this.boot();
    }

    private void boot() {
        this.initializeElements();
        this.addListeners();
        this.loadListPendingPayments();
    }

    private void initializeElements() {
        btnPagar = findViewById(R.id.btn_pagar);
        recyclerView = findViewById(R.id.rv_pendingpayments);
    }

    private void addListeners() {
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        PendingPaymentsActivity.this, R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.bottom_sheet_pending_payments,
                                (ViewGroup) findViewById(R.id.bottom_sheet_pp)
                        );
                bottomSheetView.findViewById(R.id.btn_liquidar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(PendingPaymentsActivity.this, "Liquidar", Toast.LENGTH_SHORT).show();
                    }
                });

                bottomSheetView.findViewById(R.id.btn_abonar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(PendingPaymentsActivity.this, "Abonar", Toast.LENGTH_SHORT).show();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
    }

    private void loadListPendingPayments() {
        recyclerView.setLayoutManager(new LinearLayoutManager(PendingPaymentsActivity.this));
        recyclerView.setAdapter(new PendingPaymentsAdapter(this.pendingPaymentsList()));
    }

    private List<PendingPayments> pendingPaymentsList() {
        return new ArrayList<PendingPayments>() {{
            add(new PendingPayments("Tortillas", 8, 80.0));
            add(new PendingPayments("Salsas", 2, 24.0));
            add(new PendingPayments("Frijoles", 4, 64.0));
        }};
    }
}
