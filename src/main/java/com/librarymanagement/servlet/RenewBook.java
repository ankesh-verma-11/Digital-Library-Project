package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseStudent;
import com.librarymanagement.java.IssueBooks;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class RenewBook extends HttpServlet {
    private BookDatabaseStudent bookDatabase;

    @Override
    public void init() throws ServletException {
        bookDatabase = new BookDatabaseStudent();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        String memberId = (session != null) ? (String) session.getAttribute("memberId") : null;
        if (memberId == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        String studentId  = request.getParameter("studentId");
        String issueDate = request.getParameter("issue_date");
        String returnDate = request.getParameter("return_date");
        String click = request.getParameter("click");
        if(Objects.equals(click,"RenewBook"))
        {
            System.out.println("Entered renew book ");
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            boolean result = bookDatabase.renewBook(bookId,studentId,issueDate,returnDate);
            if (result) {
                response.sendRedirect("Student/RenewSingleBook.jsp?status=success");
            } else {
                System.out.println("Some error occurred");
                response.sendRedirect("Student/RenewSingleBook.jsp?status=error");
            }

        }
        else if (Objects.equals(click,"Renew"))
        {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            List<IssueBooks> books = bookDatabase.getIssuedBooks(bookId,studentId);
            System.out.println(books);
            if(books.isEmpty())
            {
                response.sendRedirect("Student/RenewSingleBook.jsp?status=NotAvailable");
            }else {
                request.setAttribute("books", books);
                request.getRequestDispatcher("Student/RenewSingleBook.jsp").forward(request, response);
            }
        }
        else if (Objects.equals(click,"SearchRenew"))
        {
            String search = request.getParameter("search");
            List<IssueBooks> books = bookDatabase.getIssuedBooksOfStudent(search, studentId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/RenewBooks.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // Redirect GET requests to POST method
    }

}

