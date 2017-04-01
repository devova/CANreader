package com.jvit.bus;

import com.autowp.can.CanMessage;

import java.util.ArrayList;
import java.util.HashMap;


public class Bus {
    public interface SignalHandler {
        String getMessageId();
        String getSignalName();
        Signal.SignalEventListener getListener();
    }


    public HashMap<String, Message> messages = new HashMap<>();

    private Boolean forceParsing = false;

    public void addMessage(Message message) {
        this.messages.put(message.getId(), message);
    }

    public ArrayList<Message> getMessages() {
        return new ArrayList<>(messages.values());
    }

    public ArrayList<Signal> parseMessage(CanMessage canMessage) {
        ArrayList<Signal> result = new ArrayList<>();
        Message message = messages.get(String.format("%h", canMessage.getId()));
        if (message != null) {
            if (forceParsing || message.shouldParse()) {
                result = message.parseFrame(canMessage, this);
            }
        }
        return result;
    }

    public void turnOnForceParsing() {
        forceParsing = true;
        for (Message message: this.messages.values()) {
            message.turnOnForceParsing();
        }
    }

    public void turnOffForceParsing() {
        forceParsing = false;
        for (Message message: this.messages.values()) {
            message.turnOffForceParsing();
        }
    }

    public void addSignalHandler(SignalHandler signalHandler) {
        Message msg = messages.get(signalHandler.getMessageId());
        if (msg != null) {
            Signal signal = msg.signals.get(signalHandler.getSignalName());
            if (signal != null) {
                signal.addEventListener(signalHandler.getListener());
            }
        }
    }
}
