package com.example.danilwelter.ninjapizzacourier.Presenters;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Geocoder {

    final String LOG_TAG = "myLogs";

    public class Coordinates{
        private String x;
        private String y;

        public Coordinates(String pX, String pY){
            x = pX;
            y = pY;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

    }

    public class DataWithBaseUrl{
        private String baseUrl;
        private String html;
        private String mimeType;
        private String encoding;
        private String historyUrl;

        //region GETTERS_AND_SETTERS
        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public String getHistoryUrl() {
            return historyUrl;
        }

        public void setHistoryUrl(String historyUrl) {
            this.historyUrl = historyUrl;
        }
        //endregion

        public DataWithBaseUrl(String baseUrl, String html, String mimeType, String encoding, String historyUrl) {
            this.baseUrl = baseUrl;
            this.html = html;
            this.mimeType = mimeType;
            this.encoding = encoding;
            this.historyUrl = historyUrl;
        }
    }


    //region HTML-WEBVIEW-BLOCK
    private final String HTML_PART_1 = "<!DOCTYPE html><html><head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
            "<script src=\"https://api-maps.yandex.ru/2.1/?lang=ru_RU\" type=\"text/javascript\"></script>\n" +
            "<script>\n" +
            "ymaps.ready(init);\n" +
            "function init () {\n" +
            "var myMap = new ymaps.Map('map', { center: [56.010563, 92.852572], zoom: 10, controls: ['zoomControl', 'trafficControl']});\n" +
            "ymaps.geocode('";
    private final String HTML_PART_2 = "', { boundedBy: myMap.getBounds(), strictBounds: true, results: 1}).then(function (res) {\n" +
            "var firstGeoObject = res.geoObjects.get(0),\n" +
            "coords = firstGeoObject.geometry.getCoordinates(),\n" +
            "bounds = firstGeoObject.properties.get('boundedBy');\n" +
            "firstGeoObject.options.set('preset', 'islands#darkBlueDotIconWithCaption');\n" +
            "firstGeoObject.properties.set('iconCaption', firstGeoObject.getAddressLine());\n" +
            "myMap.geoObjects.add(firstGeoObject);\n" +
            "myMap.setBounds(bounds, { checkZoomRange: true});});}\n" +
            "</script>\n" +
            "<style> body, html { padding: 0; margin: 0; width: 100%; height: 100%; } #map { width: 100%; height: 90%; } </style>\n" +
            "</head> <body> <div id=\"map\"></div> </body> </html>";

    private static final String BASE_URL = "http://com.example.danilwelter.ninjapizzacourier.ymapapp";
    private static final String MIME_TYPE = "text/html";
    private static final String ENCODING = "UTF-8";
    //endregion

    HttpURLConnection conn;
    String lnk = "https://geocode-maps.yandex.ru/1.x/?geocode=";
    String answer;



    public Coordinates getCoordinates(String address){

        if(!address.isEmpty()){


            try {
                lnk += URLEncoder.encode("Красноярск, ", "UTF-8");
                lnk += URLEncoder.encode(address, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.i("GEOCODER", e.getMessage());
            }

            try{
                conn = (HttpURLConnection) new URL(lnk).openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.setDoInput(true);
                conn.connect();
            }
            catch (Exception e){
                Log.i("GEOCODER", e.getMessage());
            }

            try {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder();

                String bfr_st = null;
                while ((bfr_st = br.readLine()) != null){
                    sb.append(bfr_st);
                }

                answer = sb.toString();
                is.close();
                br.close();

            } catch (Exception e){
                Log.i("GEOCODER", e.getMessage());
            } finally {
                conn.disconnect();
            }
        } else return null;

        if(!answer.isEmpty()){
            return getCoordinatesFromYandexXmlDocument(answer);

        } else{
            return null;
        }
    }

    @Nullable
    private Coordinates getCoordinatesFromYandexXmlDocument(String xml){

        String pos = "POS_NOT_FOUND";
        String currentTag = "noTag";


        try {
            XmlPullParser xpp = prepareXpp(xml);

            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    case XmlPullParser.START_TAG:
                        currentTag = xpp.getName();
                        break;

                    case XmlPullParser.TEXT:
                        if (currentTag.equals("pos")){
                            if(pos.equals("POS_NOT_FOUND")){
                                pos = xpp.getText();
                            }
                            currentTag = "noTag";
                        }
                        break;

                    default:
                        break;
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(pos.equals("POS_NOT_FOUND")){
            return null;
        } else{
            String[] coords = pos.split("\\s");

            return new Coordinates(coords[1], coords[0]);
        }
    }

    private XmlPullParser prepareXpp (String xml) throws XmlPullParserException{
        // получаем фабрику
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        // включаем поддержку namespace (по умолчанию выключена)
        factory.setNamespaceAware(true);
        // создаем парсер
        XmlPullParser xpp = factory.newPullParser();
        // даем парсеру на вход Reader
        xpp.setInput(new StringReader(xml));
        return xpp;
    }

    public DataWithBaseUrl getDataWitbBaseUrls(String address){
        String html = HTML_PART_1 + address + HTML_PART_2;
        DataWithBaseUrl data = new DataWithBaseUrl(BASE_URL, html, MIME_TYPE, ENCODING, null);

        return data;
    }



}
