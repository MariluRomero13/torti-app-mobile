package com.example.torti_app.Util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.torti_app.Fragments.SaleFragment;
import com.example.torti_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialogSale extends BottomSheetDialogFragment
        implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if(v == this.btnCancel) {
            this.dismiss();
        } else {
            this.listener.onButtonClicked(v, this.edtDeposit.getText().toString());
        }
    }

    public interface BottomSaleDialogListener {
        void onButtonClicked(View v, String deposit);
    }

    private ViewGroup vgDeposit = null;
    private Button btnConfirm = null, btnCancel = null;
    private EditText edtDeposit = null;
    private TextView txvQuestion = null;
    private BottomSaleDialogListener listener = null;
    private boolean isCashPayment = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.isCashPayment = getArguments().getBoolean("isCashPayment");
        }
        View rootView = inflater.inflate(R.layout.layout_bottom_sheet_dialog, container, false);
        this.vgDeposit = rootView.findViewById(R.id.vgDeposit);
        this.btnCancel = rootView.findViewById(R.id.btnCancel);
        this.btnConfirm = rootView.findViewById(R.id.btnConfirm);
        this.txvQuestion = rootView.findViewById(R.id.txvQuestion);
        this.edtDeposit = rootView.findViewById(R.id.edtDeposit);
        this.btnConfirm.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);
        if(this.isCashPayment) {
            this.vgDeposit.setVisibility(View.GONE);
            this.txvQuestion.setText(R.string.label_payment_credit);
        }
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (BottomSaleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
}
