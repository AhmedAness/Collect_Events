package com.example.dt.collect_events.presenter;

import android.content.Context;

import com.example.dt.collect_events.model.PersonUtils;

import java.util.List;

public interface Collector {

    interface View{

        void SetDataToRecycler(List<PersonUtils> EventsList);


    }

    interface Model{

        void setDetails(String Email);

        List<PersonUtils> getRecycler(Context context);


    }

    interface Presenter{

        void LoadEvents(Context context);

        void submitName(String Email);

        void setView(Collector.View view);

    }

}
