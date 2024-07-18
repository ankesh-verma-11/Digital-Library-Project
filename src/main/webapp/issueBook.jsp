<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.librarymanagement.java.IssueBooks" %>

<%
String adminId = (String) session.getAttribute("memberId");
if (adminId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
    return;
}
List<IssueBooks> books = (List<IssueBooks>) request.getAttribute("books");
System.out.println("Size of IssuedBooks List :" + books.size());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Issued Books by Student</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
            }
            .container {
                width: 80%;
                margin: auto;
                text-align: center;
                margin-top: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .search-form {
                margin-top: 20px;
                display: flex;
                justify-content: center;
            }
            .search-form input[type="text"] {
                padding: 8px;
                width: 200px;
                margin-right: 10px;
            }
            .search-form button {
                padding: 8px 16px;
                background-color: #007bff;
                color: white;
                border: none;
                cursor: pointer;
            }
            .search-form button:hover {
                background-color: #0056b3;
            }

    </style>
</head>
<body>
<jsp:include page="AdminNavbar.jsp"/>

<div class="container">
    <form action="SearchBookServlet" method="post" class="mb-4">
        <h2>Books Issued to the Student from the Library</h2>
        <div class="row">
            <div class="col-md-4">
                <label for="memberId" class="form-label">Admin ID :</label>
                <input type="text" id="memberId" name="adminId" value="<%= adminId %>" readonly class="form-control">
            </div>
            <div class="col-md-4">
                <label for="bookId" class="form-label">Search Book :</label>
                <input type="text" id="bookId" name="search" required class="form-control">
            </div>
            <div class="col-md-4">
                <label>&nbsp;</label><br>


                <button type="submit" name="click" value="SearchIssuedBooks" class="btn btn-primary form-control">Search</button>
            </div>
        </div>
    </form>

    <table class="table table-bordered">
        <thead class="table-light">
        <tr>
            <th>Book ID</th>
            <th>Admin ID</th>
            <th>Student ID</th>
            <th>Book Name</th>
            <th>Student Name</th>
            <th>Student Email</th>
            <th>Issued Date</th>
            <th>Return Date</th>
        </tr>
        </thead>
        <tbody>
        <% if (books != null && !books.isEmpty()) { %>
            <% for (IssueBooks book : books) { %>
                <tr>
                    <td><%= book.getBookId() %></td>
                    <td><%= book.getAdminId() %></td>
                    <td><%= book.getStudentId() %></td>
                    <td><%= book.getBookName() %></td>
                    <td><%= book.getStudentName() %></td>
                    <td><%= book.getStudentEmail() %></td>
                    <td><%= book.getIssueDate() %></td>
                    <td><%= book.getReturnDate() %></td>
                </tr>
            <% } %>
        <% } else { %>
            <tr>
                <td colspan="8">No books found.</td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
