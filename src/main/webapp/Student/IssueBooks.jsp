<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.librarymanagement.java.BookStore" %>
<%@ page import="com.librarymanagement.servlet.ViewBooks" %>

<%
String studentId = (String) session.getAttribute("memberId");
if (studentId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
    return;
}
List<BookStore> books = (List<BookStore>) request.getAttribute("books");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Issued Books</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('libb2.jpg');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            margin: 0;
            padding: 0;
        }
        .container {
            background-color: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(10px);
            padding: 20px;
            border-radius: 10px;
            width: 80%;
            margin: auto;
            margin-top: 50px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        .navbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: rgba(0, 0, 0, 0.8);
            padding: 10px 20px;
            color: white;
        }
        .navbar-left {
            display: flex;
            align-items: center;
        }
        .navbar h1 {
            color: white;
            margin: 0;
            font-weight: bold;
        }
        .navbar button {
            color: white;
            text-align: center;
            padding: 10px 20px;
            font-weight: bold;
            text-decoration: none;
            background-color: transparent;
            border: none;
            transition: background-color 0.3s, color 0.3s;
        }
        .navbar button:hover {
            background-color: #ddd;
            color: black;
        }
        .navbar img {
            height: 40px;
            margin-right: 10px;
            border-radius: 50%;
        }
    </style>
</head>
<body>
   <jsp:include page="StudentNavbar.jsp" />
    <div class="container">
        <h2>All Books here you want to issue books</h2>
        <form action="SearchIssuedBookServlet" method="post">
            <input type="hidden" id="studentId" name="studentId" value="<%= studentId %>" readonly><br><br>
            <label for="bookId">Search book :</label>
            <input type="text" id="bookId" name="search" required placeholder="Search book by BookName BookAuthor BooK Publication year and Edition">
            <button type="submit" name="click" value="SearchAll" class="btn btn-primary">Search Book</button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>Book ID</th>
                    <th>Membership ID</th>
                    <th>Book Name</th>
                    <th>Author</th>
                    <th>Publication Year</th>
                    <th>Edition</th>
                    <th>Total Books</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% if (books != null && !books.isEmpty()) { %>
                    <% for (BookStore book : books) { %>
                        <tr>
                            <td><%= book.getBookId() %></td>
                            <td><%= book.getMemberId() %></td>
                            <td><%= book.getBookName() %></td>
                            <td><%= book.getBookAuther() %></td>
                            <td><%= book.getPublishYear() %></td>
                            <td><%= book.getEdition() %></td>
                            <td><%= book.getQuantity() %></td>
                            <td>
                                <form action="SearchIssuedBookServlet" method="post">
                                    <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                                    <input type="hidden" name="studentId" value="<%= book.getMemberId() %>">
                                    <button type="submit" name="click" value="IssueBook" class="btn btn-primary">Issue Book</button>
                                </form>
                            </td>
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
</body>
</html>
