package com.example.sonolite_app;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class PregnancyFactsAdapter extends PagerAdapter {

    private Context context;
    private List<String> facts;

    public PregnancyFactsAdapter(Context context, List<String> facts) {
        this.context = context;
        this.facts = facts;
    }

    @Override
    public int getCount() {
        return facts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fact_item, container, false);

        TextView tvFact = view.findViewById(R.id.tvFact);
        tvFact.setText(facts.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
