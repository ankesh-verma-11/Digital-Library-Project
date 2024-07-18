package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseAdmin;
import com.librarymanagement.java.BookStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewBooks extends HttpServlet {
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
        List<BookStore> books = bookDatabaseAdmin.getAllBooks(memberId);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/viewAllBooks.jsp").forward(request, response);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // Redirect GET requests to POST method
    }
}
