package com.librarymanagement.servlet;

import com.librarymanagement.java.AdminRegistration;
import com.librarymanagement.java.EmailSend;
import com.librarymanagement.java.StudentRegistration;
import com.librarymanagement.database.RegistrationDatabase;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private RegistrationDatabase registrationDatabase;

    @Override
    public void init() throws ServletException {
        registrationDatabase = new RegistrationDatabase();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String address;
        String libName;
        String membershipId;
        String password = request.getParameter("pass");
        HttpSession session = request.getSession();
        boolean domainExist = registrationDatabase.verifyDomain(email);
        System.out.println("domain :"+domainExist);
        if(!domainExist){
            System.out.println("domain :"+domainExist);
            response.sendRedirect("Registration.jsp?status=notExistsDomain");
            return;
        }
        boolean emailExists = registrationDatabase.checkEmailExists(email);
        System.out.println("domain :"+emailExists);
        if (emailExists) {
            response.sendRedirect("Registration.jsp?status=exists");
        } else {
            if (Objects.equals(role, "admin")) {
                address = request.getParameter("address");
                libName = request.getParameter("lib_name");
                membershipId = "AD" + EmailSend.getUniquMemberahipNumber();
                String status = EmailSend.sendEmailToAdmin(name, email, membershipId, password, libName);
                if (Objects.equals(status, "sent")) {
                    AdminRegistration adminRegistration = new AdminRegistration();
                    adminRegistration.setName(name);
                    adminRegistration.setEmail(email);
                    adminRegistration.setRole(role);
                    adminRegistration.setAddress(address);
                    adminRegistration.setLibraryName(libName);
                    adminRegistration.setPassword(password);
                    adminRegistration.setMemberId(membershipId);
                    try {
                        int statuss = registrationDatabase.adminRegister(adminRegistration);
                        if (statuss==0){
                            response.sendRedirect("Registration.jsp?status=error");
                        }
                        session.setAttribute("name", name);
                        response.sendRedirect("Registration.jsp?status=success");
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendRedirect("Registration.jsp?status=error");
                    }
                } else {
                    response.sendRedirect("Registration.jsp?status=error");
                }
            } else {
                membershipId = "SD" + EmailSend.getUniquMemberahipNumber();
                String status = EmailSend.sendEmailToStudent(name, email, membershipId, password, role);
                if (Objects.equals(status, "sent")) {
                    StudentRegistration studentRegistration = new StudentRegistration();
                    studentRegistration.setName(name);
                    studentRegistration.setEmail(email);
                    studentRegistration.setRole(role);
                    studentRegistration.setPassword(password);
                    studentRegistration.setMemberId(membershipId);
                    try {
                        registrationDatabase.studentRegister(studentRegistration);
                        session.setAttribute("name", name);
                        session.setAttribute("email", email);
                        response.sendRedirect("Registration.jsp?status=success");
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendRedirect("Registration.jsp?status=error");
                    }
                } else {
                    response.sendRedirect("Registration.jsp?status=error");
                }
            }
        }
    }
}
