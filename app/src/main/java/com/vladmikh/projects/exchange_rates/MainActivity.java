package com.vladmikh.projects.exchange_rates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
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

    private SharedPreferences sharedPreferences;

    public static final String SHARED_PREFERENCES_NAME = "shared_preferences";
    public static final String CURRENCY_PREFERENCE = "currencies";
    public static final String EMPTY = "empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currencyAdapter = new CurrencyAdapter();

        String currencyPreference = sharedPreferences.getString(CURRENCY_PREFERENCE, EMPTY);
        JSONObject result = null;
        if (currencyPreference.equals(EMPTY)) {
            result = NetworkUtils.getJSONFromNetwork();
            sharedPreferences.edit().putString(CURRENCY_PREFERENCE, result.toString()).apply(); //Сохраняем курсы валют в sharedPreferences
        } else {
            try {
                result = new JSONObject(currencyPreference);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Currency> currencies = JSONUtils.getCurrenciesFromJSON(result);
        StringBuilder builder = new StringBuilder();
        for (Currency i : currencies) {
            builder.append(i.getName()).append("\n");
         }
        currencyAdapter.setCurrencies(currencies);
        recyclerView.setAdapter(currencyAdapter);
    }
}