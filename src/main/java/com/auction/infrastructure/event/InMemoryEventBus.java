package com.auction.infrastructure.event;

import com.auction.domain.event.DomainEvent;
import com.auction.application.port.EventPublisher;
import com.auction.application.port.EventListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryEventBus implements EventPublisher {
    private final List<EventListener<DomainEvent>> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void publish(DomainEvent event) {
        for (EventListener<DomainEvent> listener : listeners) {
            try {
                listener.on(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void register(EventListener<?> listener) {
        listeners.add((EventListener<DomainEvent>) listener);
    }
}