<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.librarymanagement.java.BookStore" %>

<%
String memberId = (String) session.getAttribute("memberId");
if (memberId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
    return;
}
List<BookStore> books = (List<BookStore>) request.getAttribute("books");
String status = request.getParameter("status");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Books</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
    <jsp:include page="AdminNavbar.jsp" />
    <div class="container mt-5">
        <% if (status != null) { %>
            <h2>Delete Book Status</h2>
            <div class="alert alert-<%= status.equals("success") ? "success" : "danger" %>">
                <% if (status.equals("success")) { %>
                    <div>Congratulations! The book has been successfully deleted.</div>
                     <script>
                                                    setTimeout(function() {
                                                        window.location.href = "deleteBooks.jsp";
                                                        }, 3000); // Wait for 2 seconds before redirecting
                                   </script>

                <% } else if (status.equals("decreased")) { %>
                    <div>Note: This book cannot be deleted as it has been issued to students. However, the quantity of available books in the library has been decreased. Please ensure all issued copies are returned before attempting to delete the book.</div>
                     <script>
                                                    setTimeout(function() {
                                                        window.location.href = "deleteBooks.jsp";
                                                        }, 3000); // Wait for 2 seconds before redirecting
                                   </script>
                <% } else if (status.equals("error")) { %>
                    <div>Either the book is not present in your library or an error occurred while deleting the book. Please try again.</div>
                    <script>
                                                                       setTimeout(function() {
                                                                           window.location.href = "deleteBooks.jsp";
                                                                           }, 3000); // Wait for 2 seconds before redirecting
                                                      </script>
                <% } %>
            </div>
        <% } %>
        <form action="DeleteBookServlet" method="post" class="my-4">
            <div class="form-group row">
                <label for="memberId" class="col-sm-2 col-form-label">Admin Id:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="adminId" value="<%= memberId %>" readonly>
                </div>
            </div>
            <div class="form-group row">
                <label for="bookId" class="col-sm-2 col-form-label">Enter Book Id:</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="deleteBookId" name="search" required placeholder="search book">
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-outline-primary btn-block mb-2" name="click" value="SearchForDelete" type="submit">Search</button>
                </div>
            </div>
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
                    <th>Issued Books</th>
                     <th>Actions</th>
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
                            <td><%= book.getIssuedBooks() %></td>
                             <td>
                                                            <form action="DeleteBookServlet" method="post">
                                                                <input type="hidden" name="adminId" value="<%= memberId %>">
                                                                <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                                                                <button type="submit" class="btn btn-danger btn-block" name="click" value="Delete">Delete</button>
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
