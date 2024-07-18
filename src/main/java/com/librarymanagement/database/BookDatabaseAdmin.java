package com.librarymanagement.database;
import com.librarymanagement.java.BookStore;
import com.librarymanagement.java.IssueBooks;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BookDatabaseAdmin {
    private Connection con = ConnectionProvider.getConnection();

    public boolean addBook(BookStore book) {
        try {
            String query = "INSERT INTO Books(MembershipId, BookName, BookAuther, PublicationYear, Edition, Quantity,IssuedBooks) VALUES (?, ?, ?, ?, ?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, book.getMemberId());
            pstmt.setString(2, book.getBookName());
            pstmt.setString(3, book.getBookAuther());
            pstmt.setString(4, book.getPublishYear());
            pstmt.setString(5, book.getEdition());
            pstmt.setInt(6, book.getQuantity());
            pstmt.setInt(7, book.getIssuedBooks());
            pstmt.executeUpdate();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // get all books of a particular admin it is only accessible by admin
    public List<BookStore> getAllBooks(String adminId) {
        List<BookStore> bookList = new ArrayList<>();
        if (con == null) {
            return bookList;
        }
        try {
            String query = "SELECT * FROM Books WHERE MembershipId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, adminId);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Total Books :"+bookList.size());
        return bookList;
    }

    //admin can delete a book
    public int deleteBook(int bookId ,String memberId) {
        int reault=0;
        try {
            int issuedBookCount = getIssuedBookCount(bookId,memberId);
            if(issuedBookCount == 0){
                String query = "DELETE FROM Books WHERE BookId = ? AND MembershipId = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1,bookId);
                pstmt.setString(2, memberId);
                reault= pstmt.executeUpdate();
                if(reault>0){
                    System.out.println("deleted successful");
                }
                else {
                    System.out.println("deleted unsuccessful");
                    reault = 0;
                }
            }
            else {
                String query = "UPDATE Books SET Quantity = ? WHERE BookId = ? AND MembershipId = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(2,bookId);
                pstmt.setString(3, memberId);
                pstmt.setInt(1,issuedBookCount);
                pstmt.executeUpdate();
                System.out.println("deleted unsuccessful");
                reault =-1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reault;
    }
    int getIssuedBookCount(int bookId , String memberId)  {
        int result=0;
        try {
            String query = "select * FROM Books WHERE BookId = ? AND MembershipId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookId);
            pstmt.setString(2, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                result = rs.getInt("IssuedBooks");
            }
        }catch (Exception e)
        {
           e.printStackTrace();
        }
    return result;
    }
    public List<IssueBooks> getAIssuedBookByStudent(String adminId, String searchBy) {
        List<IssueBooks> studentBooks = new ArrayList<>();
        try {
            String query = "select I.AdminId,b.BookId,b.BookName,s.MembershipId as StudentId,s.Name AS StudentName,s.Email AS StudentEmail, I.IssueDate ,I.ReturnDate from IssuedBooks as I left join Books B on B.BookId = I.BookId right join StudentRegister AS s on I.StudentId = s.MembershipId right join AdminRegister AS A on  I.AdminId = A.MembershipNumber where I.AdminId = ?";
            if (searchBy != null && !searchBy.isEmpty()) {
                query += " AND (b.BookName LIKE ? OR b.BookAuther LIKE ? OR b.Edition LIKE ? OR b.PublicationYear LIKE ?)";
            }
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, adminId);
            if (searchBy != null && !searchBy.isEmpty()) {
                String likeParam = "%" + searchBy + "%";
                pstmt.setString(2, likeParam); // BookName
                pstmt.setString(3, likeParam); // BookAuthor
                pstmt.setString(4, likeParam); // Edition
                pstmt.setString(5, likeParam); // PublicationYear
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("BookId");
                String bookName = rs.getString("BookName");
                String studentId = rs.getString("StudentId");
                String studentName = rs.getString("StudentName");
                String email = rs.getString("StudentEmail");
                String issuedDate = rs.getString("IssueDate");
                String returnDate = rs.getString("ReturnDate");

                IssueBooks issueBooks = new IssueBooks(bookId, adminId, studentId, studentName, email, bookName, issuedDate, returnDate);
                studentBooks.add(issueBooks);
                System.out.println("Book Name: " + bookName);
                System.out.println("Issued books by admin: " + studentBooks.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentBooks;
    }
    // admin can access only own books that is issued by students
    public List<IssueBooks> getAllIssuedBookByStudents( String adminId)  {
            List<IssueBooks> studentBooks = new ArrayList<>();
            try {
                String query = "select I.AdminId,b.BookId,b.BookName,s.MembershipId as StudentId,s.Name AS StudentName,s.Email AS StudentEmail, I.IssueDate ,I.ReturnDate from IssuedBooks as I left join Books B on B.BookId = I.BookId right join StudentRegister AS s on I.StudentId = s.MembershipId right join AdminRegister AS A on  I.AdminId = A.MembershipNumber where I.AdminId = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, adminId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String AdminId = rs.getString("AdminId");
                    int BookId = rs.getInt("BookId");
                    String BookName = rs.getString("BookName");
                    String StudentId = rs.getString("StudentId");
                    String StudentName = rs.getString("StudentName");
                    String Email = rs.getString("StudentEmail");
                    String IssuedDate = rs.getString("IssueDate");
                    String ReturnDate = rs.getString("ReturnDate");
                    IssueBooks issueBooks = new IssueBooks(BookId, AdminId, StudentId, StudentName, Email, BookName,IssuedDate, ReturnDate);
                    studentBooks.add(issueBooks);
                    System.out.println("issued books by admin : "+studentBooks.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return studentBooks;
    }
    public boolean doesAdminExist(String adminId) {
        boolean exists = false;
        try {
            String query = "SELECT COUNT(*) FROM AdminRegister WHERE MembershipNumber = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, adminId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public List<BookStore> getBookByAdmin(String search , String adminId){
        List<BookStore> books = new ArrayList<>();
        try{
            String query = "SELECT * FROM Books WHERE MembershipId = ?";
            // Determine the search criteria and adjust the query dynamically
            if (search != null && !search.isEmpty()) {
                query += " AND (BookName LIKE ? OR BookAuther LIKE ? OR Edition LIKE ? OR PublicationYear LIKE ?)";
            }
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, adminId);
            if (search != null && !search.isEmpty()) {
                String likeParam = "%" + search + "%";
                pstmt.setString(2, likeParam);  // BookName
                pstmt.setString(3, likeParam);  // BookAuther
                pstmt.setString(4, likeParam);  // Edition
                pstmt.setString(5, likeParam);  // PublicationYear
            }
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
                //System.out.println("Book ID: " + bookId + ", Book Name: " + bookName); // Debug print
                BookStore bookStore = new BookStore(booksId, memberId, bookName, bookAuther, publishYear, edition, quantity, issuedBooks);
                books.add(bookStore);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    public List<BookStore> getBookForEditAdmin(String bookId , String adminId){
        List<BookStore> books = new ArrayList<>();
        try{
            String query = "Select * from Books where BookId = ? and MembershipId =?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(bookId));
            pstmt.setString(2, adminId);
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
                //System.out.println("Book ID: " + bookId + ", Book Name: " + bookName); // Debug print
                BookStore bookStore = new BookStore(booksId, memberId, bookName, bookAuther, publishYear, edition, quantity, issuedBooks);
                books.add(bookStore);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    public boolean editBookByAdmin(BookStore book, String adminId, int bookId){
        try {
            String query = "UPDATE Books SET BookName=?, BookAuther=?, PublicationYear=?, Edition=?, Quantity=?, IssuedBooks=? WHERE MembershipId=? AND BookId=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, book.getBookName());
            pstmt.setString(2, book.getBookAuther());
            pstmt.setString(3, book.getPublishYear());
            pstmt.setString(4, book.getEdition());
            pstmt.setInt(5, book.getQuantity());
            pstmt.setInt(6, book.getIssuedBooks());
            pstmt.setString(7, adminId);
            pstmt.setInt(8, bookId);
            pstmt.executeUpdate();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
