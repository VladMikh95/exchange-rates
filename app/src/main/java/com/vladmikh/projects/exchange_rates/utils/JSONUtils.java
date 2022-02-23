package com.vladmikh.projects.exchange_rates.utils;

import android.util.Log;

import com.vladmikh.projects.exchange_rates.data.Currency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String KEY_VALUTE = "Valute";

    private static final String ID = "ID";
    private static final String NUM_CODE = "NumCode";
    private static final String CHAR_CODE = "CharCode";
    private static final String NAME = "Name";
    private static final String VAlUE = "Value";
    private static final String PREVIOUS = "Previous";

    public static ArrayList<Currency> getCurrenciesFromJSON(JSONObject jsonObject) {
        ArrayList<Currency> results = new ArrayList<>();
        Currency currency = null;
        if (jsonObject == null) {
            return results;
        }
        try {
            JSONObject jsonObjectValute = jsonObject.getJSONObject(KEY_VALUTE);
            JSONArray jsonArray = jsonObjectValute.toJSONArray(jsonObjectValute.names());
            for (int i = 0; i < jsonArray.length(); i++) {
                currency = getCurrencyFromJSON(jsonArray.getJSONObject(i));
                results.add(currency);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    private static Currency getCurrencyFromJSON(JSONObject jsonObject) {
        Currency currency = null;
        try {
            String id = jsonObject.getString(ID);
            int numCode = jsonObject.getInt(NUM_CODE);
            String charCode = jsonObject.getString(CHAR_CODE);
            String name = jsonObject.getString(NAME);
            double value = jsonObject.getDouble(VAlUE);
            double previous = jsonObject.getDouble(PREVIOUS);
            currency = new Currency(id, numCode, charCode, name, value, previous);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return currency;
    }
}
