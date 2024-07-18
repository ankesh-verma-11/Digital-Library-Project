package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseAdmin;
import com.librarymanagement.java.BookStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AddBookServlet extends HttpServlet {
    private BookDatabaseAdmin bookDatabaseAdmin;
    @Override
    public void init() throws ServletException {
        bookDatabaseAdmin = new BookDatabaseAdmin();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String memberId = (session != null) ? (String) session.getAttribute("memberId") : null;

        if (memberId == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        String bookName = request.getParameter("book_name");
        String bookAuthor = request.getParameter("author");
        String publishYear = request.getParameter("publication_year");
        String bookQuantity = request.getParameter("number_of_books");
        String bookEdition = request.getParameter("book_edition");

        BookStore bookStore = new BookStore();
        bookStore.setMemberId(memberId);
        bookStore.setBookName(bookName);
        bookStore.setBookAuther(bookAuthor);
        bookStore.setPublishYear(publishYear);
        bookStore.setQuantity(Integer.parseInt(bookQuantity));
        bookStore.setEdition(bookEdition);
        bookStore.setIssuedBooks(0);
        boolean isAdded;
        try {
            isAdded = bookDatabaseAdmin.addBook(bookStore);
            if (isAdded) {
                response.sendRedirect(request.getContextPath() + "/addbook.jsp?status=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/addbook.jsp?status=error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/addbook.jsp?status=error");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
