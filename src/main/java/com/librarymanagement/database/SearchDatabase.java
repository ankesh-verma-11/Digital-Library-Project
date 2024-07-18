package com.librarymanagement.database;

import com.librarymanagement.java.IssueBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchDatabase {
    private Connection con = ConnectionProvider.getConnection();
    public List<IssueBooks> getIssuedBooksByBookId(int bookId , String studentId) {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query ="select b.BookId,B.MembershipId as AdminId ,I.StudentId,b.BookName,A.LibName AS Library ,A.Address , I.IssueDate ,I.ReturnDate from IssuedBooks as I left join Books B on B.BookId = I.BookId right join StudentRegister AS s on I.StudentId = s.MembershipId right join AdminRegister AS A on  I.AdminId = A.MembershipNumber where I.StudentId = ? and I.BookId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            pstmt.setInt(2,(bookId));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int BookId = rs.getInt("BookId");
                String AdminId = rs.getString("AdminId");
                String BookName = rs.getString("BookName");
                String Library = rs.getString("Library");
                String Address = rs.getString("Address");
                String IssuedDate = rs.getString("IssueDate");
                String ReturnDate = rs.getString("ReturnDate");
                IssueBooks issueBooks = new IssueBooks(BookId, AdminId, Library, Address,BookName,IssuedDate, ReturnDate);
                studentBooks.add(issueBooks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentBooks;
    }
    public List<IssueBooks> getIssuedBooksByBookName(String bookName , String studentId) {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query ="select b.BookId,B.MembershipId as AdminId ,I.StudentId,b.BookName,A.LibName AS Library ,A.Address , I.IssueDate ,I.ReturnDate from IssuedBooks as I left join Books B on B.BookId = I.BookId right join StudentRegister AS s on I.StudentId = s.MembershipId right join AdminRegister AS A on  I.AdminId = A.MembershipNumber where I.StudentId = ? and I.BookName = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            pstmt.setString(2,(bookName));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int BookId = rs.getInt("BookId");
                String AdminId = rs.getString("AdminId");
                String BookName = rs.getString("BookName");
                String Library = rs.getString("Library");
                String Address = rs.getString("Address");
                String IssuedDate = rs.getString("IssueDate");
                String ReturnDate = rs.getString("ReturnDate");
                IssueBooks issueBooks = new IssueBooks(BookId, AdminId, Library, Address,BookName,IssuedDate, ReturnDate);
                studentBooks.add(issueBooks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentBooks;
    }
    public List<IssueBooks> getIssuedBooksByBookAuthor(String BookAuthor , String studentId) {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query ="select b.BookId,B.MembershipId as AdminId ,I.StudentId,b.BookName,A.LibName AS Library ,A.Address , I.IssueDate ,I.ReturnDate from IssuedBooks as I left join Books B on B.BookId = I.BookId right join StudentRegister AS s on I.StudentId = s.MembershipId right join AdminRegister AS A on  I.AdminId = A.MembershipNumber where I.StudentId = ? and I.BookAuther = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            pstmt.setString(2,(BookAuthor));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int BookId = rs.getInt("BookId");
                String AdminId = rs.getString("AdminId");
                String BookName = rs.getString("BookName");
                String Library = rs.getString("Library");
                String Address = rs.getString("Address");
                String IssuedDate = rs.getString("IssueDate");
                String ReturnDate = rs.getString("ReturnDate");
                IssueBooks issueBooks = new IssueBooks(BookId, AdminId, Library, Address,BookName,IssuedDate, ReturnDate);
                studentBooks.add(issueBooks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentBooks;
    }
    public List<IssueBooks> getIssuedBooksByBookEdition(String BookEdition , String studentId) {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query ="select b.BookId,B.MembershipId as AdminId ,I.StudentId,b.BookName,A.LibName AS Library ,A.Address , I.IssueDate ,I.ReturnDate from IssuedBooks as I left join Books B on B.BookId = I.BookId right join StudentRegister AS s on I.StudentId = s.MembershipId right join AdminRegister AS A on  I.AdminId = A.MembershipNumber where I.StudentId = ? and I.Edition = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            pstmt.setString(2,(BookEdition));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int BookId = rs.getInt("BookId");
                String AdminId = rs.getString("AdminId");
                String BookName = rs.getString("BookName");
                String Library = rs.getString("Library");
                String Address = rs.getString("Address");
                String IssuedDate = rs.getString("IssueDate");
                String ReturnDate = rs.getString("ReturnDate");
                IssueBooks issueBooks = new IssueBooks(BookId, AdminId, Library, Address,BookName,IssuedDate, ReturnDate);
                studentBooks.add(issueBooks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentBooks;
    }
    public List<IssueBooks> getIssuedBooksByBookPublicationYear(String publicationYear , String studentId) {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query ="select b.BookId,B.MembershipId as AdminId ,I.StudentId,b.BookName,A.LibName AS Library ,A.Address , I.IssueDate ,I.ReturnDate from IssuedBooks as I left join Books B on B.BookId = I.BookId right join StudentRegister AS s on I.StudentId = s.MembershipId right join AdminRegister AS A on  I.AdminId = A.MembershipNumber where I.StudentId = ? and I.PublicationYear = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            pstmt.setString(2,(publicationYear));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int BookId = rs.getInt("BookId");
                String AdminId = rs.getString("AdminId");
                String BookName = rs.getString("BookName");
                String Library = rs.getString("Library");
                String Address = rs.getString("Address");
                String IssuedDate = rs.getString("IssueDate");
                String ReturnDate = rs.getString("ReturnDate");
                IssueBooks issueBooks = new IssueBooks(BookId, AdminId, Library, Address,BookName,IssuedDate, ReturnDate);
                studentBooks.add(issueBooks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentBooks;
    }

    public List<IssueBooks> getIssuedBookss(SearchType searchType, String searchValue, String studentId) {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query = "SELECT b.BookId, B.MembershipId AS AdminId, I.StudentId, b.BookName, A.LibName AS Library, " +
                    "A.Address, I.IssueDate, I.ReturnDate " +
                    "FROM IssuedBooks AS I " +
                    "LEFT JOIN Books B ON B.BookId = I.BookId " +
                    "RIGHT JOIN StudentRegister AS s ON I.StudentId = s.MembershipId " +
                    "RIGHT JOIN AdminRegister AS A ON I.AdminId = A.MembershipNumber " +
                    "WHERE I.StudentId = ? ";

            switch (searchType) {
                case BY_BOOK_ID:
                    query += "AND I.BookId = ?";
                    break;
                case BY_BOOK_NAME:
                    query += "AND I.BookName = ?";
                    break;
                case BY_BOOK_AUTHOR:
                    query += "AND I.BookAuther = ?";
                    break;
                case BY_BOOK_EDITION:
                    query += "AND I.Edition = ?";
                    break;
                case BY_BOOK_PUBLICATION_YEAR:
                    query += "AND I.PublicationYear = ?";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search type");
            }

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            pstmt.setString(2, searchValue);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("BookId");
                String adminId = rs.getString("AdminId");
                String bookName = rs.getString("BookName");
                String library = rs.getString("Library");
                String address = rs.getString("Address");
                String issueDate = rs.getString("IssueDate");
                String returnDate = rs.getString("ReturnDate");
                IssueBooks issueBooks = new IssueBooks(bookId, adminId, library, address, bookName, issueDate, returnDate);
                studentBooks.add(issueBooks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentBooks;
    }

}
