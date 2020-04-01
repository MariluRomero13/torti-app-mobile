package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.torti_app.Fragments.DeliveryFragment;
import com.example.torti_app.Fragments.HistoryFragment;
import com.example.torti_app.R;

public class DeliveryHistoryActivity extends AppCompatActivity {
    private ViewGroup tabDelivery = null, tabHistory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_history);
        this.tabDelivery = findViewById(R.id.tabDelivery);
        this.tabHistory = findViewById(R.id.tabHistory);
        this.tabDelivery.performClick();

    }

    public void switchView(View view) {
        selectTab(view);
        switch (view.getId()) {
            case R.id.tabDelivery:
                changeFragment(new DeliveryFragment());
                break;
            case R.id.tabHistory:
                changeFragment(new HistoryFragment());
                break;
        }
    }

    private void selectTab (View v) {
        if(v == this.tabDelivery){
            ((GradientDrawable)this.tabHistory
                    .getBackground())
                    .setColor(getColor(R.color.colorNegroMate));
        }
        else {
            ((GradientDrawable)this.tabDelivery
                    .getBackground())
                    .setColor(getColor(R.color.colorNegroMate));
        }
        ((GradientDrawable)v.getBackground()).setColor(getColor(R.color.colorVerde));
    }

    private void changeFragment (Fragment currentFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, currentFragment).commit();
    }
}
