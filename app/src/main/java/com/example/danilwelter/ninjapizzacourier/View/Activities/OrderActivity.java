package com.example.danilwelter.ninjapizzacourier.View.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilwelter.ninjapizzacourier.Presenters.Geocoder;
import com.example.danilwelter.ninjapizzacourier.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvAddress;
    private TextView tvComment;
    private TextView tvOrderDateTime;
    private TextView tvOrderContent;
    private TextView tvOrderNumber;
    private TextView tvOrderStatus;
    private Button btOrderDone;
    private Button btCallToClient;
    private Button btNavigation;
    private Button btShareWhatsApp;
    private Intent intent;
    private NestedScrollView scrollView;
    private WebView webView;

    String address;
    String comment;
    String dateTime;
    String orderContent;
    String orderStatus;
    int orderNumber;

    boolean READY_TO_NAVIGATE = false;
    Geocoder.Coordinates coordinates;
    String NAVI_APP = "2gis";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        init();


    }

    @SuppressLint("ResourceAsColor")
    private void init(){
        intent = getIntent();

        //region INIT_VIEW
        tvAddress = findViewById(R.id.activity_order_tv_address);
        tvComment = findViewById(R.id.activity_order_tv_comment);
        tvOrderDateTime = findViewById(R.id.activity_order_tv_order_date_time);
        tvOrderContent = findViewById(R.id.activity_order_tv_order_content);
        tvOrderNumber = findViewById(R.id.activity_order_tv_order_number);
        tvOrderStatus = findViewById(R.id.activity_order_tv_order_status);
        btOrderDone = findViewById(R.id.activity_order_bt_order_done);
        btCallToClient = findViewById(R.id.activity_order_bt_call_to_client);
        btNavigation = findViewById(R.id.activity_order_bt_navi);
        btShareWhatsApp = findViewById(R.id.activity_order_bt_share_wa);
        webView = findViewById(R.id.activity_order_wv_map);
        scrollView = findViewById(R.id.activity_order_scroll_view);
        //endregion

        //region GET_EXTRAS
        address = intent.getStringExtra("orderAddress");
        comment = intent.getStringExtra("orderComment");
        dateTime = intent.getStringExtra("orderDateTime");
        orderContent = intent.getStringExtra("orderContent");
        orderNumber = intent.getIntExtra("orderNumber", 000000);
        orderStatus = intent.getStringExtra("orderStatus");
        //endregion

        GeocodeTask geocode = new GeocodeTask();
        geocode.execute();

        tvAddress.setText(address);
        tvComment.setText(comment);
        tvOrderDateTime.setText(dateTime);
        tvOrderContent.setText(orderContent);
        tvOrderNumber.setText("" + orderNumber);
        tvOrderStatus.setText(orderStatus);

        if(orderStatus.equals("Завершен"))
        {
            btOrderDone.setEnabled(false);
            btOrderDone.setText("Доставлен");
        }

        //region BT_SET_CLICK_LISTENNER
        btOrderDone.setOnClickListener(this);
        btCallToClient.setOnClickListener(this);
        btNavigation.setOnClickListener(this);
        btShareWhatsApp.setOnClickListener(this);
        //endregion

        showYandexMap();

        scrollView.scrollTo(0,0);
    }

    private void showYandexMap(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        Geocoder.DataWithBaseUrl data = new Geocoder().getDataWitbBaseUrls(address);
        webView.loadDataWithBaseURL(data.getBaseUrl(),data.getHtml(),data.getMimeType(),data.getEncoding(),data.getHistoryUrl());
        webView.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //region CASE_CALL_TO_CLIENT
            case R.id.activity_order_bt_call_to_client : {
                String uri = "tel:" + intent.getStringExtra("orderPhoneNumber").trim() ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                try {
                    this.startActivity(intent);
                }catch (SecurityException ex){
                    Toast.makeText(this,"Приложению заблокирован доступ к звонкам!", Toast.LENGTH_LONG).show();
                }
                break;
            }//endregion

            //region CASE_ORDER_DONE
            case R.id.activity_order_bt_order_done : {

                scrollView.scrollTo(0,0);
                break;
            }//endregion

            //region CASE_NAVIGATOR
            case R.id.activity_order_bt_navi : {

                if(!READY_TO_NAVIGATE){
                    showToast("Не удалось построить маршрут, попробуйте построить мршрут в навигаторе вручную.");
                } else{

                    switch (NAVI_APP){
                        case "2gis" : {

                            String uriString = "dgis://2gis.ru/routeSearch/rsType/car/to/" + coordinates.getY() + "," + coordinates.getX();
                            Uri uri = Uri.parse(uriString);

                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);;

                            PackageManager pm = getPackageManager();
                            List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);

                            if(infos == null || infos.size() == 0){
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("market://details?id=ru.dublgis.dgismobile"));
                                startActivity(intent);
                            } else {
                                startActivity(intent);
                            }

                            break;
                        }

                        case "yandex" : {

                            Intent intent = new Intent("ru.yandex.yandexnavi.action.BUILD_ROUTE_ON_MAP");
                            intent.setPackage("ru.yandex.yandexnavi");

                            PackageManager pm = getPackageManager();
                            List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);

                            // Проверяем, установлен ли Яндекс.Навигатор
                            if (infos == null || infos.size() == 0) {
                                // Если нет - будем открывать страничку Навигатора в Google Play
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("market://details?id=ru.yandex.yandexnavi"));
                            } else {
                                intent.putExtra("lat_to", coordinates.getX());
                                intent.putExtra("lon_to", coordinates.getY());
                            }
                            // Запускаем нужную Activity
                            startActivity(intent);

                            break;
                        }
                    }



                }

                break;
            }//endregion

            //region CASE_SHARE_WA
            case R.id.activity_order_bt_share_wa : {

                String sendMessage = new String();
                sendMessage += "- - - - -\n";
                sendMessage += "Номер заказа: " + orderNumber + "\n";
                sendMessage += "Адрес: " + address + "\n";
                sendMessage += "Дата и время: " + dateTime + "\n";
                sendMessage += "Что заказано: " + orderContent + "\n";
                sendMessage += "- - - - -";

                Intent shareWaIntent = new Intent(Intent.ACTION_SEND);
                shareWaIntent.setType("text/plain");
                shareWaIntent.setPackage("com.whatsapp");
                shareWaIntent.putExtra(Intent.EXTRA_TEXT, sendMessage);

                try {
                    startActivity(shareWaIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "На устройстве не установлен WhatsApp", Toast.LENGTH_SHORT).show();
                }
                break;
            }//endregion
        }
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }


    public class GeocodeTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Geocoder geocoder = new Geocoder();
                coordinates = geocoder.getCoordinates(address);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (coordinates != null) READY_TO_NAVIGATE = true;
        }
    }


}
