<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.librarymanagement.java.BookStore" %>
<%@ page import="com.librarymanagement.servlet.ViewBooks" %>

<%
String memberId = (String) session.getAttribute("memberId");
if (memberId == null) {
    response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
}
List<BookStore> books = (List<BookStore>) request.getAttribute("books");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Books</title>
    <style>
        /* Add your CSS styles here */
    </style>
</head>
<body>
    <h2>Library Books</h2>
    <div class="container">
        <form action="ViewBooks" method="post">
            <label for="memberId">Membership Id :</label>
            <input type="text" id="memberId" name="memberId" value="<%= memberId %>" readonly>
        </form>
    </div>
    <table border="1">
        <thead>
            <tr>
                <th>Book ID</th>
                <th>Membership ID</th>
                <th>Book Name</th>
                <th>Author</th>
                <th>Publication Year</th>
                <th>Edition</th>
                <th>Number of Books</th>
            </tr>
        </thead>
        <tbody>
            <% if (books != null) { %>
                <% for (BookStore book : books) { %>
                    <tr>
                        <td><%= book.getBookId() %></td>
                        <td><%= book.getMemberId() %></td>
                        <td><%= book.getBookName() %></td>
                        <td><%= book.getBookAuther() %></td>
                        <td><%= book.getPublishYear() %></td>
                        <td><%= book.getEdition() %></td>
                        <td><%= book.getQuantity() %></td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="7">No books found.</td>
                </tr>
            <% } %>
        </tbody>

    </table>
</body>
</html>
