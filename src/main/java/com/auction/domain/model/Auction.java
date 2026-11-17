package com.auction.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class Auction {
    public enum Status { OPEN, CLOSED, FINISHED }

    private String id;
    private String itemId;
    private Instant startTime;
    private Instant endTime;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private String leaderId;
    private Status status;
    private int version;
    private List<Bid> bids;

    private Auction(String id, String itemId, Instant startTime, Instant endTime,
                    BigDecimal startingPrice, BigDecimal currentPrice, String leaderId,
                    Status status, int version, List<Bid> bids) {
        this.id = id;
        this.itemId = itemId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.leaderId = leaderId;
        this.status = status;
        this.version = version;
        this.bids = bids;
    }

    public static Auction restore(String id, String itemId, Instant startTime, Instant endTime,
                                  BigDecimal startingPrice, BigDecimal currentPrice, String leaderId,
                                  Status status, int version, List<Bid> bids) {
        return new Auction(id, itemId, startTime, endTime, startingPrice, currentPrice, leaderId, status, version, bids);
    }

    public String getId() { return id; }
    public String getItemId() { return itemId; }
    public Instant getStartTime() { return startTime; }
    public Instant getEndTime() { return endTime; }
    public BigDecimal getStartingPrice() { return startingPrice; }
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public String getLeaderId() { return leaderId; }
    public Status getStatus() { return status; }
    public int getVersion() { return version; }
    public List<Bid> getBids() { return bids; }
}