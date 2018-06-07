package com.example.danilwelter.ninjapizzacourier.Model.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Order {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("courier_id")
    @Expose
    private String courierId;

    @SerializedName("order_number")
    @Expose
    private int orderNumber;

    @SerializedName("date_time")
    @Expose
    private String dateTime;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public static Comparator<Order> COMPARE_BY_STATUS = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return Integer.compare(o1.getStatus(),o2.getStatus());
        }
    };
}
