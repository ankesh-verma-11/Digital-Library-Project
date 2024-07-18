package com.librarymanagement.servlet;

import com.librarymanagement.database.BookDatabaseAdmin;
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

public class SearchBookServlet extends HttpServlet {
    private BookDatabaseAdmin bookDatabaseAdmin;
    private BookDatabaseStudent bookDatabaseStudent;

    public void init() {
        bookDatabaseAdmin = new BookDatabaseAdmin();
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
        String adminId = request.getParameter("adminId");
        String studentId = request.getParameter("studentId");
        String search = request.getParameter("search");
        String click = request.getParameter("click");
        System.out.println("admin Id : "+adminId);
        System.out.println("click :"+click);
        System.out.println("serch book by "+search);
        if(Objects.equals(click,"SearchForView"))
        {
            List<BookStore> books = bookDatabaseAdmin.getBookByAdmin(search,adminId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("/viewAllBooks.jsp").forward(request, response);
        }
        else if (Objects.equals(click,"SearchIssuedBooks"))
        {
            List<IssueBooks> books = bookDatabaseAdmin.getAIssuedBookByStudent(adminId,search);
            request.setAttribute("books", books);
            request.getRequestDispatcher("/issueBook.jsp").forward(request, response);
        }
        else if (Objects.equals(click,"SearchIssuedBooksForStudent"))
        {
            List<IssueBooks> books = bookDatabaseStudent.getIssuedBooksOfStudent(search,studentId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/IssuedBooks.jsp").forward(request, response);
        }
        else if (Objects.equals(click,"SearchBook")){
            int bookId= Integer.parseInt(request.getParameter("bookId"));
            List<BookStore> books = bookDatabaseStudent.getSignleBook(bookId);
            request.setAttribute("books", books);
            request.getRequestDispatcher("/issueBook.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
