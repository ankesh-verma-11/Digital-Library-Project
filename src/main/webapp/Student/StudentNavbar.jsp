<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LibraryHub</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
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
</form>
</body>
</html>
