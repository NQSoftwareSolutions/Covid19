package com.nqsoftwaresolutions.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class StatisticsActivity extends AppCompatActivity {

    /**
     * STATS_URL is url from which we are going to get the data about covid-19 stats
     */
    private static final String STATS_URL = "https://api.covid19api.com/summary";
    /**
     * Tag for Logging
     */
    private static final String TAG = "error";

    ImageView backImageView;
    Button globalStatsBtn;
    private TextView totalCasesTv,newCasesTv,totalRecoveredTv,newRecoveredTv,totalDeathsTv,newDeathsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViews();

        loadHomeData();

    }

    private void initViews() {
        backImageView = findViewById(R.id.imageViewBack);
        totalCasesTv = findViewById(R.id.id_total_confirmed_tv);
        totalDeathsTv = findViewById(R.id.id_total_deaths_tv);
        totalRecoveredTv = findViewById(R.id.id_total_recovered_tv);
        newRecoveredTv = findViewById(R.id.id_new_recovered_tv);
        newCasesTv = findViewById(R.id.id_new_confirmed_tv);
        newDeathsTv = findViewById(R.id.id_new_deaths_tv);
        globalStatsBtn = findViewById(R.id.id_global_stats_btn);
    }


    private void loadHomeData(){
        //JSON string request
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, STATS_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Response received, handle response
                                handleResponse(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if any error occur, then hide progress show error
                        Log.e(TAG,"String Request : "+error.getMessage());
                    }
                });
        //Add Request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * @param response will be provided by network,JSON
     * Here we will handle what we get by our request
     * Global : is json object name in API data
     * NewConfirmed : is json string name of global json object in API data
     *                 (TotalConfirmed,NewDeaths,TotalDeaths,TotalRecovered,NewRecovered)
     */
    private void handleResponse(String response) {
        try {
            //Since we know that our response is in JSON object so 1st convert it to object
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObjectGlobal = jsonObject.getJSONObject("Global");

            //get data from that object
            String newConfirmed = jsonObjectGlobal.getString("NewConfirmed");
            String totalConfirmed = jsonObjectGlobal.getString("TotalConfirmed");
            String newDeaths = jsonObjectGlobal.getString("NewDeaths");
            String totalDeaths = jsonObjectGlobal.getString("TotalDeaths");
            String totalRecovered = jsonObjectGlobal.getString("TotalRecovered");
            String newRecovered = jsonObjectGlobal.getString("NewRecovered");

            //Set data on Views
            newCasesTv.setText(newConfirmed);
            totalCasesTv.setText(totalConfirmed);
            newDeathsTv.setText(newDeaths);
            totalDeathsTv.setText(totalDeaths);
            totalRecoveredTv.setText(totalRecovered);
            newRecoveredTv.setText(newRecovered);

        }catch (Exception e){
            Log.e(TAG,"handle response : "+e.getMessage());
        }
    }

    public void sendToMain(View view) {
        Intent mainIntent = new Intent(StatisticsActivity.this,MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public void globalStats(View view) {
        Toast.makeText(this,"Global Statistics",Toast.LENGTH_LONG).show();
        loadHomeData();
    }
}