<!DOCTYPE html>
<html>
<head>
    <title>Start Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .navbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: #eadcdc;
            padding: 10px 20px;
        }
        .navbar-left {
            display: flex;
            align-items: center;
        }
        .navbar h1 {
            color: rgb(9, 9, 9);
            margin: 0;
            font-weight: bold;
        }
        .navbar a {
            color: rgb(12, 12, 12);
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
            border-radius: 90px;
        }
        .background {
            position: relative;
            width: 100%;
            height: 90vh;
            background-image: url('images/library.jpg');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
        }
        .centered-text {
            position: absolute;
            top: 33%;
            left: 50%;
            transform: translate(-50%, -50%);
            text-align: center;
            color: rgb(1, 1, 1);
            font-size: 25px;
            font-weight: bold;
            font-family:  'Roboto', sans-serif;
        }
        @media (max-width: 768px) {
            .navbar {
                flex-direction: column;
                align-items: flex-start;
            }
            .navbar h1 {
                margin-bottom: 10px;
            }
            .navbar a {
                padding: 10px;
            }
            .background {
                height: 70vh;
            }
            .centered-text {
                font-size: 24px;
            }
        }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="navbar-left">
            <img src="images/Book.jpg" alt="Logo" />
            <h1>Digital Library</h1>
        </div>
        <div>
            <a href="Registration.jsp">REGISTER</a>
            <a href="Login.jsp">LOGIN</a>
        </div>
    </div>
    <div class="background">
        <div class="centered-text">
            <h1>WELCOME TO <br>DIGITAL LIBRARY</h1>
            <!-- <H1>DIGITAL LIBRARY</H1> -->
        </div>
    </div>
</body>
</html>
