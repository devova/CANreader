package com.jvit.bus;

import com.autowp.can.CanFrame;
import com.autowp.can.CanMessage;

import java.util.ArrayList;


public class Bus {
    public ArrayList<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public ArrayList<Signal> parseMessage(CanMessage canMessage) {
        ArrayList<Signal> result = new ArrayList<>();
        for (Message message: this.messages) {
            if (canMessage.getId() == message.id) {
                result = message.parseFrame(canMessage);
                break;
            }
        }
        return result;
    }
}
