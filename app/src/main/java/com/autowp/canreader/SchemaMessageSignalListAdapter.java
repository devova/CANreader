package com.autowp.canreader;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jvit.bus.Signal;

import java.util.ArrayList;

public class SchemaMessageSignalListAdapter extends ArrayAdapter<Signal> {
    public SchemaMessageSignalListAdapter(Context context, ArrayList<Signal> signals) {
        super(context, 0, signals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Signal signal = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_schema_message_signal, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.schemaSignalName);
        TextView tvInfo = (TextView) convertView.findViewById(R.id.schemaSignalInfo);
        // Populate the data into the template view using the data object
        tvName.setText(signal.name);
        tvInfo.setText(String.format("start bit: %d, length: %d, factor: %.2f, offset: %d",
                signal.startBit, signal.bitLength, signal.factor, signal.offset));
        // Return the completed view to render on screen
        return convertView;
    }
}
