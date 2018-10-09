package com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.danilwelter.ninjapizzacourier.Model.Entities.SheduleItem;
import com.example.danilwelter.ninjapizzacourier.Model.Entities.StatItem;
import com.example.danilwelter.ninjapizzacourier.Presenters.MainPresenter;
import com.example.danilwelter.ninjapizzacourier.R;

import java.util.ArrayList;

public class SheduleListAdapter extends RecyclerView.Adapter<SheduleListAdapter.ViewHolder>{

    ArrayList<SheduleItem> shedules;
    MainPresenter presenter;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvWeek;
        public TextView tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday, tvSunday;

        public ViewHolder(View itemView){
            super(itemView);
            tvWeek = itemView.findViewById(R.id.lsi_tv_week);
            tvMonday = itemView.findViewById(R.id.lsi_tv_monday);
            tvTuesday = itemView.findViewById(R.id.lsi_tv_tuesday);
            tvWednesday = itemView.findViewById(R.id.lsi_tv_wednesday);
            tvThursday = itemView.findViewById(R.id.lsi_tv_thursday);
            tvFriday = itemView.findViewById(R.id.lsi_tv_friday);
            tvSaturday = itemView.findViewById(R.id.lsi_tv_saturday);
            tvSunday = itemView.findViewById(R.id.lsi_tv_sunday);


        }

    }


    public SheduleListAdapter(ArrayList<SheduleItem> pShedules, Context pContext, MainPresenter pPresenter){
        shedules = pShedules;
        context = pContext;
        presenter = pPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_shedule_item,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SheduleListAdapter.ViewHolder holder, int position) {
        final SheduleItem shedule = shedules.get(position);

        if(shedule.get_weekOfYear() == 23) holder.tvWeek.setText("4.06.2018-10.06.2018");
        if(shedule.get_weekOfYear() == 24) holder.tvWeek.setText("11.06.2018-17.06.2018");
        holder.tvMonday.setText("Пн " + shedule.get_monday());
        holder.tvTuesday.setText("Вт " + shedule.get_tuesday());
        holder.tvWednesday.setText("Ср " + shedule.get_wednesday());
        holder.tvThursday.setText("Чт " + shedule.get_thursday());
        holder.tvFriday.setText("Пт " + shedule.get_friday());
        holder.tvSaturday.setText("Сб " + shedule.get_saturday());
        holder.tvSunday.setText("Вс " + shedule.get_sunday());
    }

    @Override
    public int getItemCount() {
        return shedules.size();
    }
}
