a
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.librarymanagement.java.BookStore" %>

<%
String adminId = (String) session.getAttribute("memberId");
if (adminId == null) {
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
           <h2 class="text-center">Edit Books</h2>
           <form action="EditBookServlet" method="post" class="my-4">
                   <div class="form-group row">
                       <label for="memberId" class="col-sm-2 col-form-label">Membership Id:</label>
                       <div class="col-sm-10">
                           <input type="text" class="form-control" name="adminId" value="<%= adminId %>" readonly >
                       </div>
                   </div>
                   <div class="form-group row">

                           <label for="bookId" class="col-sm-2 col-form-label">Search Book :</label>
                           <div class="col-sm-8">
                               <input type="text" class="form-control" name="search" placeholder="Search book by BookName BookAuthor PublicationYear Edition" required>
                           </div>
                           <div class="col-sm-2">
                               <button class="btn btn-outline-primary btn-block mb-2" name="click" value="SearchForEdit" type="submit">Search</button>
                           </div>
                   </div>
           </form>

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
                   <th>Number of Issued Books</th>
                   <th>Actions</th> <!-- New column for Actions -->
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

                                <form action="EditBookServlet" method="post">
                                    <input type="hidden" name="adminId" value="<%= adminId %>">
                                    <input type="hidden" name="bookId" value="<%= book.getBookId() %>">
                                    <button type="submit" class="btn btn-warning" name="click" value="Edit">Edit</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                <% } else { %>
                    <tr>
                        <td colspan="9">No books found.</td>

                    </tr>
                <% } %>
            </tbody>
          </table>
      </div>
  </form>
</body>
</html>
