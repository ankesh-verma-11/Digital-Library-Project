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

import java.util.List;
import java.util.Objects;


public class SearchIssuedBookServlet extends HttpServlet {
    private BookDatabaseStudent bookDatabaseStudent;

    public void init() {
        bookDatabaseStudent = new BookDatabaseStudent();
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
        String search = request.getParameter("search");
        String click = request.getParameter("click");

        if(Objects.equals(click,"SearchIssued"))
        {
            List<IssueBooks> books = bookDatabaseStudent.getIssuedBooksOfStudent(search,studentId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/IssuedBooks.jsp").forward(request, response);
        }
        else if(Objects.equals(click,"SearchRenew"))
        {
            int bookId= Integer.parseInt(request.getParameter("bookId"));
            List<IssueBooks> books = bookDatabaseStudent.getIssuedBooksOfStudent(search,studentId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/RenewBooks.jsp").forward(request, response);
        }

        else if (Objects.equals(click,"SearchAll")) {
            List<BookStore> books = bookDatabaseStudent.searchBooks(search);
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/ViewBookList.jsp").forward(request, response);
        }
        else if (Objects.equals(click,"IssueBook")){
            int bookId= Integer.parseInt(request.getParameter("bookId"));
            List<BookStore> books = bookDatabaseStudent.getSignleBook(bookId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/IssueSingleBook.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
