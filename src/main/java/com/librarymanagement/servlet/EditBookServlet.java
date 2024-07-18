package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseAdmin;
import com.librarymanagement.java.BookStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

public class EditBookServlet extends HttpServlet {
    private BookDatabaseAdmin bookDatabaseAdmin;

    @Override
    public void init() throws ServletException {
        bookDatabaseAdmin = new BookDatabaseAdmin();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        String memberId = (session != null) ? (String) session.getAttribute("memberId") : null;
        if (memberId == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        String adminId = request.getParameter("adminId");

        String click = request.getParameter("click");

        System.out.println("admin id : "+adminId);

        System.out.println("click : "+click);
        System.out.println("Edit servlet");
        if(Objects.equals(click,"Edit")) {
            System.out.println("Entered Edit");
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            System.out.println("bokkId : "+bookId);
            List<BookStore> allBooks = bookDatabaseAdmin.getBookForEditAdmin(String.valueOf(bookId), adminId);
            request.setAttribute("books", allBooks);
            request.getRequestDispatcher("/EditBookByAdmin.jsp").forward(request, response);
        }
        else if (Objects.equals(click,"SearchForEdit")){
            System.out.println("Entered SearchForEdit");
            String search = (request.getParameter("search"));
            List<BookStore> books = bookDatabaseAdmin.getBookByAdmin(search,adminId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("editBook.jsp").forward(request, response);
        }
        else if (Objects.equals(click,"Success")){
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            System.out.println("Entered For Edit");
            BookStore bookStore = new BookStore();
            bookStore.setMemberId(request.getParameter("memberId"));
            bookStore.setBookName(request.getParameter("book_name"));
            bookStore.setBookAuther(request.getParameter("author"));
            bookStore.setPublishYear(request.getParameter("publication_year"));
            bookStore.setQuantity(Integer.parseInt(request.getParameter("number_of_books")));
            bookStore.setEdition( request.getParameter("book_edition"));
            bookStore.setQuantity(Integer.parseInt(request.getParameter("number_of_books")));
            bookStore.setIssuedBooks(Integer.parseInt(request.getParameter("issued_books")));
            boolean result = bookDatabaseAdmin.editBookByAdmin(bookStore,adminId,bookId);
            if (result) {
                System.out.println("Successfully added");
                response.sendRedirect(request.getContextPath() + "/EditedSuccess.jsp?status=success");
            } else {
                System.out.println("Some error occurred");
                response.sendRedirect(request.getContextPath() + "/EditedSuccess.jsp?status=error");
            }
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
