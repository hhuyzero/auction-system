package com.auction.application.service;

import com.auction.application.port.AuctionRepository;
import com.auction.infrastructure.event.InMemoryEventBus;
import com.auction.application.concurrency.AuctionLockRegistry;
import java.time.Clock;

public class BiddingService {
    public BiddingService(AuctionRepository auctionRepository, InMemoryEventBus eventBus, AuctionLockRegistry lockRegistry, Clock clock) {}
}