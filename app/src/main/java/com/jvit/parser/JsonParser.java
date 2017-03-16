package com.jvit.parser;

import com.jvit.bus.Bus;
import com.jvit.bus.Message;
import com.jvit.bus.Signal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;


public class JsonParser implements SchemaParser {

    @Override
    public Bus parse(ByteArrayOutputStream stream) {
        Bus bus = new Bus();
        try {
            JSONObject json = new JSONObject(stream.toString());
            JSONArray messages = json.getJSONArray("messages");

            for (int i=0; i < messages.length(); i++) {
                JSONObject m = messages.getJSONObject(i);
                Message message = new Message(m.getString("name"), m.getString("id"));
                JSONObject signals = m.getJSONObject("signals");

                JSONArray signalStartBits = signals.names();
                for (int j=0; j < signalStartBits.length(); j++) {
                    int startBit = signalStartBits.getInt(j);
                    JSONObject s = signals.getJSONObject(signalStartBits.getString(j));

                    Signal signal = new Signal(
                            s.getString("name"), startBit, s.getInt("bit_length"));
                    if (s.has("factor")) {
                        signal.factor = s.getDouble("factor");
                    }
                    if (s.has("offset")) {
                        signal.offset = s.getDouble("offset");
                    }
                    message.addSignal(signal);
                }
                bus.addMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bus;
    }
}
