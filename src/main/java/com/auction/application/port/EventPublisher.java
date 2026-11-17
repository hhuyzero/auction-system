package com.auction.application.port;

import com.auction.domain.event.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}