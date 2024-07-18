package com.librarymanagement.database;

import com.librarymanagement.java.BookStore;
import com.librarymanagement.java.IssueBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDatabaseStudent {
    private Connection con = ConnectionProvider.getConnection();
    public boolean returnBook(int bookId, String studentId) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // First query to check if the book is issued to the student
            String query1 = "SELECT BookId, AdminId FROM IssuedBooks WHERE BookId = ? AND StudentId = ?";
            ps = con.prepareStatement(query1);
            ps.setInt(1, bookId);
            ps.setString(2, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String adminId = rs.getString("AdminId");
                int booksId = rs.getInt("BookId");
                System.out.println("adminId: " + adminId);
                System.out.println("bookId: " + booksId);

                rs.close();
                ps.close();
                String query2 = "SELECT IssuedBooks FROM Books WHERE BookId = ? AND MembershipId = ?";
                ps = con.prepareStatement(query2);
                ps.setInt(1, bookId);
                ps.setString(2, adminId);
                rs = ps.executeQuery();

                if (rs.next()) {
                    int issuedBooks = rs.getInt("IssuedBooks");
                    System.out.println("Book Issued: " + issuedBooks);
                    rs.close();
                    ps.close();
                    String query3 = "UPDATE Books SET IssuedBooks = ? WHERE BookId = ? AND MembershipId = ?";
                    ps = con.prepareStatement(query3);
                    ps.setInt(1, issuedBooks - 1);
                    ps.setInt(2, bookId);
                    ps.setString(3, adminId);

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        rs.close();
                        ps.close();
                        String query4 = "DELETE FROM IssuedBooks WHERE BookId = ? AND StudentId = ?";
                        ps = con.prepareStatement(query4);
                        ps.setInt(1, bookId);
                        ps.setString(2, studentId);

                        int rowsDeleted = ps.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println("Issued book record deleted successfully.");
                            status = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return status;
    }
    public boolean renewBook(int bookId, String studentId,String issueDate,String returnDate)
    {
        try{

            String query = "UPDATE IssuedBooks SET ReturnDate = ? WHERE BookId = ? AND StudentId = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,returnDate);
            ps.setInt(2,bookId);
            ps.setString(3,studentId);
            ps.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<IssueBooks> getIssuedBooks(int bookId , String studentId) {
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

                if(IssuedDate != null && ReturnDate != null ){
                    IssueBooks issueBooks = new IssueBooks(BookId, AdminId, Library, Address,BookName,IssuedDate, ReturnDate);
                    studentBooks.add(issueBooks);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentBooks;
    }

    public List<BookStore> getSignleBook( int bookId) {
        List<BookStore> books = new ArrayList<>();
        try {
            String query = "select * from Books where BookId=? ";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int booksId = rs.getInt("BookId");
                String memberId = rs.getString("MembershipId");
                String bookName = rs.getString("BookName");
                String bookAuther = rs.getString("BookAuther");
                String publishYear = rs.getString("PublicationYear");
                String edition = rs.getString("Edition");
                int quantity = rs.getInt("Quantity");
                int issuedBooks = rs.getInt("IssuedBooks");
                BookStore bookStore = new BookStore(booksId, memberId, bookName, bookAuther, publishYear, edition, quantity, issuedBooks);
                books.add(bookStore);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public int issueBooks(IssueBooks issueBooks) {
        int result = 0;
        try {

            String checkQuery = "SELECT COUNT(*) FROM IssuedBooks WHERE BookId = ? AND StudentId = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setInt(1, issueBooks.getBookId());
            checkStmt.setString(2, issueBooks.getStudentId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return 0;
            }

//            checkStmt.close();
//            rs.close();
//            String qu = "select WIssueDate WReturnDate from IssuedBooks";
//            checkStmt = con.prepareStatement(qu);
//            rs = checkStmt.executeQuery();
//            while(rs.next()){
//                String wIssueDate = rs.getString("WaitingIssueDate");
//                String wReturnDate = rs.getString("WaitingReturnDate");
//
//            }
            LocalDate currDate = LocalDate.now();
            LocalDate issueDate = LocalDate.parse(issueBooks.getIssueDate());
            if(currDate.isEqual(issueDate)){
                issue(issueBooks);
            }
            else {
//                String checkQuery1 = "SELECT COUNT(*) FROM IssuedBooks WHERE BookId = ? AND StudentId = ?";
//                PreparedStatement checkStmt1 = con.prepareStatement(checkQuery1);
//                checkStmt1.setInt(1, issueBooks.getBookId());
//                checkStmt1.setString(2, issueBooks.getStudentId());
//                ResultSet rs1 = checkStmt.executeQuery();
//                if (rs1.next() && rs1.getInt(1) > 0) {
//                    return 0;
//                }
                String query = "INSERT INTO IssuedBooks (BookId, AdminId, StudentId, WIssueDate, WReturnDate) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, issueBooks.getBookId());
                pstmt.setString(2, issueBooks.getAdminId());
                pstmt.setString(3, issueBooks.getStudentId());
                pstmt.setString(4, issueBooks.getIssueDate());
                pstmt.setString(5, issueBooks.getReturnDate());
                pstmt.executeUpdate();
                pstmt.close();
                rs.close();
                result =4;
//                String q = "Select Quantity ,IssuedBooks from Books where BookId =?";
//                pstmt = con.prepareStatement(q);
//                pstmt.setInt(1,issueBooks.getBookId());
//                rs = pstmt.executeQuery();

//                if(rs.next()){
//                    int totalCount = rs.getInt("Quantity");
//                    int issuedBook = rs.getInt("IssuedBooks");
//                    if(totalCount>issuedBook)
//                    {
//                        String query1 = "Update Books set IssuedBooks =? where MembershipId =? and BookId =? ";
//                        pstmt = con.prepareStatement(query1);
//                        pstmt.setInt(1,issuedBook+1);
//                        pstmt.setString(2,issueBooks.getAdminId());
//                        pstmt.setInt(3,issueBooks.getBookId());
//                        result = pstmt.executeUpdate();
//                        if(result>0)
//                            return result;
//                    }else {
//                        return -1;
//                    }
//                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result =0;
        }
        System.out.println("result :"+result);
        return result;
    }


    public int issue(IssueBooks issueBooks)
    {
        int result;
        PreparedStatement checkStmt;
        ResultSet rs;
        try{
            String checkQuery1 = "SELECT COUNT(*) FROM IssuedBooks WHERE BookId = ? AND StudentId = ? ";
            checkStmt = con.prepareStatement(checkQuery1);
            checkStmt.setInt(1, issueBooks.getBookId());
            checkStmt.setString(2, issueBooks.getStudentId());
            rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return 0;
            }
            String query = "INSERT INTO IssuedBooks (BookId, AdminId, StudentId, IssueDate, ReturnDate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, issueBooks.getBookId());
            pstmt.setString(2, issueBooks.getAdminId());
            pstmt.setString(3, issueBooks.getStudentId());
            pstmt.setString(4, issueBooks.getIssueDate());
            pstmt.setString(5, issueBooks.getReturnDate());
            pstmt.executeUpdate();
            pstmt.close();
            String q = "Select Quantity ,IssuedBooks from Books where BookId =?";
            pstmt = con.prepareStatement(q);
            pstmt.setInt(1,issueBooks.getBookId());
            rs = pstmt.executeQuery();
            if(rs.next()){
                int totalCount = rs.getInt("Quantity");
                int issuedBook = rs.getInt("IssuedBooks");
                if(totalCount>issuedBook)
                {
                    String query1 = "Update Books set IssuedBooks =? where MembershipId =? and BookId =? ";
                    pstmt = con.prepareStatement(query1);
                    pstmt.setInt(1,issuedBook+1);
                    pstmt.setString(2,issueBooks.getAdminId());
                    pstmt.setInt(3,issueBooks.getBookId());
                    result = pstmt.executeUpdate();
                    if(result>0)
                        return result;
                }else {
                    return -1;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;

    }

    public List<BookStore> getAllBook()  {
        List<BookStore> bookList = new ArrayList<>();
        if (con == null) {
            System.out.println("Database connection is null");
            return bookList;
        }
        try {
            String query = "SELECT * FROM Books";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("BookId");
                String memberId = rs.getString("MembershipId");
                String bookName = rs.getString("BookName");
                String bookAuther = rs.getString("BookAuther");
                String publishYear = rs.getString("PublicationYear");
                String edition = rs.getString("Edition");
                int quantity = rs.getInt("Quantity");
                int issuedBooks = rs.getInt("IssuedBooks");
                BookStore bookStore = new BookStore(bookId, memberId, bookName, bookAuther, publishYear, edition, quantity, issuedBooks);
                bookList.add(bookStore);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }


    public List<IssueBooks> getAllStudentIssuedBook( String studentId)  {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query ="select b.BookId,B.MembershipId as AdminId ,I.StudentId,b.BookName,A.LibName AS Library , A.Address , I.IssueDate ,I.ReturnDate from IssuedBooks as I left join Books B on B.BookId = I.BookId right join StudentRegister AS s on I.StudentId = s.MembershipId right join AdminRegister AS A on  I.AdminId = A.MembershipNumber where I.StudentId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
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

    public List<BookStore> searchBooks(String search) {
        List<BookStore> bookStores = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE BookName LIKE ? OR BookAuther LIKE ? OR Edition LIKE ? OR PublicationYear LIKE ?";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            String searchPattern = "%" + search + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);
            preparedStatement.setString(4, searchPattern);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookStore bookStore = new BookStore();
                bookStore.setBookId(resultSet.getInt("BookId"));
                bookStore.setMemberId(resultSet.getString("MembershipId"));
                bookStore.setBookName(resultSet.getString("BookName"));
                bookStore.setBookAuther(resultSet.getString("BookAuther"));
                bookStore.setPublishYear(resultSet.getString("PublicationYear"));
                bookStore.setEdition(resultSet.getString("Edition"));
                bookStore.setQuantity(resultSet.getInt("Quantity"));
                bookStores.add(bookStore);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookStores;
    }
    public List<IssueBooks> getIssuedBooksOfStudent(String search, String studentId) {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query = "SELECT b.BookId, B.MembershipId AS AdminId, I.StudentId, b.BookName, A.LibName AS Library, A.Address, I.IssueDate, I.ReturnDate " +
                    "FROM IssuedBooks AS I " +
                    "LEFT JOIN Books B ON B.BookId = I.BookId " +
                    "RIGHT JOIN StudentRegister AS S ON I.StudentId = S.MembershipId " +
                    "RIGHT JOIN AdminRegister AS A ON I.AdminId = A.MembershipNumber " +
                    "WHERE I.StudentId = ? ";

            if (search != null && !search.trim().isEmpty()) {
                query += "AND (b.BookName LIKE ? OR B.BookAuther LIKE ? OR B.Edition LIKE ? OR B.PublicationYear LIKE ?) ";
            }
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentId);
            if (search != null && !search.trim().isEmpty())
            {
                String searchPattern = "%" + search.trim() + "%";

                pstmt.setString(2, searchPattern);
                pstmt.setString(3, searchPattern);
                pstmt.setString(4, searchPattern);
                pstmt.setString(5, searchPattern);
            }
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
}
