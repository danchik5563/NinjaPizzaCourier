package com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilwelter.ninjapizzacourier.Model.Entities.Order;
import com.example.danilwelter.ninjapizzacourier.Model.Enums.OrderNote;
import com.example.danilwelter.ninjapizzacourier.Model.Enums.OrderStatus;
import com.example.danilwelter.ninjapizzacourier.Presenters.MainPresenter;
import com.example.danilwelter.ninjapizzacourier.R;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    ArrayList<Order> orders;
    MainPresenter presenter;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvAddress;
        public TextView tvComment;
        public TextView tvOrderDateTime;
        public TextView tvMinToDelivery;
        public TextView tvOrderStatus;
        public Button btOrderDone;
        public Button btInfo;
        public CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            tvAddress = itemView.findViewById(R.id.loi_tv_address);
            tvComment = itemView.findViewById(R.id.loi_tv_comment);
            tvOrderDateTime = itemView.findViewById(R.id.loi_tv_order_date_time);
            tvMinToDelivery = itemView.findViewById(R.id.loi_tv_minutes);
            tvOrderStatus = itemView.findViewById(R.id.loi_tv_status);
            btOrderDone = itemView.findViewById(R.id.loi_btn_order_done);
            btInfo = itemView.findViewById(R.id.loi_btn_order_info);
            cardView = itemView.findViewById(R.id.cardViewOrder);
        }

    }

    public String getMinutesToDeliver(String dateTime){
        //TODO Сделать функцию остатка по минутам
        return "15";
    }

    public OrderListAdapter(ArrayList<Order> pOrders, Context pContext, MainPresenter pPresenter){
        orders = pOrders;
        context = pContext;
        presenter = pPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order_item,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Order order = orders.get(position);

        if(order.getStatus() == 0)
        {
            holder.btOrderDone.setEnabled(false);
            holder.btOrderDone.setText("Доставлен");
            holder.tvOrderStatus.setText("Доставлен");
        } else {
            holder.btOrderDone.setEnabled(true);
            holder.btOrderDone.setText("Отметить");
            holder.tvOrderStatus.setText("В пути");
        }

        holder.tvAddress.setText(order.getAddress().toString());
        holder.tvComment.setText(order.getComment().toString());
        holder.tvOrderDateTime.setText(order.getDateTime());
        holder.tvMinToDelivery.setText(getMinutesToDeliver(order.getDateTime()).toString());
        holder.btInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onInfoPressed(order);
            }
        });
        holder.btOrderDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                holder.btOrderDone.setEnabled(false);
                holder.btOrderDone.setText("Доставлен");
                presenter.onOrderDonePressed(order.getOrderNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


}
