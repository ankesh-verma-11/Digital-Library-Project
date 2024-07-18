<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.java.IssueBooks" %>
<%
String studentId = (String) session.getAttribute("memberId");
if (studentId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
    return;
}
List<IssueBooks> books = (List<IssueBooks>) request.getAttribute("books");
String status = request.getParameter("status");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Renew Books</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-image: url('libb2.jpg');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        .container {
            max-width: 500px;
            width: 60%;
            padding: 20px;
            border: 1px solid #aeaeae;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #f4ecec;
            margin-top: 20px;
        }
        h2 {
            text-align: center;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label, input {
            margin-bottom: 5px;
            padding: 5px;
            border-radius: 5px;
        }
        input[readonly] {
            background-color: #e9ecef;
        }
        input[type="date"], input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .button-group {
            display: flex;
            justify-content: space-between;
        }
        button[type="submit"], .btn-secondary {
            padding: 10px;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
        }
        button[type="submit"] {
            background-color: #28a745;
        }
        button[type="submit"]:hover {
            background-color: #218838;
        }
        .btn-secondary {
            background-color: #6c757d;
            text-decoration: none;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        .alert {
            text-align: center;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 5px;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
        }
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>
   <% if (status == null) { %>
       <%@ include file="StudentNavbar.jsp" %>
   <% } %>
    <div class="container">
        <h2>Renew Books Here</h2>
        <form action="RenewBook" method="post" onsubmit="return validateDates()">
            <% if ("success".equals(status)) { %>
                <div class="alert alert-success">Congratulations! The book has been successfully renewed to your account.</div>
                <a href="javascript:history.go(-2)" class="btn btn-secondary">Back</a>
            <% } else if ("error".equals(status)) { %>
                <div class="alert alert-error">An error occurred while renewing the book. Please try again.</div>
               <div class="button-group">
                   <a href="javascript:history.go(-1)" class="btn btn-secondary">Go Back</a>
               </div>
            <% } else if ("NotAvailable".equals(status)) { %>
                <div class="alert alert-error">You entered a book that is not present in your account. Please issue the book first.</div>
                <div class="button-group">
                    <a href="javascript:history.go(-1)" class="btn btn-secondary">Go Back</a>
                </div>
            <% } %>

            <% if (books != null && !books.isEmpty()) {
                IssueBooks book = books.get(0); // Assuming you want to display the first book in the list
            %>
                <label for="studentId">Student Id :</label>
                <input type="text" id="studentId" name="studentId" value="<%= studentId %>" readonly>

                <label for="bookId">Book Id :</label>
                <input type="text" id="bookId" name="bookId" value="<%= book.getBookId() %>" readonly>

                <label for="adminId">Admin Id :</label>
                <input type="text" id="adminId" name="adminId" value="<%= book.getAdminId() %>" readonly>

                <label for="bookName">Book Name :</label>
                <input type="text" id="bookName" name="bookName" value="<%= book.getBookName() %>" readonly>

                <label for="library">Library Name :</label>
                <input type="text" id="library" name="library" value="<%= book.getLibrary() %>" readonly>

                <label for="address">Library Address :</label>
                <input type="text" id="address" name="address" value="<%= book.getAddress() %>" readonly>

                <label for="issueDate">Issue Date :</label>
                <input type="text" id="issueDate" name="issue_date" value="<%= book.getIssueDate() %>" readonly>

                <label for="return_date">Return Date :</label>
                <input type="date" id="return_date" name="return_date" value="<%= book.getReturnDate() %>" required>
                <span id="renew-error" class="error-message"></span>
                <div class="button-group">
                    <a href="javascript:history.go(-1)" class="btn btn-secondary">Go Back</a>
                    <button type="submit" name="click" value="RenewBook">Renew Book</button>
                </div>
            <% } else { %>
                <p>No book information available.</p>
            <% } %>
        </form>
    </div>
    <script>
    var today = new Date();
                    var dd = String(today.getDate()).padStart(2, '0');
                    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
                    var yyyy = today.getFullYear();
                    today = yyyy + '-' + mm + '-' + dd;
                    document.getElementById("return_date").setAttribute("min", today);
        function validateDates() {
            var issueDate = new Date(document.getElementById("issueDate").value);
            var returnDate = new Date(document.getElementById("return_date").value);
            var errorSpan = document.getElementById("renew-error");

            if (returnDate < issueDate) {
                errorSpan.textContent = "Return date cannot be earlier than issue date.";
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
