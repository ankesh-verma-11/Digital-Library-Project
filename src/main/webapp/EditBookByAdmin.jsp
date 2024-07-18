<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.java.BookStore" %>

<%
String adminId = (String) session.getAttribute("memberId");
if (adminId == null) {
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
    <title>Issue Books</title>
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
        h2 {
            text-align: center;
        }
        form {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        label, input {
            margin-bottom: 5px;
            padding: 5px;
            border-radius: 5px;
            width: 40%;
        }
        input[readonly] {
            background-color: #e9ecef;
        }
        input[type="submit"], button[type="submit"] {
            width: 100px;
            margin-top: 10px;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
        }
        input[type="submit"]:hover, button[type="submit"]:hover {
            background-color: #218838;
        }
        .alert {
            text-align: center;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 5px;
            width: 40%;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
        }
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
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
            font-size: 1.5rem; /* Reduced font size */
        }
        .navbar button {
            color: white;
            text-align: center;
            padding: 10px 15px; /* Reduced padding */
            font-weight: bold;
            text-decoration: none;
            background-color: transparent;
            border: none;
            transition: background-color 0.3s, color 0.3s;
            margin: 0 5px; /* Added margin between buttons */
            font-size: 1rem; /* Reduced font size */
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
                    form {
                        width: 100%;
                    }
                    label, input, .alert {
                        width: 100%;
                    }
                }
    </style>
</head>
<body>
 <% if (status == null) { %>
            <%@ include file="AdminNavbar.jsp" %>
        <% } %>
    <div class="container">
        <h2>Edit Book Here</h2>
       <form action="EditBookServlet" method="post" onsubmit="return validateForm()">
            <% if ("success".equals(status)) { %>
                <div class="alert alert-success">Congratulations! The book has been successfully edited to your account.</div>
            <script>
                                setTimeout(function() {
                                    window.location.href = "editBook.jsp";
                                    }, 2000); // Wait for 2 seconds before redirecting
               </script>

            <% } else if ("error".equals(status)) { %>
                <div class="alert alert-error">An error occurred while editing the book. Please try again.</div>
                 <script>
                                                setTimeout(function() {
                                                    window.location.href = "editBook.jsp";
                                                    }, 2000); // Wait for 2 seconds before redirecting
                 </script>

            <% } %>

            <%
            if (books != null && !books.isEmpty()) {
                BookStore book = books.get(0); // Assuming you want to display the first book in the list
            %>
                <label for="bookId">Book Id:</label>
                <input type="text" id="bookId" name="bookId" value="<%= book.getBookId() %>" readonly>

                <label for="adminId">Admin Id:</label>
                <input type="text" id="adminId" name="adminId" value="<%= book.getMemberId() %>" readonly>

                <label for="name">Name of Book:</label>
                <input type="text" id="book_name" name="book_name" value="<%= book.getBookName() %>" required>

                <label for="author">Author:</label>
                <input type="text" id="author" name="author" value="<%= book.getBookAuther() %>" required>

                <label for="publication_year">Publication Year:</label>
                <input type="text" id="publication_year" name="publication_year" value="<%= book.getPublishYear() %>" required>

                <label for="book_edition">Book Edition:</label>
                <input type="number" id="book_edition" name="book_edition" value="<%= book.getEdition() %>" required>

                <label for="number_of_books">Quantity:</label>
                <input type="number" id="number_of_books" name="number_of_books" value="<%= book.getQuantity() %>" required>

                <label for="number_of_books">Number of Issued Books :</label>
                <input type="number" id="number_of_issued_books" name="issued_books" value="<%= book.getIssuedBooks() %>" readonly>

                <button type="submit" name="click" value="Success" class="btn btn-primary">Edit Book</button>
            <% } else { %>
                <p>No book information available.</p>
                <script>
                                setTimeout(function() {
                                window.location.href = "editBook.jsp";
                                }, 2000);
                </script>
            <% } %>

       </form>
    </div>
    <script>
        function validateForm() {
                    var quantity = document.getElementById("number_of_books").value;
                    var bookName = document.getElementById("book_name").value;
                    var authorName = document.getElementById("author").value;
                    var bookNamePattern = /^[a-zA-Z][a-zA-Z0-9\s]*$/;
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
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
