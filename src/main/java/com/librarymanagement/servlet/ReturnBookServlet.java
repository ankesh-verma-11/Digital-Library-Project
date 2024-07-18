package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseStudent;
import com.librarymanagement.java.IssueBooks;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ReturnBookServlet extends HttpServlet {
    private BookDatabaseStudent bookDatabaseStudent;

    @Override
    public void init() throws ServletException {
        bookDatabaseStudent = new BookDatabaseStudent();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String memberId = (session != null) ? (String) session.getAttribute("memberId") : null;
        if (memberId == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        String click = request.getParameter("click");
        String studentId =request.getParameter("studentId");

        if (Objects.equals(click, "Return")) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            boolean status = bookDatabaseStudent.returnBook(bookId, studentId);
            if (status) {
                response.sendRedirect(request.getContextPath() + "/Student/ReturnBooks.jsp?status=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/Student/ReturnBooks.jsp?status=error");
            }
        } else if (Objects.equals(click, "SearchReturn")) {
            String search = request.getParameter("search");
            List<IssueBooks> books = bookDatabaseStudent.getIssuedBooksOfStudent(search,studentId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/ReturnBooks.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("StudentProfile.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
