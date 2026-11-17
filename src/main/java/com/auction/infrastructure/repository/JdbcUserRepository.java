package com.auction.infrastructure.repository;

import com.auction.infrastructure.persistence.DataSourceProvider;

public class JdbcUserRepository {
    private final DataSourceProvider dataSourceProvider;
    public JdbcUserRepository(DataSourceProvider dataSourceProvider) {
        this.dataSourceProvider = dataSourceProvider;
    }
}