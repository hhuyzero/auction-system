package com.auction.application.port;

import com.auction.domain.model.Auction;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository {
    Optional<Auction> findById(String id);
    void save(Auction auction);
    List<Auction> findAll();
    List<Auction> findByStatus(Auction.Status status);
}