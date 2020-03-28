package com.example.torti_app.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.torti_app.Adapters.DeliveryAdapter;
import com.example.torti_app.Adapters.HistoryAdapter;
import com.example.torti_app.Models.Customer;
import com.example.torti_app.Models.History;
import com.example.torti_app.R;
import com.example.torti_app.activities.HistoryDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new HistoryAdapter(this.getList(), new HistoryAdapter.OnHistoryClickListener() {
            @Override
            public void onHistoryClick(History history) {
                Toast.makeText(getContext(),
                        history.getCustomer().getName(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), HistoryDetailActivity.class));
            }
        }));
        return rootView;
    }

    private List<History> getList () {
        return new ArrayList<History>(){{
            add(new History("2019/02/21",
                    new Customer("Mortilio", "Gonzalez", "De montes")));
            add(new History("2019/10/02",
                    new Customer("Petronila", "Montes", "De oca")));
            add(new History("2019/02/21",
                    new Customer("Luis", "Perez", "Perez")));
        }};
    }
}
