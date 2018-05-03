package com.example.danilwelter.ninjapizzacourier.Model.Entities;

import com.example.danilwelter.ninjapizzacourier.Model.Enums.OrderNote;
import com.example.danilwelter.ninjapizzacourier.Model.Enums.OrderStatus;

import java.util.ArrayList;

public class Order {

    private int _id;
    public int getId(){
        return _id;
    }

    private int _orderNumber;
    public int getOrderNumber(){return _orderNumber;}

    private String _OrderDateTime;
    public String getOrderDateTime(){return _OrderDateTime;}

    private int _minutesToDeliver;
    public int getMinutesToDeliver(){return _minutesToDeliver;}

    private int _orderPrice;
    public int getOrderPrice(){return _orderPrice;}

    private String _orderContent;
    public String getOrderContent(){return _orderContent;}

    private OrderStatus _orderStatus;
    public OrderStatus getOrderStatus(){return _orderStatus;}

    private String _comment;
    public String getComment(){return _comment;}

    private String _address;
    public String getAddress(){return _address;}

    private String _phoneNumber;
    public String getPhoneNumber(){return _phoneNumber;}

    private ArrayList<OrderNote> _notes;
    public ArrayList<OrderNote> getOrderNotes(){return _notes;}

    public Order(int id, int orderNumber, String orderDateTime, int minutesToDeliver,
                 int orderPrice, String orderContent, OrderStatus orderStatus,
                 String comment, String address, String phoneNumber, ArrayList<OrderNote> notes){
        _id = id;
        _orderNumber = orderNumber;
        _OrderDateTime = orderDateTime;
        _minutesToDeliver = minutesToDeliver;
        _orderPrice = orderPrice;
        _orderContent = orderContent;
        _orderStatus = orderStatus;
        _comment = comment;
        _address = address;
        _phoneNumber = phoneNumber;
        _notes = notes;
    }
}
