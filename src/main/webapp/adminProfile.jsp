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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Profile</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
        body {
            background-image: url('<%= request.getContextPath() %>/images/libb2.jpg');
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: fixed;
            color: white;
        }
        .card {
            background-color: rgba(255, 255, 255, 0.7);

        }
        .container h2 {
            text-align: center;
            color:white;
        }
        .card-wrapper {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 1rem;
        }
        .card-custom {
            width: 80%;
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
        .logout {
            position: absolute;
            top: 10px;
            right: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="AdminNavbar.jsp" />
<div class="container mt-5">
    <h2 class="mb-4">Welcome to Admin Profile</h2>
    <div class="row">
        <div class="col-md-6 card-wrapper">
            <div class="card card-custom">
                <div class="card-body">
                    <h5 class="card-title">Add Books</h5>
                    <p class="card-text">Add new books to the library.</p>
                    <a href="addbook.jsp" name="Add" class="btn btn-primary">Add Books</a>
                </div>
            </div>
        </div>
        <div class="col-md-6 card-wrapper">
            <div class="card card-custom">
                <div class="card-body">
                    <h5 class="card-title">View Books</h5>
                    <p class="card-text">View all books in the library.</p>
                    <form action="AdminProfileServlet" method="post">
                        <input type="hidden" id="memberId" name="memberId" value="<%= memberId %>" readonly>
                        <button type="submit" name="click" value="View" class="btn btn-primary">View Books</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-6 card-wrapper">
            <div class="card card-custom">
                <div class="card-body">
                    <h5 class="card-title">Issued Books</h5>
                    <p class="card-text">Issued books by students.</p>
                    <form action="AdminProfileServlet" method="post">
                        <input type="hidden" id="memberId" name="memberId" value="<%= memberId %>" readonly>
                        <button type="submit" name="click" value="Issue" class="btn btn-primary">Issued Books</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-6 card-wrapper">
            <div class="card card-custom">
                <div class="card-body">
                    <h5 class="card-title">Delete Books</h5>
                    <p class="card-text">Delete books from the library.</p>
                    <form action="AdminProfileServlet" method="post">
                        <input type="hidden" id="memberId" name="memberId" value="<%= memberId %>" readonly>
                        <button type="submit" name="click" value="Delete" class="btn btn-primary">Delete Books</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-6 card-wrapper">
            <div class="card card-custom">
                <div class="card-body">
                    <h5 class="card-title">Edit Books</h5>
                    <p class="card-text">Edit book details.</p>
                    <form action="AdminProfileServlet" method="post">
                        <input type="hidden" id="memberId" name="memberId" value="<%= memberId %>" readonly>
                        <button type="submit" name="click" value="Edit" class="btn btn-primary">Edit Books</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
