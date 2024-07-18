package com.librarymanagement.java;

public class BookStore {
    private int bookId;
    private String memberId;
    private String bookName;
    private String bookAuther;
    private String publishYear;
    private int quantity;
    private String edition;
    private int issudBooks;

    public BookStore(int bookId, String memberId, String bookName, String bookAuther, String publishYear, String edition, int quantity,int issudBooks) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.bookName = bookName;
        this.bookAuther = bookAuther;
        this.publishYear = publishYear;
        this.edition = edition;
        this.quantity = quantity;
        this.issudBooks = issudBooks;
    }
    public int getIssuedBooks() {
        return issudBooks;
    }

    public void setIssuedBooks(int issudBooks) {
        this.issudBooks = issudBooks;
    }

    public BookStore() {}

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuther() {
        return bookAuther;
    }

    public void setBookAuther(String bookAuther) {
        this.bookAuther = bookAuther;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}
