package com.vladmikh.projects.exchange_rates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vladmikh.projects.exchange_rates.data.Currency;
import com.vladmikh.projects.exchange_rates.utils.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity {

    private EditText editText;
    private Spinner spinner;
    private TextView textViewResult;
    private ArrayList<Currency> currencies;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editText = findViewById(R.id.editTextNumber);
        spinner = findViewById(R.id.spinner);
        textViewResult = findViewById(R.id.textViewResult);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(sharedPreferences.getString(MainActivity.CURRENCY_PREFERENCE, MainActivity.EMPTY));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        currencies = JSONUtils.getCurrenciesFromJSON(jsonObject);
        strings = new ArrayList<>();
        for (Currency currency : currencies) {
            strings.add(currency.getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext()
                , android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(adapter);


    }

    public void onClickCalculate(View view) {
        if (editText.getText().length() == 0) {
            Toast.makeText(this, R.string.edit_text_hint, Toast.LENGTH_SHORT).show();
        } else {
            double count = Double.valueOf(editText.getText().toString());
            int spinnerSelectId = (int) spinner.getSelectedItemId();
            Currency currency = currencies.get(spinnerSelectId);
            double value = currency.getValue();
            double result = count / value;
            textViewResult.setText(String.format("%.2f", result));
        }
    }
}