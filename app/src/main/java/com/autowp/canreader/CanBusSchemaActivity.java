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
        SchemaMessageListAdapter adapter = new SchemaMessageListAdapter(this, bus.messages);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.schemaMessages);
        listView.setAdapter(adapter);

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
    public void handleMonitorUpdated(MonitorCanMessage message) {
        bus.parseMessage(message.getCanMessage());
    }

    @Override
    public void handleSpeedChanged(double speed) {

    }
}
