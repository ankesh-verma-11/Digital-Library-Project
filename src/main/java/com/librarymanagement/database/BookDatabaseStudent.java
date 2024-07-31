package com.librarymanagement.database;

import com.librarymanagement.java.BookStore;
import com.librarymanagement.java.IssueBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDatabaseStudent {
    public Connection con = ConnectionProvider.getConnection();
    private final  int ERROR = 0;
    private final  int ISSUE_BOOK = 1;
    private final  int RESERVE_BOOK = 2;
    private final  int NOT_AVAILABLE_BOOK = 3;
    private final  int NOT_AVAILABLE_FOR_SELECTED_DATES = 4;
    private final int ALREADY_HAVE = 5;

    public boolean returnBook(int bookId, String studentId) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
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

        LocalDate currDate = LocalDate.now();
        LocalDate issueDate = LocalDate.parse(issueBooks.getIssueDate());
        LocalDate returnDate = LocalDate.parse(issueBooks.getReturnDate());

        try {
            String checkBookQuery = "SELECT Quantity, IssuedBooks FROM Books WHERE BookId = ?";
            PreparedStatement checkBookStmt = con.prepareStatement(checkBookQuery);
            checkBookStmt.setInt(1, issueBooks.getBookId());
            ResultSet rsBook = checkBookStmt.executeQuery();
            int totalQuantity = 0;
            int issuedBooks = 0;
            if (rsBook.next()) {
                totalQuantity = rsBook.getInt("Quantity");
                issuedBooks = rsBook.getInt("IssuedBooks");
            }

            String checkUserIssuedQuery = "SELECT IssueDate, ReturnDate, WIssueDate, WReturnDate FROM IssuedBooks WHERE BookId = ? AND StudentId = ?";
            PreparedStatement checkUserIssuedStmt = con.prepareStatement(checkUserIssuedQuery);
            checkUserIssuedStmt.setInt(1, issueBooks.getBookId());
            checkUserIssuedStmt.setString(2, issueBooks.getStudentId());
            ResultSet rsUserIssued = checkUserIssuedStmt.executeQuery();
            System.out.println("User Exist or not with bookId :");
            boolean bookIssued = false;
            LocalDate dbIssueDate = null;
            LocalDate dbReturnDate = null;
            LocalDate wIssueDate = null;
            LocalDate wReturnDate = null;
            if (rsUserIssued.next()) {
                dbIssueDate = rsUserIssued.getDate("IssueDate") != null ? rsUserIssued.getDate("IssueDate").toLocalDate() : null;
                dbReturnDate = rsUserIssued.getDate("ReturnDate") != null ? rsUserIssued.getDate("ReturnDate").toLocalDate() : null;
                wIssueDate = rsUserIssued.getDate("WIssueDate") != null ? rsUserIssued.getDate("WIssueDate").toLocalDate() : null;
                wReturnDate = rsUserIssued.getDate("WReturnDate") != null ? rsUserIssued.getDate("WReturnDate").toLocalDate() : null;

                if (dbIssueDate != null) {
                    bookIssued = true;
                    System.out.println("have not not :"+bookIssued);
                    if(bookIssued && currDate.equals(issueDate))
                        return ALREADY_HAVE;
                }
            }else if((issuedBooks < totalQuantity) && issueDate.equals(currDate)){
                System.out.println("if user not exist with that book id issue date is currdate then issue");
                int resutlt = issue(issueBooks);
                if(resutlt == 1 ){
                    return ISSUE_BOOK;
                }
            }
            if ( issueDate.isEqual(currDate) && (issuedBooks < totalQuantity)){
                if ((dbIssueDate == null && dbReturnDate == null) && (wIssueDate != null && wReturnDate != null)) {
                    System.out.println("successfully issue when null");
                    int resutlt = issue(issueBooks);
                    if(resutlt == ISSUE_BOOK){
                        return ISSUE_BOOK;
                    }
                    return ERROR;
                }
                else if ((dbIssueDate == null && dbReturnDate == null) && (wIssueDate != null && returnDate.isBefore(wIssueDate))) {
                    System.out.println("successfully issue");
                    int resutlt = issue(issueBooks);
                    if(resutlt == ISSUE_BOOK){
                        return ISSUE_BOOK;
                    }
                    return ERROR;
                }
               else if ((dbIssueDate == null && dbReturnDate == null) && returnDate.isAfter(wIssueDate)) {
                    System.out.println("Book not available for the selected dates.");
                    return NOT_AVAILABLE_FOR_SELECTED_DATES;
               }
               else if(bookIssued){
                    System.out.println("book already you have");
                    return ALREADY_HAVE;
               }
            }else if(!currDate.equals(issueDate)){
                if (wIssueDate == null && wReturnDate == null) {
                    boolean available =  bookAvailability(issueBooks);
                    if(available){
                        int resultt = reserveBook(issueBooks);
                        System.out.println("Book reserved for future dates.");
                        if(resultt == 1) return RESERVE_BOOK;
                        else  return ERROR;
                    }
                    return NOT_AVAILABLE_FOR_SELECTED_DATES;
                } else if (issueDate.isBefore(wIssueDate) && returnDate.isBefore(wIssueDate)) {
                    boolean available =  bookAvailability(issueBooks);
                    if(available){
                        int resultt = reserveBook(issueBooks);
                        System.out.println("Book reserved for future dates.");
                        if(resultt == 1) return RESERVE_BOOK;
                        else  return ERROR;
                    }
                    return NOT_AVAILABLE_FOR_SELECTED_DATES;
                } else if (issueDate.isAfter(wIssueDate) && returnDate.isAfter(wIssueDate)) {
                    boolean available =  bookAvailability(issueBooks);
                    if(available){
                        int resultt = reserveBook(issueBooks);
                        System.out.println("Book reserved for future dates.");
                        if(resultt == 1) return RESERVE_BOOK;
                        else  return ERROR;
                    }
                    return NOT_AVAILABLE_FOR_SELECTED_DATES;
                }else {
                    System.out.println("Book not available for the selected dates.");
                    return NOT_AVAILABLE_FOR_SELECTED_DATES;
                }
            }else {
                return NOT_AVAILABLE_BOOK;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ERROR;
    }

    private int issue(IssueBooks issueBooks) {
            int  status =ERROR;
            ResultSet rs;
            try {
                String query = "INSERT INTO IssuedBooks (BookId, AdminId, StudentId, IssueDate, ReturnDate) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, issueBooks.getBookId());
                pstmt.setString(2, issueBooks.getAdminId());
                pstmt.setString(3, issueBooks.getStudentId());
                pstmt.setString(4, issueBooks.getIssueDate());
                pstmt.setString(5, issueBooks.getReturnDate());
                int updateResult = pstmt.executeUpdate();
                status = updateResult > 0 ? 1 : 0;
                pstmt.close();

                String q = "SELECT Quantity, IssuedBooks FROM Books WHERE BookId = ?";
                pstmt = con.prepareStatement(q);
                pstmt.setInt(1, issueBooks.getBookId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int totalCount = rs.getInt("Quantity");
                    int issuedBook = rs.getInt("IssuedBooks");
                    if (totalCount > issuedBook) {
                        String query1 = "UPDATE Books SET IssuedBooks = ? WHERE BookId = ?";
                        pstmt = con.prepareStatement(query1);
                        pstmt.setInt(1, issuedBook + 1);
                        pstmt.setInt(2, issueBooks.getBookId());
                         updateResult = pstmt.executeUpdate();
                        status = updateResult > 0 ? 1 : 0;
                    } else {
                        status = 0;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                status = 0;
            }
            return status;
        }
        private int reserveBook(IssueBooks issueBooks) {
            int status = ERROR;
            try
            {
                String query = "INSERT INTO IssuedBooks (BookId, AdminId, StudentId, WIssueDate, WReturnDate) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, issueBooks.getBookId());
                pstmt.setString(2, issueBooks.getAdminId());
                pstmt.setString(3, issueBooks.getStudentId());
                pstmt.setString(4, issueBooks.getIssueDate());
                pstmt.setString(5, issueBooks.getReturnDate());
                int updateResult = pstmt.executeUpdate();
                status = updateResult > 0 ? 1 : 0;
                pstmt.close();
            }catch (Exception e){
                e.printStackTrace();
                status = 0;
            }
            return status;
        }
        private  boolean bookAvailability(IssueBooks issueBooks)
        {
            LocalDate issueDate = LocalDate.parse(issueBooks.getIssueDate());
            LocalDate returnDate = LocalDate.parse(issueBooks.getReturnDate());
            try{
                String checkUserIssuedQuery = "SELECT IssueDate, ReturnDate, WIssueDate, WReturnDate FROM IssuedBooks WHERE BookId =  ?";
                PreparedStatement checkUserIssuedStmt = con.prepareStatement(checkUserIssuedQuery);
                checkUserIssuedStmt.setInt(1, issueBooks.getBookId());
               // checkUserIssuedStmt.setString(2, issueBooks.getStudentId());
                ResultSet rsUserIssued = checkUserIssuedStmt.executeQuery();
                LocalDate dbIssueDate = null;
                LocalDate dbReturnDate = null;
                LocalDate wIssueDate = null;
                LocalDate wReturnDate = null;
                while (rsUserIssued.next())
                {
                    dbIssueDate = rsUserIssued.getDate("IssueDate") != null ? rsUserIssued.getDate("IssueDate").toLocalDate() : null;
                    dbReturnDate = rsUserIssued.getDate("ReturnDate") != null ? rsUserIssued.getDate("ReturnDate").toLocalDate() : null;
                    wIssueDate = rsUserIssued.getDate("WIssueDate") != null ? rsUserIssued.getDate("WIssueDate").toLocalDate() : null;
                    wReturnDate = rsUserIssued.getDate("WReturnDate") != null ? rsUserIssued.getDate("WReturnDate").toLocalDate() : null;
                    System.out.println("issueDate :"+issueDate);
                    System.out.println("returnDate :"+returnDate);
                    System.out.println("dbIssueDate :"+dbIssueDate);
                    System.out.println("dbreturn date :"+dbReturnDate);
                    if((dbIssueDate == null && dbReturnDate == null)){
                        return true;
                    }
                    else if(issueDate.isBefore(dbIssueDate) && returnDate.isBefore(dbIssueDate))
                    {
                        return true;
                    }else if(issueDate.isAfter(dbReturnDate) && returnDate.isBefore(dbReturnDate)){
                        return true;
                    }else if((wIssueDate == null && wReturnDate == null)){
                     return true;
                    } else if(issueDate.isBefore(wIssueDate) && returnDate.isBefore(wReturnDate)){
                        return true;
                    }else if(issueDate.isAfter(wReturnDate) && returnDate.isAfter(wReturnDate)){
                        return true;
                    }else{
                        return false;
                    }
                }if(!rsUserIssued.next()){
                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
          return  false;
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
                if(IssuedDate != null && ReturnDate != null){
                    IssueBooks issueBooks = new IssueBooks(BookId, AdminId, Library, Address,BookName,IssuedDate, ReturnDate);
                    studentBooks.add(issueBooks);
                }

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
