package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseAdmin;
import com.librarymanagement.java.BookStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DeleteBookServlet extends HttpServlet {
    private BookDatabaseAdmin bookDatabaseAdmin;

    @Override
    public void init() throws ServletException {
        bookDatabaseAdmin = new BookDatabaseAdmin();
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
        String search = (request.getParameter("search"));
        String bookId = (request.getParameter("bookId"));
        String click = request.getParameter("click");

        if (Objects.equals(click, "Delete")) {

            int status = bookDatabaseAdmin.deleteBook(Integer.parseInt(bookId), memberId);
            if (status == 0) {
                response.sendRedirect(request.getContextPath() + "/DeleteSuccess.jsp?status=error");
            } else if (status ==-1) {
                response.sendRedirect(request.getContextPath() + "/DeleteSuccess.jsp?status=decreased");
            } else {
                response.sendRedirect(request.getContextPath() + "/DeleteSuccess.jsp?status=success");
            }
        }
        else if (Objects.equals(click, "SearchForDelete")) {
            List<BookStore> books = bookDatabaseAdmin.getBookByAdmin(search,memberId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("/deleteBooks.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
