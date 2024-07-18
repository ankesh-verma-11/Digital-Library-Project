package com.librarymanagement.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginDatabase {
    private  final int Login = 1;
    private  final int IncorrectInformation =0;
    private final int Error = -1;
    Connection con = ConnectionProvider.getConnection();

    public int adminLogin(String memberId, String password, String role) {
      int result=Error;
        try {
            String query = "SELECT * FROM adminregister WHERE membershipNumber = ? AND Password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                result =Login;
            } else {
                result= IncorrectInformation;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result=Error;
        }
        return result;
    }

    public int studentLogin(String memberId, String password, String role) {
        if (!Objects.equals(role, "Student")) {
            return 0; // Role mismatch
        }

        try {
            String query = "SELECT * FROM StudentRegister WHERE membershipId = ? AND Password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, memberId);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                // User found in the database
                String dbPassword = resultSet.getString("password");

                if (!Objects.equals(dbPassword, password)) {

                    return 2;
                } else {
                    // Credentials are correct
                    return 1;
                }
            } else {
                // No matching student found
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicates an error occurred
        }
    }
}
