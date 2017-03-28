package com.jvit.bus;

import com.autowp.can.CanFrame;
import com.autowp.can.CanMessage;

import java.util.ArrayList;
import java.util.HashMap;


public class Bus {
    public interface MessageHandler {
        String getMessageId();
        String getSignalName();
        Signal.SignalEventListener getListener();
    }


    public HashMap<String, Message> messages = new HashMap<>();

    private Boolean forceParsing = false;

    public void addMessage(Message message) {
        this.messages.put(message.getId(), message);
    }

    public ArrayList<Signal> parseMessage(CanMessage canMessage) {
        ArrayList<Signal> result = new ArrayList<>();
        for (Message message: this.messages.values()) {
            if (canMessage.getId() == message.id && (forceParsing || message.shouldParse())) {
                result = message.parseFrame(canMessage);
                break;
            }
        }
        return result;
    }

    public void turnOnForceParsing() {
        forceParsing = true;
    }

    public void turnOffForceParsing() {
        forceParsing = false;
    }

    public void addMessageHandler(MessageHandler messageHandler) {
        Message msg = messages.get(messageHandler.getMessageId());
        if (msg != null) {
            Signal signal = msg.signals.get(messageHandler.getSignalName());
            if (signal != null) {
                signal.addEventListener(messageHandler.getListener());
            }
        }
    }
}
