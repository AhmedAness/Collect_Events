package com.example.dt.collect_events.presenter;

import android.content.Context;

public class CollectorPresenter implements Collector.Presenter {
    private Collector.View view;
    private Collector.Model model;

    public CollectorPresenter(Collector.Model model) {
        this.model=model;
    }

    @Override
    public void LoadEvents(Context context) {

        view.SetDataToRecycler(model.getRecycler(context));


    }

    @Override
    public void submitName(String Email) {
        model.setDetails(Email);
    }

    @Override
    public void setView(Collector.View view) {
        this.view=view;
    }
}
