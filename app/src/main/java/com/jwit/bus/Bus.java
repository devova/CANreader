package com.jwit.bus;

import com.autowp.can.CanFrame;

import java.util.ArrayList;


public class Bus {
    public ArrayList<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public ArrayList<Signal> parseFrame(CanFrame frame) {
        ArrayList<Signal> result = new ArrayList<>();
        for (Message message: this.messages) {
            if (frame.getId() == message.id) {
                result = message.parseFrame(frame);
                break;
            }
        }
        return result;
    }
}
