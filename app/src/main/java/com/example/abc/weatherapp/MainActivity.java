package com.example.abc.weatherapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abc.weatherapp.Common.Common;
import com.example.abc.weatherapp.Helper.Helper;
import com.example.abc.weatherapp.Model.OpenWeatherApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_ID = 1097;

    TextView CityWeatherDesc, CityHumidityDesc, CityTempDesc, CityName_txt, CityWindspeedDesc;
    EditText CityName;
    ImageView CityImage;
    Button ApiRequestBtn;

    Picasso picasso;

    OpenWeatherApp openWeatherApp = new OpenWeatherApp();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CityWeatherDesc = (TextView) findViewById(R.id.Descriptiton_txt);

        CityHumidityDesc = (TextView) findViewById(R.id.Humidity_txt);

        CityTempDesc = (TextView) findViewById(R.id.Temperature_txt);

        CityName_txt = (TextView) findViewById(R.id.City_txt);

        CityImage = (ImageView) findViewById(R.id.image_view);

        ApiRequestBtn = (Button) findViewById(R.id.ApiBtn);

        CityName = (EditText) findViewById(R.id.EditCityName);

        CityWindspeedDesc = (TextView) findViewById(R.id.Windspeed_txt);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this , new String[]{
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, PERMISSION_REQUEST_ID);
        }

//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&
//                checkSelfPermission(Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this , new String[]{
//                    Manifest.permission.INTERNET
//            }, PERMISSION_REQUEST_ID);
//        }
//
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&
//                checkSelfPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this , new String[]{
//                    Manifest.permission.SYSTEM_ALERT_WINDOW
//            }, PERMISSION_REQUEST_ID);
//        }
//
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&
//                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this , new String[]{
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            }, PERMISSION_REQUEST_ID);
//        }

        ApiRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String CityNameEnteredByUser = CityName.getText().toString();

                    String queryApiUrlString  = Common.apirequest(CityNameEnteredByUser);
                    new GetWeather().execute(queryApiUrlString);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode == PERMISSION_REQUEST_ID){

            for(int i = 0; (i < grantResults.length) ; i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(MainActivity.this,String.format("%s not granted ",permissions[i]), Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private class GetWeather extends AsyncTask<String, Void, String>{

        ProgressDialog pd = new ProgressDialog(MainActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setTitle("Please Wait ... ");
            pd.show();
        }



        @Override
        protected String doInBackground(String... strings) {

            String stream = null;
            String UrlStringForApiCall = strings[0];
            Helper http = new Helper();

            stream = http.getHttpData(UrlStringForApiCall);


            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           // Toast.makeText(MainActivity.this, "Reached Onpost Execute function", Toast.LENGTH_SHORT).show();
            if(s.contains("Error: Not found city")){
                pd.dismiss();
                return;
            }

            Gson gson = new Gson();
            Type mtype = new TypeToken<OpenWeatherApp>(){}.getType();

            openWeatherApp = gson.fromJson(s, mtype);

            pd.dismiss();
           // Toast.makeText(MainActivity.this, "The Process reached here", Toast.LENGTH_SHORT).show();



//            double Temp = openWeatherApp.getmain().getTemp();
//
//            Toast.makeText(MainActivity.this,String.format("%s" , Temp), Toast.LENGTH_SHORT).show();


//            Double windspeed = openWeatherApp.getWind().getSpeed();
//            Toast.makeText(MainActivity.this, String.format("%s is the windspeed", windspeed), Toast.LENGTH_SHORT).show();

            CityName_txt.setText(String.format("City -: %s, Country -:%s ", openWeatherApp.getName(), openWeatherApp.getSys().getCountry()));
            CityWeatherDesc.setText(String.format("Weather Description -:%s", openWeatherApp.getWeather().get(0).getDescription()));
            CityHumidityDesc.setText(String.format("Humidity -: %s ", openWeatherApp.getmain().getHumidity()));
            CityTempDesc.setText(String.format("%s degree Celcius", openWeatherApp.getmain().getTemp() - 273));
            CityWindspeedDesc.setText(String.format("%s m/s", openWeatherApp.getWind().getSpeed()));

            picasso.with(MainActivity.this)
                    .load(Common.getImage(openWeatherApp.getWeather().get(0).getIcon()))
                    .into(CityImage);

        }
    }

}
