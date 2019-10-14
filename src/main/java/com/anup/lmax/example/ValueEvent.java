package com.anup.lmax.example;

import com.lmax.disruptor.EventFactory;

public final class ValueEvent {
    private int value;
    private String updatedBy;
    public final static EventFactory<ValueEvent> EVENT_FACTORY = () -> new ValueEvent();

    public void setValue(int value) {
        this.value = value;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "ValueEvent{" +
                "value=" + value +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
