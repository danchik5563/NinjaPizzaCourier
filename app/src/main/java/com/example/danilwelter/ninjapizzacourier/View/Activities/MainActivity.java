package com.example.danilwelter.ninjapizzacourier.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.danilwelter.ninjapizzacourier.Model.Entities.Order;
import com.example.danilwelter.ninjapizzacourier.Presenters.MainPresenter;
import com.example.danilwelter.ninjapizzacourier.R;
import com.example.danilwelter.ninjapizzacourier.ServerAPI.OrderService;
import com.example.danilwelter.ninjapizzacourier.View.ListViewAdapters.OrderListAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private ArrayList<Order> orders;
    private SwipeRefreshLayout taskSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginActivity loginActivity = new LoginActivity();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        init();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_my_orders) {
            refreshList();
        } else if (id == R.id.nav_my_stat) {

        } else if (id == R.id.nav_call_to_admin) {

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_my_stat){
            Intent intent = new Intent(this, StatActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        presenter.detachView();

    }

    private void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new MainPresenter();
        taskSwipeRefreshLayout = findViewById(R.id.swipe_container);
        orders = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleViewOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderListAdapter adapter = new OrderListAdapter(orders,this,presenter);
        recyclerView.setAdapter(adapter);
        final SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipe_container);

        //region SWIPE RERESH LISTENNER
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                OrderService.getApi().getOrders().enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        if (response.isSuccessful()) {
                            orders.clear();
                            orders.addAll(response.body());
                            Collections.sort(orders, Order.COMPARE_BY_STATUS);
                            Collections.reverse(orders);
                            recyclerView.getAdapter().notifyDataSetChanged();
                            taskSwipeRefreshLayout.setRefreshing(false);

                        } else {
                            showToast("response code " + response.code());
                            taskSwipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        showToast(t.getMessage());
                        taskSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        //endregion

        presenter.attachView(this);

    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void refreshList(){
        OrderService.getApi().getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    orders.clear();
                    orders.addAll(response.body());
                    Collections.sort(orders, Order.COMPARE_BY_STATUS);
                    Collections.reverse(orders);
                    recyclerView.getAdapter().notifyDataSetChanged();

                } else {
                    showToast("response code " + response.code());
                    taskSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                showToast(t.getMessage());
                taskSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }





}
