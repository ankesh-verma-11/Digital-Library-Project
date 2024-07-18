<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.servlet.*" %>

<%
String memberId = (String) session.getAttribute("memberId");
if (memberId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
    return;
}
String status = request.getParameter("status");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Books</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            width: 300px; /* Adjusted width */
            margin: 0 auto;
            padding: 10px;
            border: 1px solid #aeaeae;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #f4ecec;
        }
        h2 {
            text-align: center;
            margin-bottom: 10px;
        }
        form {
            margin-bottom: 10px;
        }
        label, input {
            margin-bottom: 10px;
        }
        .alert {
            margin-bottom: 10px;
            text-align: center;
        }
    </style>
    <script>
        function validateForm() {
            var quantity = document.getElementById("number_of_books").value;
            var bookName = document.getElementById("book_name").value;
            var authorName = document.getElementById("author").value;
            var bookNamePattern = /^[a-zA-Z][a-zA-Z0-9\s+$.~%-_#]*$/;
            var authorNamePattern = /^[a-zA-Z][a-zA-Z\s]*$/;
            if (quantity <= 0) {
                alert("Quantity must be greater than zero.");
                return false;
            }
            if (!bookNamePattern.test(bookName)) {
                alert("Book name should start with an alphabet and not contain any special characters.");
                return false;
            }
            if (!authorNamePattern.test(authorName)) {
                alert("Author name should start with an alphabet and not contain any special characters.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<jsp:include page="AdminNavbar.jsp" />
<div class="container">
    <h2>Add Book Here</h2>
    <% if (status != null) { %>
        <div class="alert alert-<%= status.equals("success") ? "success" : "danger" %>">
            <% if (status.equals("success")) { %>
                Congratulations! The book has been successfully added.
                <script>
                    setTimeout(function() {
                        window.location.href = "viewAllBooks.jsp";
                    }, 2000); // Wait for 2 seconds before redirecting
                </script>
            <% } else if (status.equals("error")) { %>
                An error occurred while adding the book. Please try again.
            <% } %>
        </div>
    <% } %>
    <% if (status == null) { %>
        <form action="AddBook" method="post" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="memberId">Membership Id:</label>
                <input type="text" id="memberId" name="memberId" value="<%= memberId %>" class="form-control" readonly>
            </div>
            <div class="form-group">
                <label for="book_name">Name of Book:</label>
                <input type="text" id="book_name" name="book_name" class="form-control" required placeholder="Enter book name">
            </div>
            <div class="form-group">
                <label for="author">Author:</label>
                <input type="text" id="author" name="author" class="form-control" required placeholder="Enter book author">
            </div>
            <div class="form-group">
                <label for="publication_year">Publication Year:</label>
                <input type="number" id="publication_year" name="publication_year" class="form-control" required placeholder="Enter publication year" min="1500" max="<%= Calendar.getInstance().get(Calendar.YEAR) %>">
            </div>
            <div class="form-group">
                <label for="book_edition">Book Edition:</label>
                <input type="number" id="book_edition" name="book_edition" class="form-control" required placeholder="Enter book edition">
            </div>
            <div class="form-group">
                <label for="number_of_books">Quantity:</label>
                <input type="number" id="number_of_books" name="number_of_books" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Add Book</button>
        </form>
    <% } %>
    <a href="javascript:history.go(-1)" class="btn btn-secondary">Back</a>
</div>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
