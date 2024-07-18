package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseAdmin;
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

public class AdminProfileServlet extends HttpServlet {
    private BookDatabaseAdmin bookDatabaseAdmin;

    @Override
    public void init() throws ServletException {
        bookDatabaseAdmin = new BookDatabaseAdmin();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        String memberId = (session != null) ? (String) session.getAttribute("memberId") : null;

        if (memberId == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        String click = request.getParameter("click");

        if (Objects.equals(click, "Delete")) {
            List<BookStore> allBooks = bookDatabaseAdmin.getAllBooks(memberId);
            request.setAttribute("books", allBooks);
            request.getRequestDispatcher("/deleteBooks.jsp").forward(request, response);
        }
        else if (Objects.equals(click, "View")) {
            List<BookStore> allBooks = bookDatabaseAdmin.getAllBooks(memberId);
            request.setAttribute("books", allBooks);
            request.getRequestDispatcher("/viewAllBooks.jsp").forward(request, response);
        }
        else if (Objects.equals(click, "Edit")) {
            List<BookStore> allBooks = bookDatabaseAdmin.getAllBooks(memberId);
            request.setAttribute("books", allBooks);
            request.getRequestDispatcher("/editBook.jsp").forward(request, response);
        }
        else if (Objects.equals(click, "Add")) {
            request.getRequestDispatcher("/addbook.jsp").forward(request, response);
        }
        else if (Objects.equals(click, "Issue")) {
            System.out.println("Entered Issued Books ");
            List<IssueBooks> issuedBooks = bookDatabaseAdmin.getAllIssuedBookByStudents(memberId);
            System.out.println("size of list "+issuedBooks.size());
            request.setAttribute("books", issuedBooks);
            request.getRequestDispatcher("/issueBook.jsp").forward(request, response);
        }
        else if (Objects.equals(click, "Logout")) {
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
        }
        else {
            request.getRequestDispatcher("/adminProfile.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // Redirect GET requests to POST method
    }
}
