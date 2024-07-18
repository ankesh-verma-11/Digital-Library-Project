package com.librarymanagement.servlet;

import com.librarymanagement.database.LoginDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class LoginServlet extends HttpServlet {
    private LoginDatabase loginDatabase = new LoginDatabase();
    private final int LOGIN_SUCCESS = 1;
    private final int INCORRECT_INFORMATION = 0;
    private final int ERROR = -1;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String role = request.getParameter("role");
        String memberId = request.getParameter("memberId");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if (Objects.equals(role, "Admin")) {
            int status = loginDatabase.adminLogin(memberId, password, role);
            if (status == LOGIN_SUCCESS) {
                session.setAttribute("memberId", memberId);
                response.sendRedirect("adminProfile.jsp?status=correct");
            } else if (status == INCORRECT_INFORMATION) {
                response.sendRedirect("Login.jsp?status=incorrect");
            } else {
                response.sendRedirect("Login.jsp?status=error");
            }
        } else {
            int status = loginDatabase.studentLogin(memberId, password, role);
            if (status == LOGIN_SUCCESS) {
                session.setAttribute("memberId", memberId);
                response.sendRedirect("StudentProfile.jsp");
            } else if (status == INCORRECT_INFORMATION) {
                response.sendRedirect("Login.jsp?status=incorrect");
            } else {
                response.sendRedirect("Login.jsp?status=error");
            }
        }
    }
}
