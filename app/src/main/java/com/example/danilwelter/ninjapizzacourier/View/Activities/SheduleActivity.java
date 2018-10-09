package com.example.danilwelter.ninjapizzacourier.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.danilwelter.ninjapizzacourier.Model.Entities.SheduleItem;
import com.example.danilwelter.ninjapizzacourier.Model.Entities.StatItem;
import com.example.danilwelter.ninjapizzacourier.Presenters.MainPresenter;
import com.example.danilwelter.ninjapizzacourier.R;
import com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters.SheduleListAdapter;
import com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters.StatListAdapter;

import java.util.ArrayList;

public class SheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);

        MainPresenter presenter = new MainPresenter();
        ArrayList<SheduleItem> shedules = new ArrayList<>();
        shedules.add(new SheduleItem(1, "delivery55456", 2018, 23, "10:30", "10:30","10:30","10:30","10:30","10:30","Выходной"));
        shedules.add(new SheduleItem(2, "delivery55456", 2018, 24, "10:30", "17:00","10:30","10:30","10:30","Выходной","10:30"));

        RecyclerView recyclerView = findViewById(R.id.activity_shedule_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SheduleListAdapter adapter = new SheduleListAdapter(shedules, this, presenter);
        recyclerView.setAdapter(adapter);
    }
}
