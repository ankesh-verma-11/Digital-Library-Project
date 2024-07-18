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
    <title>Renew Book</title>
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
            width: 100%;
            margin: auto;
            text-align: center;
            margin-top: 20px;
            background-color: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(10px);
            padding: 20px;
            border-radius: 10px;
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
        input[type="text"], input[type="date"] {
            width: calc(100% - 24px);
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
        @media (max-width: 768px) {
            .container {
                width: 100%;
                margin-top: 20px;
                padding: 15px;
            }
            table, th, td {
                font-size: 14px;
            }
            input[type="text"], input[type="date"] {
                width: 80%;
            }
        }
    </style>
</head>
<body>
   <jsp:include page="StudentNavbar.jsp" />
    <form action="RenewBook" method="post">
        <div class="container">
            <h2>Renew Books Here</h2>

                <input type="hidden" id="studentId" name="studentId" value="<%= studentId %>" readonly><br><br>
                <label for="bookId">Search Book :</label>
                <input type="text" id="bookId" name="search" required placeholder="Search Book by BookName, BookAuthor and BookPublicationYear"><br><br>
                <button type="submit" name="click" value="SearchRenew">Search Book</button>
    </form>
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
                    <th>Action</th>
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
                            <td>
                                <form action="RenewBook" method="post">
                                    <input type="hidden" name="studentId" value="<%= studentId %>">
                                    <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                                    <button type="submit" name="click" value="Renew">Renew</button>
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