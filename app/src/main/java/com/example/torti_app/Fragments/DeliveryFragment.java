package com.example.torti_app.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.torti_app.Adapters.DeliveryAdapter;
import com.example.torti_app.Models.Customer;
import com.example.torti_app.Models.Delivery;
import com.example.torti_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryFragment extends Fragment {

    public DeliveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_delivery, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new DeliveryAdapter(this.getList()));
        return rootView;
    }

    private List<Delivery> getList () {
        return new ArrayList<Delivery>(){{
            add(new Delivery("2020/02/18",
                    new Customer("Cynthia Suseth", "Macías", "Gómez")));
            add(new Delivery("2020/04/25",
                    new Customer("Misael", "De la O", "Gurrola")));
            add(new Delivery("2020/07/18",
                    new Customer("Marilu", "Martínez", "Romero")));
            add(new Delivery("2020/02/12",
                    new Customer("Pancrasia", "Fernnadez", "Gutierrez")));
            add(new Delivery("2020/02/28",
                    new Customer("Cesario", "Ulloa", "Torres")));
        }};
    }
}
