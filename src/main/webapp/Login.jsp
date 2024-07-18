<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.servlet.*" %>

<%
String status = (String) request.getParameter("status");
%>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-image: url('images/libb2.jpg');
            background-size: cover;
            background-position: center;
        }
        .navbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: rgba(0, 0, 0, 0.8);
            padding: 5px 20px;
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
        .navbar a {
            color: white;
            text-align: center;
            padding: 14px 20px;
            font-weight: bold;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .navbar img {
            height: 40px;
            margin-right: 10px;
            border-radius: 50%;
        }
        .container {
            width: 40%;
            text-align: center;
            border: 2px solid;
            margin: auto;
            padding: 20px;
            border-radius: 20px;
            background-color: rgba(213, 204, 204, 0.8);
            margin-top: 120px;
        }
        .container h4 {
            margin-bottom: 20px;
        }
        .form-group {
            font-weight: bold;
            margin-bottom: 10px;
            text-align: left;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 5px;
            border: 1px solid #0f0f0f;
            border-radius: 5px;
        }
        .admin-fields {
            display: none;
        }
        .error-message {
            color: red;
            font-size: 14px;
        }
        .container2 {
                    margin-top : 400px;
                    max-width: 500px;
                    margin: 0 auto;
                    padding: 20px;
                    border: 1px solid #aeaeae;
                    border-radius: 10px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    background-color: #f4ecec;
                }
                h2 {
                    text-align: center;
                    margin-bottom: 20px;
                }
                .alert {
                    margin-bottom: 10px;
                    text-align: center;
                }
    </style>
    <script>
        function validateForm() {
            var isValid = true;
            var memberId = document.getElementById("memberId").value;
            var memberIdPattern = /^[a-zA-Z][a-zA-Z0-9\s]*$/;
            if (!memberIdPattern.test(memberId)) {
            alert("MemberId should start with an alphabet and contain alphabet and digit.");
                isValid = false;
            }
            var requiredFields = document.querySelectorAll('.form-group input, .form-group select');
            requiredFields.forEach(function(field) {
                var errorSpan = field.nextElementSibling;
                if (!errorSpan || !errorSpan.classList.contains('error-message')) {
                    errorSpan = document.createElement('span');
                    errorSpan.classList.add('error-message');
                    field.parentNode.appendChild(errorSpan);
                }
                if (field.value.trim() === "") {
                    errorSpan.textContent = "This field is required";
                    isValid = false;
                } else {
                    errorSpan.textContent = "";
                }
            });
            return isValid;
        }
        document.addEventListener('DOMContentLoaded', function() {
            var form = document.querySelector('form');
            form.addEventListener('submit', function(event) {
                if (!validateForm()) {
                    event.preventDefault();
                }
            });
        });
    </script>
</head>
<body>
<div class="navbar">
    <div class="navbar-left">
        <img src="images/Book.jpg" alt="Logo" />
        <h1>LibraryHub</h1>
    </div>
    <div>
        <a href="Registration.jsp">Register</a>
        <a href="Login.jsp">Login</a>
    </div>
</div>
<div class="container">
    <h4>Login Page</h4>
    <% if (status != null && status.equals("incorrect")) { %>
        <div class="alert alert-danger">
            Invalid username or password. Please try again.
        </div>
    <% } else if (status != null && status.equals("error")) { %>
        <div class="alert alert-danger">
            An error occurred. Please try again later.
        </div>
    <% } %>
    <form action="Login" method="post" >
        <div class="form-group role">
            <label for="mySelect">Role:</label>
            <select id="mySelect" name="role" >
                <option value="Admin">Admin</option>
                <option value="Student">Student</option>
            </select>
            <span class="error-message"></span>
        </div>
        <div class="form-group name">
            <label for="name">Membership Number :</label>
            <input type="text" id="memberId"  name="memberId" required placeholder="Enter Membership Number">
            <span class="error-message"></span>
        </div>
        <div class="form-group password">
            <label for="pass">Password :</label>
            <input type="password" id="pass" name="password" required placeholder="Enter Password">
            <span class="error-message"></span>
        </div>
        <div class="from-submit-button">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>
    </form>
</div>
</body>
</html>
