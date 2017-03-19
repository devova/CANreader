package com.autowp.canreader;

import android.os.Bundle;
import android.widget.ListView;

import com.jvit.bus.Bus;
import com.jvit.parser.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CanBusSchemaActivity extends ServiceConnectedActivity implements CanReaderService.OnMonitorChangedListener {
    private Bus bus;
    private SchemaMessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_bus_schema);

        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = getResources().openRawResource(R.raw.can_bus_schema);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser jsonParser = new JsonParser();
        bus = jsonParser.parse(byteArrayOutputStream);

        // Create the adapter to convert the array to views
        adapter = new SchemaMessageListAdapter(this, bus.messages);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.schemaMessages);
        listView.setAdapter(adapter);

    }

    private void applyMessage(MonitorCanMessage message) {
        bus.parseMessage(message.getCanMessage());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void afterConnect() {
        canReaderService.addListener(this);
    }

    @Override
    protected void beforeDisconnect() {
        canReaderService.removeListener(this);
    }

    @Override
    public void handleMonitorUpdated() {

    }

    @Override
    public void handleMonitorUpdated(final MonitorCanMessage message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    applyMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void handleSpeedChanged(double speed) {

    }
}
