package com.devlomi.circularstatusview.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.devlomi.circularstatusview.CircularStatusView;

import java.util.List;

import static com.devlomi.circularstatusview.sample.DummyDataGenerator.generateStatuses;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv);
        final List<UserStatus> userStatusList = generateStatuses();
        final StatusAdapter adapter = new StatusAdapter(userStatusList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnStatusClickListener(new StatusAdapter.OnStatusClickListener() {
            @Override
            public void onStatusClick(CircularStatusView circularStatusView, int pos) {
                UserStatus userStatus = userStatusList.get(pos);
                if (!userStatus.areAllSeen()) {
                    for (int i = 0; i < userStatus.getStatusList().size(); i++) {
                        Status status = userStatus.getStatusList().get(i);
                        if (!status.isSeen()) {
                            //update view
                            circularStatusView.setPortionColorForIndex(i, Color.GRAY);
                            //update adapter to prevent changes when scrolling
                            status.setSeen(true);
                            break;
                        }
                    }
                }
            }
        });
    }



}
