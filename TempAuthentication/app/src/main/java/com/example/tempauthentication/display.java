package com.example.tempauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class display extends AppCompatActivity {
    ImageView imgDisplayCart;
    TextView txtDisplayName, txtDisplayLocation;


    TextView tvResult;

    private final String url="https://api.openweathermap.org/data/2.5/weather";
    private final String appid="913e91ad8f4f0a72640e8723ab679740";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        imgDisplayCart = findViewById(R.id.imgDisplayCard);
        txtDisplayLocation = findViewById(R.id.txtDisplayLocation);
        txtDisplayName = findViewById(R.id.txtDisplayName);
        tvResult=findViewById(R.id.tvResult);

        Intent i = getIntent();
        int image = i.getIntExtra("imgDisplayCard", 0);
        String name = i.getStringExtra("txtDisplayName");
        String location = i.getStringExtra("txtDisplayLocation");
        imgDisplayCart.setImageResource(image);
        txtDisplayName.setText(name);
        txtDisplayLocation.setText(location);

        String tempUrl = "";
        String City = txtDisplayLocation.getText().toString().trim();

        if(City.equals("")){
            tvResult.setText("City Field cannot be empty");
        }else{
            tempUrl = url+"?q="+City+"&appid="+appid;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Log.d("response",response);
                String output="";
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp")-273.15;
                    double feelslike = jsonObjectMain.getDouble("feels_like")-273.15;
                    JSONObject jsonObjectWind= jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");

                    output += "Current weather of " + City
                            + "\n Temp: " + df.format(temp) + " °C"
                            + "\n Feels Like: " + df.format(feelslike) + " °C"
                            + "\n Description: " + description
                            + "\n Wind Speed: " + wind + "m/s (meters per second)"
                    ;
                    tvResult.setText(output);


                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }
}