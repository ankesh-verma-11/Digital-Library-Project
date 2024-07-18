<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.librarymanagement.java.IssueBooks" %>
<%@ page import="com.librarymanagement.servlet.ViewBooks" %>

<%
String studentId = (String) session.getAttribute("memberId");
if (studentId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
    return;
}
List<IssueBooks> books = (List<IssueBooks>) request.getAttribute("books");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Issued Books</title>
    <style>
        /* Add your CSS styles here */
        table {
            width: 80%;
            border-collapse: collapse;
            margin-top: 20px;
            margin-left: 50px
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .container {
            width: 50%;
            margin: auto;
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="StudentNavbar.jsp" />
    <div class="container">
     <h2>Your searched book</h2>
            <form action="SearchIssuedBookServlet" method="post">
                <label for="memberId">Student Membership Id :</label>
                <input type="text" id="studentId" name="studentId" value="<%= studentId %>" readonly><br><br>
                <label for="bookId">Search Book by ID:</label>
                <input type="text" id="bookId" name="bookId" required placeholder="Enter Book ID ">
               <button type="submit" name="click" value="Search" class="btn btn-primary">Search Book</button>
            </form>
        </div>
    <table>
        <thead>
            <tr>
                <th>Book ID</th>
                <th>Admin ID</th>
                <th>Book Name</th>
                <th>Library</th>
                <th>Address</th>
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
                        <td><%= book.getBookName() %></td>
                        <td><%= book.getLibrary() %></td>
                        <td><%= book.getAddress() %></td>
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
</body>
</html>
