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
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="30">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Books</title>
    <style>
        /* Global Styles */
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
    <script>
        if (!sessionStorage.getItem('reloaded')) {
            sessionStorage.setItem('reloaded', 'true');
            location.reload();
        } else {
            sessionStorage.removeItem('reloaded');
        }
    </script>

</head>
<body>
   <jsp:include page="AdminNavbar.jsp" />
  <form action="SearchBookServlet" method="post">
    <div class="container">
        <h2>Library Books</h2>
            <label for="memberId">Admin Id :</label>
            <input type="text" id="memberId" name="adminId" value="<%= memberId %>" readonly>
        <div class="search-form">
               <label for="bookId">Search Book :</label>
                <input type="text" id="bookId" name="search" required placeholder=" Search book by BookName BookAuthor">
                <button type="submit" name="click" value="SearchForView">Search</button>
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
                    <th>Total Books</th>
                    <th>Issued Books</th>
                    <th>Edit</th>
                    <th>Delete</th>
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
                                <form action="EditBookServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="adminId" value="<%= memberId %>">
                                    <input type="hidden"  name="bookId" value="<%= book.getBookId() %>">
                                    <button type="submit"  name="click" value= "Edit" class="btn btn-warning">Edit</button>
                                </form>
                            </td>
                            <td>
                                <form action="DeleteBookServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="adminId" value="<%=memberId  %>">
                                    <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                                    <button type="submit" name="click" value="Delete" class="btn btn-danger">Delete</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                <% } else { %>
                    <tr>
                        <td colspan="10">No books found.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
  </form>

</body>
</html>
