package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView tvTemp, tvDescription, tvWind;
    EditText etCity;
    Button btnGet;
    RequestQueue queue;
    //JsonObjectRequest jor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTemp = findViewById(R.id.tvTemp);
        tvWind = findViewById(R.id.tvWind);

        etCity = findViewById(R.id.etCity);
        tvDescription = findViewById(R.id.tvDescription);
        btnGet = findViewById(R.id.btnGet);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findWeather(etCity.getText().toString());
            }
        });
        queue = Volley.newRequestQueue(this);
    }




    public void findWeather(String city) {

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=c2aa3620703b8695df8546c19acb32ea&units=Metric";
        //tvTemp.setText("defrgrewfbersvbg");

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tvTemp.setText("defrgrewfbersvbg");
                    JSONObject main_object = response.getJSONObject("main");
                    JSONObject wind_object = response.getJSONObject("wind");
                    JSONArray jArray = response.getJSONArray("weather");
                    JSONObject object = jArray.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String windSpeed = String.valueOf(wind_object.getDouble("speed"));
                    tvTemp.setText(temp);
                    tvDescription.setText(description);
                    tvWind.setText(windSpeed);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.getMessage());
            }
        });
        queue.add(jor);
    }
}
