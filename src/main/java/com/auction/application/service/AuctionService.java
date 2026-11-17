package com.auction.application.service;

import com.auction.application.port.AuctionRepository;
import com.auction.infrastructure.repository.JdbcItemRepository;
import java.time.Clock;

public class AuctionService {
    public AuctionService(AuctionRepository auctionRepository, JdbcItemRepository itemRepository, Clock clock) {}
}