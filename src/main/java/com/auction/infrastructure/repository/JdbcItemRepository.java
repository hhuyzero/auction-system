package com.auction.infrastructure.repository;

import com.auction.infrastructure.persistence.DataSourceProvider;

public class JdbcItemRepository {
    private final DataSourceProvider dataSourceProvider;
    public JdbcItemRepository(DataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }
}