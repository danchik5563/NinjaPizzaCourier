package com.example.danilwelter.ninjapizzacourier.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.danilwelter.ninjapizzacourier.Model.Entities.StatItem;
import com.example.danilwelter.ninjapizzacourier.Presenters.MainPresenter;
import com.example.danilwelter.ninjapizzacourier.R;
import com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters.OrderListAdapter;
import com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters.StatListAdapter;

import java.util.ArrayList;


public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);


        MainPresenter presenter = new MainPresenter();
        ArrayList<StatItem> stats = new ArrayList<>();
        stats.add(new StatItem("04.06.2018", 17));
        stats.add(new StatItem("05.06.2018", 13));
        stats.add(new StatItem("06.06.2018", 19));
        stats.add(new StatItem("07.06.2018", 11));
        stats.add(new StatItem("08.06.2018", 7));
        stats.add(new StatItem("09.06.2018", 16));

        stats.add(new StatItem("11.06.2018", 14));
        stats.add(new StatItem("12.06.2018", 3));
        stats.add(new StatItem("13.06.2018", 12));
        stats.add(new StatItem("14.06.2018", 19));
        stats.add(new StatItem("15.06.2018", 26));
        stats.add(new StatItem("17.06.2018", 33));



        TextView tvMonth = findViewById(R.id.activity_stat_tv_current_month);
        TextView tvWeek = findViewById(R.id.activity_stat_tv_current_week);

        tvMonth.setText("190");
        tvWeek.setText("107");

        RecyclerView recyclerView = findViewById(R.id.rv_stats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StatListAdapter adapter = new StatListAdapter(stats, this, presenter);
        recyclerView.setAdapter(adapter);
    }
}
