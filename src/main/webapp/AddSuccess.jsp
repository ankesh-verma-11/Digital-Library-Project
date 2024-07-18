<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.servlet.*" %>

<%
String memberId = (String) session.getAttribute("memberId");
if (memberId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
    return;
}
String status = (String) request.getAttribute("status");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Added Book</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #aeaeae;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #f4ecec;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .alert {
            margin-bottom: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <from action ="AddBook" method ="POST">
            <h2>Successfully added</h2>
            <% if (status != null) {  %>
                <div class="alert alert-<%= status.equals("success") ? "success" : "danger" %>">
                    <% if (status.equals("success")) { %>
                        <div>Congratulations! The book has been successfully added. </div>
                    <% } else if (status.equals("error")) { %>
                      <div>  An error occurred while adding the book. Please try again. </div>
                    <% } %>
                </div>
            <% } %>
            <a href="javascript:history.go(-2)" class="btn btn-secondary">Back</a>
        </from>
    </div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
