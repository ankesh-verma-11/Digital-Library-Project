<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.database.*" %>
<%@ page import="com.librarymanagement.servlet.*" %>
<%

String status = request.getParameter("status");
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
    <title>Registration Page</title>
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
            margin-top: 23px;
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
    <script>

        function toggleAdminFields() {
            var roleSelect = document.getElementById('mySelect');
            var adminFields = document.querySelectorAll('.admin-fields');

            if (roleSelect.value === 'admin') {
                adminFields.forEach(function(field) {
                    field.style.display = 'block';
                });
            } else {
                adminFields.forEach(function(field) {
                    field.style.display = 'none';
                });
            }
        }
 function validatePassword() {
         var errorSpanName = document.getElementById("name-error");

                             var passwordPattern = /^(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$/;
                             var name = document.getElementById("name").value;
                             var email = document.getElementById("email").value;
                             var lib_name = document.getElementById("lib-name").value;
                             var address = document.getElementById("address").value;
                             var emailPattern = /^[a-zA-Z][a-zA-Z0-9@._]*$/;
                             var namePattern = /^[a-zA-Z][a-zA-Z\s]*$/;
                             var addressPattern = /^[a-zA-Z0-9\s,.-]+$/;
                             if (!namePattern.test(name)) {
                                alert("Invalid name. It should start with a letter and can contain only letters and spaces.");
                                 return false;
                             }
                             if (!emailPattern.test(email)) {
                                  alert("Invalid email. It should start with a letter and can contain letters, digits, and the special characters @, ., and _.");
                                  return false;
                             }
                             var roleSelect = document.getElementById('mySelect');
                             var adminFields = document.querySelectorAll('.admin-fields');
                              if (roleSelect.value === 'admin') {
                                 if (!namePattern.test(lib_name)) {
                                     alert("Invalid Library name. It should start with a letter and can contain only letters and spaces.");
                                     return false;
                                 }
                                 if (!addressPattern.test(address)) {
                                      alert("Invalid address. It should start with a letter and can contain letters, numbers, spaces, commas, periods, and hyphens.");
                                      return false;
                                 }
                              }

        var password = document.getElementById("pass").value;
        var confirmPassword = document.getElementById("confirm-pass").value;
        var errorSpan = document.getElementById("password-error");
        var passwordPattern = /^(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$/;

        if (!passwordPattern.test(password)) {
            errorSpan.textContent = "Password must be at least 8 characters long and contain a special symbol (!@#$%^&*)";
            return false;
        } else if (password !== confirmPassword) {
            errorSpan.textContent = "Passwords do not match";
            return false;
        } else {
            errorSpan.textContent = "";
            return true;
        }
    }
        document.addEventListener('DOMContentLoaded', function() {
            var roleSelect = document.getElementById('mySelect');
            roleSelect.addEventListener('change', toggleAdminFields);
            toggleAdminFields(); // Initial call to set the correct state on page load
            var form = document.querySelector('form');
            form.addEventListener('submit', function(event) {
                if (!validatePassword()) {
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
        <h1>Digital Library</h1>
    </div>
    <div>
        <a href="Registration.jsp">Register</a>
        <a href="Login.jsp">Login</a>
    </div>
</div>
<div class="container">
    <h4>Register here</h4>
    <form action="Register" method="post">
        <% if (status != null) {  %>
                    <div class="alert alert-<%= status.equals("success") ? "success" : "error" %>">
                        <% if (status.equals("success")) { %>
                            <div>Congratulations! You have successfully registered.</div>
                            <script>
                                setTimeout(function() {
                                window.location.href = "Login.jsp";
                                }, 2000);
                            </script>
                        <% } else if (status.equals("error")) { %>
                            <div>Some error occurred during registration. Please try again later.</div>
                        <% } else if (status.equals("exists")) { %>
                            <div>An account with this email already exists.</div>
                        <% }  else if (status.equals("notExistsDomain")) { %>
                            <div>The domain part of the email does not exist. Please use a valid email address.</div>
                        <% } %>
                    </div>
        <% } %>

        <div class="form-group name">
            <label for="name">Name :</label>
            <input type="text" id="name" name="name" placeholder="Enter name" required>
             <span id="name-error" class="error-message"></span>
        </div>
        <div class="form-group email">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="Enter Email" required>
             <span id="email-error" class="error-message"></span>
        </div>
        <div class="form-group role">
            <label for="mySelect">Role:</label>
            <select id="mySelect" name="role">
                <option value="admin">Admin</option>
                <option value="student">Student</option>
            </select>
        </div>
        <div class="form-group admin-fields">
            <label for="lib-name">Library Name:</label>
            <input type="text" id="lib-name" name="lib_name" placeholder="Enter Library Name">
            <span id="lib-name-error" class="error-message"></span>
        </div>
        <div class="form-group admin-fields">
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" placeholder="Enter Address">
            <span id="address-error" class="error-message"></span>
        </div>
        <div class="form-group password">
            <label for="pass">Password:</label>
            <input type="password" id="pass" name="pass" placeholder="Enter Password" required>
        </div>
        <div class="form-group password">
            <label for="confirm-pass">Confirm Password:</label>
            <input type="password" id="confirm-pass" name="con_pass" placeholder="Enter Confirm Password" required>
            <span id="password-error" class="error-message"></span>
        </div>
        <div class="form-submit-button">
            <button type="submit" class="btn btn-primary">Register</button>
        </div>
    </form>
</div>
</body>
</html>
