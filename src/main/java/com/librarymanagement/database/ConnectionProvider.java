package com.librarymanagement.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
    private static  Connection con;
    public static Connection getConnection () {
        try {
            if (con == null)
            {
                // lado driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // creating connection
                String url = "jdbc:mysql://localhost:3306/LibraryManagement";
                String userName = "root";
                String password = "Ankesh";
                con = DriverManager.getConnection(url, userName, password);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
}
