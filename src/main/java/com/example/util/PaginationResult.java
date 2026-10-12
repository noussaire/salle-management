package com.example.util;

import java.util.List;

public class PaginationResult<T> {
    private final List<T> items;
    private final int currentPage;
    private final int pageSize;
    private final int totalPages;
    private final long totalItems;

    public PaginationResult(List<T> items, int currentPage, int pageSize, long totalItems) {
        this.items = items;
        this.currentPage = Math.max(currentPage, 1);
        this.pageSize = Math.max(pageSize, 1);
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / this.pageSize);
    }

    public List<T> getItems() {
        return items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }
}
