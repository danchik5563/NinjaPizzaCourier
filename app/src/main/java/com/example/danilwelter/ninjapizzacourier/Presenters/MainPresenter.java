package com.example.danilwelter.ninjapizzacourier.Presenters;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.danilwelter.ninjapizzacourier.Model.Entities.Order;
import com.example.danilwelter.ninjapizzacourier.View.Activities.MainActivity;
import com.example.danilwelter.ninjapizzacourier.View.Activities.OrderActivity;

public class MainPresenter {

    private MainActivity view;
    private OrderActivity orderView;

    public MainPresenter(){

    }

    public void attachView(MainActivity mainActivity){
        view = mainActivity;

        //TODO Реализовать в потоке
        //view.fillOrderList(orderService.getOrderList());
    }

    public void detachView(){
        view = null;
    }

    public void onOrderDonePressed(int pOrderNumber){

    }

    public void onListRefreshed(SwipeRefreshLayout swipeRefresh){


    }

    public void onInfoPressed(Order order){
        orderView = new OrderActivity();
        Intent intent = new Intent(view, OrderActivity.class);
        intent.putExtra("orderNumber", order.getOrderNumber());
        intent.putExtra("orderAddress", order.getAddress());
        intent.putExtra("orderComment", order.getComment());
        intent.putExtra("orderDateTime", order.getDateTime());
        intent.putExtra("orderContent", order.getContent());
        intent.putExtra("orderPhoneNumber", order.getPhoneNumber());
        if(order.getStatus() == 1) intent.putExtra("orderStatus", "В пути");
        else intent.putExtra("orderStatus", "Завершен");
        view.startActivity(intent);
    }


}
