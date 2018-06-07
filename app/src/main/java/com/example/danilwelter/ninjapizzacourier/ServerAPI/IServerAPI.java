package com.example.danilwelter.ninjapizzacourier.ServerAPI;

import com.example.danilwelter.ninjapizzacourier.Model.Entities.Order;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IServerAPI {

    @GET("/ordersAPI/?action=select&courier=delivery12345")
    Call<List<Order>> getOrders ();

}
