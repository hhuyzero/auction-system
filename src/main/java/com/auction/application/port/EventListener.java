package com.auction.application.port;

import com.auction.domain.event.DomainEvent;

public interface EventListener<E extends DomainEvent> {
    void on(E event);
}