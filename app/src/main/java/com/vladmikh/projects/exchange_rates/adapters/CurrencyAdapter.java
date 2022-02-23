package com.vladmikh.projects.exchange_rates.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.vladmikh.projects.exchange_rates.R;
import com.vladmikh.projects.exchange_rates.data.Currency;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>{

    private ArrayList<Currency> currencies;

    public CurrencyAdapter() {
        currencies = new ArrayList<>();
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency currency = currencies.get(position);
        holder.textViewCurrencyName.setText(currency.getName());
        holder.textViewValue.setText(String.valueOf(currency.getValue()));
        String dynamics = getDynamicsValue(currency.getValue(), currency.getPrevious());
        holder.textViewDynamics.setText(dynamics);
        if (dynamics.charAt(0) == '+') {
            holder.textViewDynamics.setTextColor(Color.RED);
        } else {
            holder.textViewDynamics.setTextColor(Color.GREEN);
        }

    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public void setCurrencies(ArrayList<Currency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    private String getDynamicsValue(double value, double previous) {
        String result;
        double dynamics = value / previous * 100 - 100;
        if (dynamics > 0) {
            result = String.format("+" + "%.2f", dynamics) + "%";
        } else {
            result = String.format("%.2f", dynamics) +"%";
        }
        return result;
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCurrencyName;
        private TextView textViewValue;
        private TextView textViewDynamics;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCurrencyName = itemView.findViewById(R.id.textViewCurrencyName);
            textViewValue = itemView.findViewById(R.id.textViewValue);
            textViewDynamics = itemView.findViewById(R.id.textViewDynamics);
        }
    }



}
