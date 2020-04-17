package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.torti_app.Fragments.MapFragment;
import com.example.torti_app.Fragments.SaleFragment;
import com.example.torti_app.Models.Customer;
import com.example.torti_app.Models.Delivery;
import com.example.torti_app.R;

public class MapSaleActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewGroup tabSale = null, tabMap = null;
    private Delivery delivery = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_sale);
        this.delivery = getIntent().getParcelableExtra("delivery");
        this.tabSale = findViewById(R.id.tabSale);
        this.tabMap = findViewById(R.id.tabMap);
        this.tabMap.setOnClickListener(this);
        this.tabSale.setOnClickListener(this);
        this.tabSale.performClick();
    }

    @Override
    public void onClick(View v) {
        ((GradientDrawable)v.getBackground())
                .setColor(getColor(R.color.colorVerde));
        Fragment currentFragment = null;
        switch (v.getId()) {
            case R.id.tabMap:
                ((GradientDrawable)this.tabSale
                        .getBackground())
                        .setColor(getColor(R.color.colorNegroMate));
                currentFragment = new MapFragment();
                changeFragment(new MapFragment());
                break;
            case R.id.tabSale:
                ((GradientDrawable)this.tabMap
                        .getBackground())
                        .setColor(getColor(R.color.colorNegroMate));
                currentFragment = new SaleFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("delivery", this.delivery);
                currentFragment.setArguments(bundle);
                changeFragment(currentFragment);
                break;
        }
    }

    private void changeFragment (Fragment currentFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, currentFragment).commit();
    }
}
