package com.example.danilwelter.ninjapizzacourier.Presenters;

import com.example.danilwelter.ninjapizzacourier.Model.OrderService;
import com.example.danilwelter.ninjapizzacourier.View.Activities.MainActivity;

public class MainPresenter {

    private MainActivity view;
    private OrderService orderService;

    public MainPresenter(OrderService pModel){
        orderService = pModel;
    }

    public void attachView(MainActivity mainActivity){
        view = mainActivity;

        //TODO Реализовать в потоке
        view.fillOrderList(orderService.getOrderList());
    }

    public void detachView(){
        view = null;
    }

    public void onOrderDonePressed(int pOrderNumber){

    }

    public void onListRefreshed(){

        //TODO Реализовать в потоке
        view.fillOrderList(orderService.getOrderList());
    }


}
