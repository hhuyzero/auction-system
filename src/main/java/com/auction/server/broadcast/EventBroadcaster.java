package com.auction.server.broadcast;

import com.auction.domain.event.BidPlacedEvent;
import com.auction.domain.event.DomainEvent;
import com.auction.application.port.EventListener;
import com.auction.server.network.ClientSession;
import com.auction.server.network.SessionRegistry;
import com.google.gson.Gson;

import java.util.Set;

public class EventBroadcaster implements EventListener<DomainEvent> {
    private final SessionRegistry sessionRegistry;
    private final Gson gson = new Gson();

    public EventBroadcaster(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void on(DomainEvent event) {
        if (event instanceof BidPlacedEvent) {
            BidPlacedEvent bEvent = (BidPlacedEvent) event;
            String jsonPayload = gson.toJson(bEvent);
            Set<ClientSession> subscribers = sessionRegistry.getSubscribers(bEvent.getAuctionId());

            for (ClientSession session : subscribers) {
                session.sendResponse(jsonPayload);
            }
        }
    }
}