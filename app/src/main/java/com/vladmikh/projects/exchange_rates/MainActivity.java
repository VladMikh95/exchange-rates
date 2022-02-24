package com.vladmikh.projects.exchange_rates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

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

    private ArrayList<Currency> currencies;

    private SharedPreferences sharedPreferences;

    private static final long DELAY_MILLIS = 600000; //Время через которое обновляются данные в миллисекундах;

    public static final String SHARED_PREFERENCES_NAME = "shared_preferences";
    public static final String CURRENCY_PREFERENCE = "currencies";
    public static final String EMPTY = "empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currencyAdapter = new CurrencyAdapter();

        currencies = new ArrayList<>();
        currencies.add(new Currency("1",1,"1","name", 32,31));
        currencies.add(new Currency("1",1,"1","name", 33,31));
        currencies.add(new Currency("1",1,"1","name", 34,31));

        currencies = getCurrencies();
        currencyAdapter.setCurrencies(currencies);
        recyclerView.setAdapter(currencyAdapter);
        autoReloading();
    }

    private ArrayList<Currency> getCurrencies() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
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

        return JSONUtils.getCurrenciesFromJSON(result);
    }

    private void autoReloading() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            int  i = 0; //переменная используется для того чтобы пропустить обновление данных сразу при запуске приложения
            @Override
            public void run() {
                if (i > 0) {
                    reload();
                }
                handler.postDelayed(this, DELAY_MILLIS);
                i++;
            }
        });
    }
    private void reload() {
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork();
        sharedPreferences.edit().putString(CURRENCY_PREFERENCE, jsonObject.toString()).apply();
        currencies =JSONUtils.getCurrenciesFromJSON(jsonObject);
        currencyAdapter.setCurrencies(currencies);
    }

    public void onClickReload(View view) {
        reload();

    }

    public void onClickCalculate(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
    }
}