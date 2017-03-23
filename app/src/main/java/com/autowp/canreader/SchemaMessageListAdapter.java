package com.autowp.canreader;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jvit.bus.Message;
import com.jvit.bus.Signal;

import java.util.ArrayList;

public class SchemaMessageListAdapter extends ArrayAdapter<Message> {
    private Boolean showDoc = true;

    public SchemaMessageListAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_schema_message, parent, false);
        }
        // Lookup view for data population
        TextView tvId = (TextView) convertView.findViewById(R.id.canMessageId);
        TextView tvName = (TextView) convertView.findViewById(R.id.canMessageName);
        TextView tvSignals = (TextView) convertView.findViewById(R.id.canMessagesignals);
        // Populate the data into the template view using the data object
        tvId.setText(String.format("%03X", message.id));
        tvName.setText(message.name);

        String signals = "";
        for (Signal s: message.signals) {
            signals = String.format("%s\n%s", signals, showDoc ? s.toDocString() : s.toString());
        }
        tvSignals.setText(signals);

        // Return the completed view to render on screen
        return convertView;
    }

    public void toggleShowingDoc(Boolean show) {
        showDoc = show;
    }
}
