package com.anup.lmax.example;

import com.lmax.disruptor.EventFactory;

public final class ValueEvent {
    private int value;
    private String updatedBy;
    public final static EventFactory<ValueEvent> EVENT_FACTORY = () -> new ValueEvent();

    public ValueEvent() {
    }

    public ValueEvent(int value, String updatedBy) {
        this.value = value;
        this.updatedBy = updatedBy;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ValueEvent{" +
                "value=" + value +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
