package com.example.danilwelter.ninjapizzacourier.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.danilwelter.ninjapizzacourier.R;



public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        /*GraphView graph = (GraphView) findViewById(R.id.graph);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);*/
    }
}
