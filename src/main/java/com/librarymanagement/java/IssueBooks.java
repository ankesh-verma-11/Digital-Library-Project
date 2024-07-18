package com.librarymanagement.java;

import jakarta.mail.Address;

public class IssueBooks {
    private int BookId;
    private String AdminId;
    private  String StudentId;
    private String StudentName;
    private  String StudentEmail;
    private  String BookName;
    private String IssueDate;
    private String ReturnDate;
    private String Address;
    private String Library;


    public IssueBooks(int bookId, String adminId, String studentId, String studentName, String studentEmail, String bookName, String issueDate, String returnDate) {
        BookId = bookId;
        AdminId = adminId;
        StudentId = studentId;
        StudentName = studentName;
        StudentEmail = studentEmail;
        BookName = bookName;
        IssueDate = issueDate;
        ReturnDate = returnDate;
    }

    public IssueBooks(int bookId, String adminId, String library ,String address, String bookName, String issueDate, String returnDate) {
        BookId = bookId;
        AdminId = adminId;
        Library = library;
        Address = address;
        BookName = bookName;
        IssueDate = issueDate;
        ReturnDate = returnDate;
    }

    public IssueBooks() {

    }
    public String getLibrary() {
        return Library;
    }

    public void setLibrary(String library) {
        Library = library;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    public String getAdminId() {
        return AdminId;
    }

    public void setAdminId(String adminId) {
        AdminId = adminId;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }
}
