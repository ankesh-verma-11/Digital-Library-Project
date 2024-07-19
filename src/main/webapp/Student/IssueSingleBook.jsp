<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.java.BookStore" %>

<%
String studentId = (String) session.getAttribute("memberId");
if (studentId == null) {
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
        .container {
            background-color: rgba(255, 255, 255, 0.8);
            backdrop-filter: blur(10px);
            padding: 20px;
            border-radius: 10px;
            width: 100%;
            margin: auto;
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
            padding: 3px;
            border-radius: 5px;
            width: 60%;
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
        .button-group {
            display: flex;
            justify-content: space-between;
            width: 50%;
            margin-top: 20px;
        }
        .btn-secondary {
            background-color: #218838;
            margin-top: 10px;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
        }
        .btn-secondary:hover {
            background-color: #218838;
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
                width: 50%;
            }
            label, input, .alert {
                width: 50%;
            }
        }
    </style>
</head>
<body>
<% if (status == null) { %>
    <%@ include file="StudentNavbar.jsp" %>
<% } %>
<div class="container">
    <h2>Issue Books Here</h2>
    <form action="IssuedBook" method="post" onsubmit="return validateDates()">
       <%  if(status !=null){ %>
           <% if ("success".equals(status)) { %>
               <div class="alert alert-success">Congratulations! The book has been successfully issued to your account.</div>
           <% } else if ("notAvailable".equals(status)) { %>
               <div class="alert alert-error">Sorry, all copies of this book have already been issued.</div>
           <% } else if ("have".equals(status)) { %>
               <div class="alert alert-error">You already have this book issued to your account.</div>
           <% } else if ("booked".equals(status)) { %>
                          <div class="alert alert-error">Booking successfully for future</div>
                      <% }
            else if ("notAvailableForDate".equals(status)) { %>
               <div class="alert alert-error">You selected a book that is not available for selected dates</div>
           <% } %>
       <% } %>

        <% if (books != null && !books.isEmpty()) {
            BookStore book = books.get(0); // Assuming you want to display the first book in the list
        %>
            <label for="studentId">Student Membership Id:</label>
            <input type="text" id="studentId" name="studentId" value="<%= studentId %>" readonly>

            <label for="bookId">Book Id:</label>
            <input type="text" id="bookId" name="bookId" value="<%= book.getBookId() %>" readonly>

            <label for="adminId">Membership Id:</label>
            <input type="text" id="adminId" name="adminId" value="<%= book.getMemberId() %>" readonly>

            <label for="name">Name of Book:</label>
            <input type="text" id="name" name="book_name" value="<%= book.getBookName() %>" readonly>

            <label for="author">Author:</label>
            <input type="text" id="author" name="Author" value="<%= book.getBookAuther() %>" readonly>

            <label for="publication_year">Publication Year:</label>
            <input type="text" id="publication_year" name="publication_year" value="<%= book.getPublishYear() %>" readonly>

            <label for="book_edition">Book Edition:</label>
            <input type="text" id="book_edition" name="book_edition" value="<%= book.getEdition() %>" readonly>

            <label for="number_of_books">Quantity:</label>
            <input type="number" id="number_of_books" name="number_of_books" value="1" placeholder="Enter quantity of books" readonly>

            <label for="issue_date">Issue Date:</label>
            <input type="date" id="issue_date" name="issue_date" required>

            <label for="return_date">Return Date:</label>
            <input type="date" id="return_date" name="return_date" required>
            <span id="issue-error" class="error-message"></span>
            <div class="button-group">
                <a href="javascript:history.go(-1)" class="btn btn-secondary">Go Back</a>
                <button type="submit" name="click" value="Success" class="btn btn-primary">Issue Book</button>
            </div>
        <% } else { %>
            <p>No book information available.</p>
            <div class="button-group">
                <a href="javascript:history.go(-2)" class="btn btn-secondary">Go Back</a>
            </div>
        <% } %>
    </form>
</div>
<script>
                var today = new Date();
                var dd = String(today.getDate()).padStart(2, '0');
                var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
                var yyyy = today.getFullYear();
                today = yyyy + '-' + mm + '-' + dd;
                document.getElementById("issue_date").setAttribute("min", today);
                document.getElementById("return_date").setAttribute("min", today);

    function validateDates() {
        const issueDate = new Date(document.getElementById("issue_date").value);
        const returnDate = new Date(document.getElementById("return_date").value);
        const errorSpan = document.getElementById("issue-error");
        if (returnDate < issueDate) {
            errorSpan.textContent = "Return date must be later than the issue date.";
            return false;
        } else {
            errorSpan.textContent = "";
            return true;
        }
    }
</script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
