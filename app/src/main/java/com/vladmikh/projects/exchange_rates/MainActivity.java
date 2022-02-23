package com.vladmikh.projects.exchange_rates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.vladmikh.projects.exchange_rates.adapters.CurrencyAdapter;
import com.vladmikh.projects.exchange_rates.data.Currency;
import com.vladmikh.projects.exchange_rates.utils.JSONUtils;
import com.vladmikh.projects.exchange_rates.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private CurrencyAdapter currencyAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currencyAdapter = new CurrencyAdapter();
        JSONObject result = NetworkUtils.getJSONFromNetwork();
        ArrayList<Currency> currencies = JSONUtils.getCurrenciesFromJSON(result);
        StringBuilder builder = new StringBuilder();
        for (Currency i : currencies) {
            builder.append(i.getName()).append("\n");
         }
        currencyAdapter.setCurrencies(currencies);
        recyclerView.setAdapter(currencyAdapter);
    }
}