package com.auction.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Bid {
    private String id;
    private String auctionId;
    private String bidderId;
    private BigDecimal amount;
    private Instant timestamp;

    public Bid(String id, String auctionId, String bidderId, BigDecimal amount, Instant timestamp) {
        this.id = id;
        this.auctionId = auctionId;
        this.bidderId = bidderId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public String getAuctionId() { return auctionId; }
    public String getBidderId() { return bidderId; }
    public BigDecimal getAmount() { return amount; }
    public Instant getTimestamp() { return timestamp; }
}