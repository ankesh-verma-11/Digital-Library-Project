package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseStudent;
import com.librarymanagement.java.BookStore;
import com.librarymanagement.java.IssueBooks;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class IssueSingleBook extends HttpServlet {
    private BookDatabaseStudent bookDatabase;
    private final  int ERROR = 0;
    private final  int ISSUE_BOOK = 1;
    private final  int RESERVE_BOOK = 2;
    private final  int NOT_AVAILABLE_BOOK = 3;
    private final  int NOT_AVAILABLE_FOR_SELECTED_DATES = 4;
    private final int ALREADY_HAVE = 5;

    @Override
    public void init() throws ServletException {
        bookDatabase = new BookDatabaseStudent();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        String memberId = (session != null) ? (String) session.getAttribute("memberId") : null;
        if (memberId == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String adminId = request.getParameter("adminId");
        String studentId = request.getParameter("studentId");
        String issueDate = request.getParameter("issue_date");
        String returnDate = request.getParameter("return_date");
        System.out.println("IssueDate :"+issueDate);
        System.out.println("ReturnDate :"+returnDate);
        IssueBooks book = new IssueBooks();
        book.setBookId(bookId);
        book.setAdminId(adminId);
        book.setStudentId(studentId);
        book.setIssueDate(issueDate);
        book.setReturnDate(returnDate);
        List<BookStore> books = bookDatabase.getSignleBook(bookId);
        if(books.isEmpty())
        {
            response.sendRedirect("Student/IssueSingleBook.jsp?status=NotAvailable");
        }
        try {
            int  result = bookDatabase.issueBooks(book);
            System.out.println("message : "+result);
            if (result == ALREADY_HAVE) {
                System.out.println("User already has book");
                response.sendRedirect("Student/IssueSingleBook.jsp?status=have");
            } else if(result ==NOT_AVAILABLE_BOOK ){
                System.out.println("NOT_AVAILABLE_BOOK");
                response.sendRedirect("Student/IssueSingleBook.jsp?status=notAvailable");
            }else if(result == NOT_AVAILABLE_FOR_SELECTED_DATES){
                System.out.println("NOT_AVAILABLE_FOR_SELECTED_DATES");
                response.sendRedirect("Student/IssueSingleBook.jsp?status=notAvailableForDate");
            }

            else if(result == RESERVE_BOOK){
                System.out.println("booking successful");
                response.sendRedirect("Student/IssueSingleBook.jsp?status=booked");
            }
            else if(result == ISSUE_BOOK){
                System.out.println("Successfully issued");
                response.sendRedirect("Student/IssueSingleBook.jsp?status=success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Student/IssuedBooks.jsp?status=error");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
