package com.example.danilwelter.ninjapizzacourier.Model;

import com.example.danilwelter.ninjapizzacourier.Model.Entities.Order;
import com.example.danilwelter.ninjapizzacourier.Model.Enums.OrderNote;
import com.example.danilwelter.ninjapizzacourier.Model.Enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    public ArrayList<Order> getOrderList(){

        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<OrderNote> on = new ArrayList<>();
        on.add(OrderNote.ATC);
        on.add(OrderNote.BTC);

        orders.add(new Order(145587,145587, "22.11.17, 23:34",
                56, 649, new String("От Рафа"),
                OrderStatus.IN_ROUTE, new String("позвонить!!. Сдача с 1000"), new String("Карла Маркса д. 96"), "89135223663", on));
        orders.add(new Order(145585,145585, "22.11.17, 23:23",
                20, 1218, new String("Цезарь, Гавайская, Bigcheese 26см + Ниндзя 26см"),
                OrderStatus.IN_ROUTE, new String("БЕЗ СДАЧИ!!"), new String("Петра Словцова д. 16 кв.47 под.2 эт.6"), "89069155545", on));
        orders.add(new Order(145584,145584, "22.11.17, 23:10",
                35, 744, new String("Coca-Cola 1 л, КомбоПицца (Солянка Злодейская + Классическая)"),
                OrderStatus.IN_ROUTE, new String("Сдача с 1000"), new String("Водопьянова д. 19 кв.6 под.1 эт.2"), "89069158875", on));
        orders.add(new Order(145581,145581, "22.11.17, 22:42",
                20, 1168, new String("Английский завтрак, Цезарь, Bigcheese 26см + Ниндзя 26см"),
                OrderStatus.IN_ROUTE, new String("Сдача с 2000 ПОЗВОНИТЬ"), new String("Яковлева д. 59 под.1"), "89065588875", on));
        orders.add(new Order(145571,145571, "22.11.17, 22:06",
                15, 889, new String("Coca-Cola 0,5 л, Солянка Злодейская, Nestea 1 л, Coca-Cola 0,5 л"),
                OrderStatus.DELIVERED, new String("бокал!!!. Карта"), new String("Батурина д. 30 кв.42 под.1 эт.8"), "89064478875", on));

        return orders;
    }

    public int setOrderStatusDone(String pOrderNumber){
        return 0;
    }
}
