package com.auction.infrastructure.repository;

import com.auction.domain.model.Auction;
import com.auction.domain.model.Bid;
import com.auction.application.port.AuctionRepository;
import com.auction.infrastructure.persistence.DataSourceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAuctionRepository implements AuctionRepository {
    private final DataSourceProvider dataSourceProvider;

    public JdbcAuctionRepository(DataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    public Optional<Auction> findById(String id) {
        String sql = "SELECT * FROM auctions WHERE id = ?";
        try (Connection conn = dataSourceProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                List<Bid> bids = findBidsByAuctionId(conn, id);
                Auction auction = Auction.restore(
                        rs.getString("id"),
                        rs.getString("item_id"),
                        rs.getTimestamp("start_time").toInstant(),
                        rs.getTimestamp("end_time").toInstant(),
                        rs.getBigDecimal("starting_price"),
                        rs.getBigDecimal("current_price"),
                        rs.getString("leader_id"),
                        Auction.Status.valueOf(rs.getString("status")),
                        rs.getInt("version"),
                        bids
                );
                return Optional.of(auction);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding auction by id", e);
        }
        return Optional.empty();
    }

    private List<Bid> findBidsByAuctionId(Connection conn, String auctionId) throws SQLException {
        List<Bid> bids = new ArrayList<>();
        String sql = "SELECT * FROM bids WHERE auction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, auctionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bids.add(new Bid(
                        rs.getString("id"),
                        rs.getString("auction_id"),
                        rs.getString("bidder_id"),
                        rs.getBigDecimal("amount"),
                        rs.getTimestamp("timestamp").toInstant()
                ));
            }
        }
        return bids;
    }

    @Override
    public void save(Auction auction) {
        String upsertAuction = "MERGE INTO auctions (id, item_id, start_time, end_time, starting_price, current_price, leader_id, status, version) KEY(id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertBid = "MERGE INTO bids (id, auction_id, bidder_id, amount, timestamp) KEY(id) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = dataSourceProvider.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtA = conn.prepareStatement(upsertAuction)) {
                stmtA.setString(1, auction.getId());
                stmtA.setString(2, auction.getItemId());
                stmtA.setTimestamp(3, Timestamp.from(auction.getStartTime()));
                stmtA.setTimestamp(4, Timestamp.from(auction.getEndTime()));
                stmtA.setBigDecimal(5, auction.getStartingPrice());
                stmtA.setBigDecimal(6, auction.getCurrentPrice());
                stmtA.setString(7, auction.getLeaderId());
                stmtA.setString(8, auction.getStatus().name());
                stmtA.setInt(9, auction.getVersion());
                stmtA.executeUpdate();
            }

            for (Bid bid : auction.getBids()) {
                try (PreparedStatement stmtB = conn.prepareStatement(insertBid)) {
                    stmtB.setString(1, bid.getId());
                    stmtB.setString(2, bid.getAuctionId());
                    stmtB.setString(3, bid.getBidderId());
                    stmtB.setBigDecimal(4, bid.getAmount());
                    stmtB.setTimestamp(5, Timestamp.from(bid.getTimestamp()));
                    stmtB.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Error saving auction transaction", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }

    @Override
    public List<Auction> findAll() { return new ArrayList<>(); }
    @Override
    public List<Auction> findByStatus(Auction.Status status) { return new ArrayList<>(); }
}