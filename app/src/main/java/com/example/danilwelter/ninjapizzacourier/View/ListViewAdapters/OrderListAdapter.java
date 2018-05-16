package com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
    Context context;
    MainPresenter presenter;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvAddress;
        public TextView tvComment;
        public TextView tvMinToDelivery;
        public TextView tvOrderStatus;
        public Button btOrderDone;
        public Button btCallToClient;
        public Button btNavigation;
        public Button btInfo;
        public CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            tvAddress = itemView.findViewById(R.id.loi_tv_address);
            tvComment = itemView.findViewById(R.id.loi_tv_comment);
            tvMinToDelivery = itemView.findViewById(R.id.loi_tv_minutes);
            tvOrderStatus = itemView.findViewById(R.id.loi_tv_order_status);

            btOrderDone = itemView.findViewById(R.id.loi_btn_order_done);
            btCallToClient = itemView.findViewById(R.id.loi_btn_call_to_client);
            btNavigation = itemView.findViewById(R.id.loi_btn_navigation);
            btInfo = itemView.findViewById(R.id.loi_btn_order_info);

            cardView = itemView.findViewById(R.id.cardViewOrder);
        }

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

    public String getStringOrderStatus(Order order){
        if (order.getOrderStatus() == OrderStatus.IN_ROUTE)
            return "В пути";
        else return "Завершен";
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Order order = orders.get(position);

        holder.tvAddress.setText(order.getAddress().toString());
        holder.tvComment.setText(order.getComment().toString());
        holder.tvMinToDelivery.setText("" + order.getMinutesToDeliver() + "");
        holder.tvOrderStatus.setText(getStringOrderStatus(order));
        //if(order.getOrderStatus() == OrderStatus.IN_ROUTE) holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorOrderInRoute));
        //else holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorOrderDone));


        holder.btOrderDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onOrderDonePressed(order.getOrderNumber());
            }
        });

        holder.btCallToClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "tel:" + order.getPhoneNumber().trim() ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                try {
                    context.startActivity(intent);
                }catch (SecurityException ex){
                    Toast.makeText(context,"Приложению заблокирован доступ к звонкам!", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.btNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Navigation
            }
        });

        holder.btInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String orderInfo;
                orderInfo = "Номер заказа: " + order.getOrderNumber();
                orderInfo += "\nДата и время заказа: " + order.getOrderDateTime();
                orderInfo += "\nСумма заказа, руб: " + order.getOrderPrice();
                orderInfo += "\nЧто заказано: " + order.getOrderContent();
                orderInfo += "\nПримечания: ";
                for (OrderNote note : order.getOrderNotes()
                        ) {
                    if(note == OrderNote.ATC) orderInfo += "\nЧитать комментарии;";
                    if(note == OrderNote.BTC) orderInfo += "\nКупон в подарок;";
                    if(note == OrderNote.DOT) orderInfo += "\nДоставка на время;";
                    if(note == OrderNote.PBC) orderInfo += "\nОплата картой;";

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Информация о заказе");
                builder.setMessage(orderInfo);
                builder.setCancelable(false).setNeutralButton("Закрыть", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


}
