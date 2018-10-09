package com.example.danilwelter.ninjapizzacourier.View.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
    private SharedPreferences settings;

    private String adminPhoneNumber;

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

        switch (id){

            //region My orders
            case R.id.nav_my_orders : {

                refreshList();

                break;
            }//endregion

            //region My Stat
            case R.id.nav_my_stat : {

                Intent intent = new Intent(this, StatActivity.class);
                startActivity(intent);

                break;
            }//endregion

            //region My shedule
            case R.id.nav_my_shedule : {

                Intent intent = new Intent(this, SheduleActivity.class);
                startActivity(intent);

                break;
            }//endregion

            //region Call to admin
            case R.id.nav_call_to_admin : {

                adminPhoneNumber = settings.getString("pref_admin_phone", null);
                if((adminPhoneNumber == null) || (adminPhoneNumber.equals(""))) {
                    showToast("Для начала задайте номер администратора в настройках.");
                } else {
                    String uri = "tel:" + adminPhoneNumber.trim() ;
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(uri));
                    try {
                        this.startActivity(intent);
                    }catch (SecurityException ex){
                        showToast("Приложению заблокирован доступ к звонкам!");
                    }
                }

                break;
            }//endregion

            //region Settings
            case R.id.nav_settings : {

                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);

                break;
            }//endregion

            //region Bug report
            case R.id.nav_bug_report : {

                Intent intent = new Intent(this, BugReportActivity.class);
                startActivity(intent);

                break;
            }//endregion

            //region About program
            case R.id.nav_about_program : {

                String aboutProgram = "Данное приложение предназначено для курьеров службы доставки NinjaPizza.\nОно предоставляет удобный пользовательский интерфейс для работы с заказами.\nВерсия приложения 1.0";

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("О программе")
                        .setMessage(aboutProgram)
                        .setIcon(R.drawable.ico)
                        .setCancelable(false)
                        .setNegativeButton("ОК",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

                break;
            }//endregion

            default: break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();


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

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        presenter = new MainPresenter();
        taskSwipeRefreshLayout = findViewById(R.id.swipe_container);
        orders = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleViewOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OrderListAdapter adapter = new OrderListAdapter(orders, this, presenter);
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
                            swipeRefresh.setRefreshing(false);

                        } else {
                            showToast("response code " + response.code());
                            swipeRefresh.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        showToast(t.getMessage());
                        swipeRefresh.setRefreshing(false);
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
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                showToast(t.getMessage());
            }
        });
    }





}
