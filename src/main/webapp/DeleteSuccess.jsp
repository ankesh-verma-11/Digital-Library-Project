<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.librarymanagement.servlet.*" %>

<%
String memberId = (String) session.getAttribute("memberId");
if (memberId == null) {
    response.sendRedirect(request.getContextPath() + "/Login.jsp");
    return;
}
String status = request.getParameter("status"); // Change here
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deleted Book</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
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
</head>
<body>
<% if (status == null) { %>
           <%@ include file="StudentNavbar.jsp" %>
       <% } %>
    <div class="container">

        <form action="DeleteBookServlet" method="POST">
         <% if (status != null) { %>
             <h2>Delete Book Status</h2>
                        <div class="alert alert-<%= status.equals("success") ? "success" : "danger" %>">
                            <% if (status.equals("success")) { %>
                                <div>Congratulations! The book has been successfully deleted.</div>
                                <script>
                                    setTimeout(function() {
                                        window.location.href = "deleteBooks.jsp";
                                    }, 1500); // Wait for 1.5 seconds before redirecting
                                </script>
                            <% } else if (status.equals("decreased")) { %>
                                <div>Note: This book cannot be deleted as it has been issued to students. However, the quantity of available books in the library has been decreased. Please ensure all issued copies are returned before attempting to delete the book.</div>
                                <script>
                                    setTimeout(function() {
                                        window.location.href = "deleteBooks.jsp";
                                    }, 1500); // Wait for 1.5 seconds before redirecting
                                </script>
                            <% } else if (status.equals("error")) { %>
                                <div>Either the book is not present in your library or an error occurred while deleting the book. Please try again.</div>
                                <script>
                                    setTimeout(function() {
                                        window.location.href = "deleteBooks.jsp";
                                    }, 1500); // Wait for 1.5 seconds before redirecting
                                </script>
                            <% } %>
                        </div>
                    <% } %>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
