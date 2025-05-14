package org.example.productfeatureservice.dto;



import java.util.List;

public class PaginatedResponse<T> {
    private List<T> content;
    private PaginationMetadata pagination;

    public PaginatedResponse(List<T> content, PaginationMetadata pagination) {
        this.content = content;
        this.pagination = pagination;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public PaginationMetadata getPagination() {
        return pagination;
    }

    public void setPagination(PaginationMetadata pagination) {
        this.pagination = pagination;
    }

    public static class PaginationMetadata {
        private long totalItems;
        private int currentPage;
        private int pageSize;
        private int totalPages;

        public PaginationMetadata(long totalItems, int currentPage, int pageSize, int totalPages) {
            this.totalItems = totalItems;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalPages = totalPages;
        }

        public long getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(long totalItems) {
            this.totalItems = totalItems;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }
}