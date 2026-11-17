package com.auction.domain.event;

public class BidPlacedEvent implements DomainEvent {
    private String auctionId;
    private String bidderId;
    private double amount;

    public BidPlacedEvent(String auctionId, String bidderId, double amount) {
        this.auctionId = auctionId;
        this.bidderId = bidderId;
        this.amount = amount;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public double getAmount() {
        return amount;
    }
}