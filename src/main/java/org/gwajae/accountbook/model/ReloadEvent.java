package org.gwajae.accountbook.model;

import javafx.event.Event;
import javafx.event.EventType;


public class ReloadEvent extends Event {
    public static final EventType<ReloadEvent> OPTIONS_ALL = new EventType<>(Event.ANY,"OPTIONS_ALL");

    public ReloadEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
