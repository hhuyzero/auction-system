package com.auction.application.service;

import com.auction.infrastructure.repository.JdbcItemRepository;

public class ItemService {
    private final JdbcItemRepository itemRepository;

    public ItemService(JdbcItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
}