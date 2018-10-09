package com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.danilwelter.ninjapizzacourier.Model.Entities.Order;
import com.example.danilwelter.ninjapizzacourier.Model.Entities.StatItem;
import com.example.danilwelter.ninjapizzacourier.Presenters.MainPresenter;
import com.example.danilwelter.ninjapizzacourier.R;

import java.util.ArrayList;

public class StatListAdapter extends RecyclerView.Adapter<StatListAdapter.ViewHolder> {

    ArrayList<StatItem> stats;
    MainPresenter presenter;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvDay;
        public TextView tvCountDay;

        public ViewHolder(View itemView){
            super(itemView);
            tvDay = itemView.findViewById(R.id.lsi_tv_day);
            tvCountDay = itemView.findViewById(R.id.lsi_tv_day_count);
        }

    }


    public StatListAdapter(ArrayList<StatItem> pStats, Context pContext, MainPresenter pPresenter){
        stats = pStats;
        context = pContext;
        presenter = pPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_stat_item,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final StatListAdapter.ViewHolder holder, int position) {
        final StatItem stat = stats.get(position);

        holder.tvDay.setText(stat.getDate().toString());
        holder.tvCountDay.setText("" + stat.getCount());
    }

    @Override
    public int getItemCount() {
        return stats.size();
    }
}
