package com.example.dt.collect_events.view;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.dt.collect_events.presenter.Collector;
import com.example.dt.collect_events.presenter.CollectorPresenter;
import com.example.dt.collect_events.R;
import com.example.dt.collect_events.model.UserDataManager;
import com.example.dt.collect_events.model.PersonUtils;

import org.json.JSONArray;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity implements Collector.View, service.UpdateListener {

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    int MyVersion = Build.VERSION.SDK_INT;

    Collector.Presenter presenter;
    @BindView(R.id.recycleViewContainer)
    RecyclerView recyclerView;
    @BindView(R.id.Email)
    EditText Email;
    @BindView(R.id.Collect)
    Button Collect;
    @BindView(R.id.EmailContainer)
    LinearLayout EmailContainer;

    String EmailT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        AndroidNetworking.initialize(getApplicationContext());

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        presenter = new CollectorPresenter(new UserDataManager());

        AndroidNetworking.get("https://www.google.com/search?source=hp&ei=Ye4xXbHxDZvkgwfPzKzQDA&q=fast+network+android&oq=fast+&gs_l=psy-ab.3.0.35i39j0i67j0i131i67j0i67l2j0i131i67j0i67l2j0i203j0i131.1033.1864..3044...0.0..0.154.876.0j6......0....1..gws-wiz.....0..0.QW5gPSR3cgs")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(MainActivity.this, "hi ", Toast.LENGTH_SHORT).show();
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        // handle error
                    }
                });


        if (!isMyServiceRunning(service.class)) {

            service servicee = new service();
            servicee.setListener(this);
            Intent intent = new Intent(this, servicee.getClass());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                this.startForegroundService(intent);
            } else {
                this.startService(intent);
            }
        }


    }


    private void GetData() {
        EmailContainer.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        presenter.submitName(EmailT);
        presenter.LoadEvents(getApplicationContext());
    }


    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_SMS, Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    GetData();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(MainActivity.this);
    }

    @Override
    public void SetDataToRecycler(List<PersonUtils> EventsList) {
        mAdapter = new CustomRecyclerAdapter(this, EventsList);
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.Collect)
    public void onClick() {

        if (TextUtils.isEmpty(Email.getText())){
            Email.setError("Valid item");
        }else {
            EmailT=Email.getText().toString();
            if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (!checkIfAlreadyhavePermission()) {
                    requestForSpecificPermission();
                } else {
                    GetData();
                }
            }
        }

    }

    @Override
    public void onUpdate() {
        if (Collect.getVisibility()==View.INVISIBLE) {
            if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (!checkIfAlreadyhavePermission()) {
                    requestForSpecificPermission();
                } else {
                    GetData();
                }
            }
        }
    }
}
