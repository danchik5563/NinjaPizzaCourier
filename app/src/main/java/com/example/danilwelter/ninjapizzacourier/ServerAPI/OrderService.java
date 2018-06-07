package com.example.danilwelter.ninjapizzacourier.ServerAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderService {
    private static final OrderService ourInstance = new OrderService();
    private static IServerAPI api;
    private Retrofit retrofit;
    private Gson gson;

    public static OrderService getInstance() {
        return ourInstance;
    }

    private OrderService() {
        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://danchinj.beget.tech")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(IServerAPI.class);
    }

    public static IServerAPI getApi(){
        return api;
    }
}
