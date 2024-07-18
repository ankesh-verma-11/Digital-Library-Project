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
import java.util.Objects;

public class StudentServlet extends HttpServlet {
    private BookDatabaseStudent bookDatabaseStudent;

    @Override
    public void init() throws ServletException {
        bookDatabaseStudent = new BookDatabaseStudent();

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        String memberId = (session != null) ? (String) session.getAttribute("memberId") : null;
        if (memberId == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        String studentId = (String) session.getAttribute("memberId");
        String click = request.getParameter("click");
        if(Objects.equals(click,"Issue"))
        {
            System.out.println("Issue book Information");
            List<BookStore> allBooks = bookDatabaseStudent.getAllBook();
            System.out.println("Size of list "+allBooks.size());
            request.setAttribute("books", allBooks);

            request.getRequestDispatcher("Student/IssueBooks.jsp").forward(request, response);
        }
        else if(Objects.equals(click,"View"))
        {

            System.out.println("View book information");
            List<BookStore> books = bookDatabaseStudent.getAllBook();
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/ViewBookList.jsp").forward(request, response);
        }
        else if(Objects.equals(click,"Renew"))
        {
            System.out.println("Enter Renew books information");
            List<IssueBooks> books = bookDatabaseStudent.getAllStudentIssuedBook(studentId);
            request.setAttribute("books", books);
            System.out.println("Size of list "+books.size());
            request.getRequestDispatcher("Student/RenewBooks.jsp").forward(request, response);
        }
        else if(Objects.equals(click,"Issued"))
        {
            System.out.println("Enter Issued books information");
            List<IssueBooks> books = bookDatabaseStudent.getAllStudentIssuedBook(studentId);
            request.setAttribute("books", books);
            System.out.println("Size of list "+books.size());
            request.getRequestDispatcher("Student/IssuedBooks.jsp").forward(request, response);
        }
        else if(Objects.equals(click,"Return")){
            System.out.println("Enter return books information");
            List<IssueBooks> books = bookDatabaseStudent.getAllStudentIssuedBook(studentId);
            System.out.println("Size of list "+books.size());
            request.setAttribute("books", books);
            request.getRequestDispatcher("Student/ReturnBooks.jsp").forward(request, response);
        }
        else if(Objects.equals(click,"Logout")){
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
        else{
            request.getRequestDispatcher("StudentProfile.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
