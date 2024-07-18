<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.servlet.RegistrationServlet" %>
<%
String memberId = (String) session.getAttribute("memberId");
if (memberId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
}
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
    <title>Student Profile</title>
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
        .container {
            width: 100%;
            margin: auto;
            padding: 0px;
            margin-top: 100px;
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
        .card-wrapper {
            padding: 40px;
            margin-left: 30px;
            width: 30%;
            margin-bottom: 20px;
        }
        .card-custom {
            background-color: rgba(255, 255, 255, 0.8);
            border: none;
            border-radius: 10px;
            padding: 10px;
        }
        .btn-primary {
            transition: background-color 0.3s, color 0.3s;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            color: white;
        }
        @media (max-width: 768px) {
            .card-wrapper {
                width: 100%;
                margin-left: 0;
            }
            .navbar button {
                padding: 8px 10px;
            }
        }
    </style>
</head>
<body>
<form action="StudentServlet" method="post">
    <div class="navbar">
        <div class="navbar-left">
            <img src="images/Book.jpg" alt="Logo" />
            <h1>LibraryHub</h1>
        </div>
        <div>
            <button  type="submit" name="click" value="Home" >Home</button>
            <button type="submit" name="click" value="View" >View Books</button>
            <button type="submit" name="click" value="Issue" >Issue Books</button>
            <button type="submit" name="click" value="Issued" >Issued Books</button>
            <button type="submit" name="click" value="Renew" >Renew Book</button>
            <button type="submit" name="click" value="Return" >Return Book</button>
            <button type="submit" name="click" value="Logout" class="btn btn-danger">Logout</button>
        </div>
    </div>
     <div class="container mt-5">
            <div class="row">
                <div class="col-md-6 card-wrapper">
                    <div class="card card-custom">
                        <div class="card-body">
                            <h5 class="card-title">View Books</h5>
                            <p class="card-text">View all books present in the library.</p>
                            <button type="submit" name="click" value="View" class="btn btn-primary">View Books</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 card-wrapper">
                    <div class="card card-custom">
                        <div class="card-body">
                            <h5 class="card-title">Issue Books</h5>
                            <p class="card-text">Issue books from the library.</p>
                            <input type="hidden" id="memberId" name="memberId" value="<%= memberId %>">
                            <button type="submit" name="click" value="Issue" class="btn btn-primary">Issue Books</button>
                         </div>
                    </div>
                </div>
                <div class="col-md-6 card-wrapper">
                    <div class="card card-custom">
                        <div class="card-body">
                            <h5 class="card-title">Issued Books</h5>
                            <p class="card-text">All issued books.</p>
                            <input type="hidden" id="memberId" name="memberId" value="<%= memberId %>">
                            <button type="submit" name="click" value="Issued" class="btn btn-primary">Issued Books</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 card-wrapper">
                    <div class="card card-custom">
                        <div class="card-body">
                            <h5 class="card-title">Renew Books</h5>
                            <p class="card-text">Renew books from the library.</p>
                            <input type="hidden" id="memberId" name="memberId" value=" <%= memberId %>">
                           <button type="submit" name="click" value="Renew" class="btn btn-primary">Renew Books</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 card-wrapper">
                    <div class="card card-custom">
                        <div class="card-body">
                            <h5 class="card-title">Return Books</h5>
                            <p class="card-text">Return book to the library.</p>
                            <input type="hidden" id="memberId" name="memberId" value="<%= memberId %>">
                           <button type="submit" name="click" value="Return" class="btn btn-primary">Return Books</button>
                        </div>
                    </div>
                </div>

            </div>
    </div>
</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
