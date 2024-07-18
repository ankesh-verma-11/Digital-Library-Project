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
            int result = bookDatabase.issueBooks(book);
            if (result == 0) {
                System.out.println("User already has book");
                response.sendRedirect("Student/IssueSingleBook.jsp?status=have");
            } else if(result ==-1 ){
                System.out.println("All the books issued not");
                response.sendRedirect("Student/IssueSingleBook.jsp?status=Donot");
            }else if(result ==4 ){
                System.out.println("booking successful");
                response.sendRedirect("Student/IssueSingleBook.jsp?status=booked");
            }
            else {
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
