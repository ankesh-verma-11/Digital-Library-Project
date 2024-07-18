package com.librarymanagement.database;

import com.librarymanagement.java.AdminRegistration;
import com.librarymanagement.java.StudentRegistration;

import javax.naming.directory.InitialDirContext;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDatabase {
    Connection con = ConnectionProvider.getConnection();

    public int adminRegister(AdminRegistration admin) {
        int result = 0;
        try {

            String query = "insert into adminregister(Name,LibName,Address,Email,Role,Password,MembershipNumber) values(?,?,?,?,?,?,?)";
            PreparedStatement pstsm = con.prepareStatement(query);
            pstsm.setString(1, admin.getName());
            pstsm.setString(2, admin.getLibraryName());
            pstsm.setString(3, admin.getAddress());
            pstsm.setString(4, admin.getEmail());
            pstsm.setString(5, admin.getRole());
            pstsm.setString(6, admin.getPassword());
            pstsm.setString(7, admin.getMemberId());
            result = pstsm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            result=0;
        }
        return result;
    }

    public int studentRegister(StudentRegistration student) {
        int result = 0;
        try {
            //Connection con =  ConnectionProvider.getConnection();
            String query = "insert into StudentRegister(MembershipId,Name,Email,Password) values(?,?,?,?)";
            PreparedStatement pstsm = con.prepareStatement(query);
            pstsm.setString(1, student.getMemberId());
            pstsm.setString(2, student.getName());
            pstsm.setString(3, student.getEmail());
            pstsm.setString(4, student.getPassword());
            result = pstsm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkEmailExists(String email) {
        boolean emailExists = false;
        String sqlStudent = "SELECT COUNT(*) AS count FROM StudentRegister WHERE Email = ?";
        emailExists = checkIfExists(email, sqlStudent);

        if (!emailExists) {
            String sqlAdmin = "SELECT COUNT(*) AS count FROM AdminRegister WHERE Email = ?";
            emailExists = checkIfExists(email, sqlAdmin);
        }
        return emailExists;
    }
    private boolean checkIfExists(String email, String sqlQuery) {
        boolean exists = false;
        try (PreparedStatement stmt = con.prepareStatement(sqlQuery)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public boolean verifyDomain(String email) {
        try {
            String domain = email.substring(email.indexOf("@") + 1);
            Attributes attributes = new InitialDirContext().getAttributes("dns:/" + domain, new String[]{"MX"});
            Attribute mxAttr = attributes.get("MX");
            if (mxAttr == null) {
                return false;
            }
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
