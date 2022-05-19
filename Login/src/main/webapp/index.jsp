<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Boodskap</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <style>
        .container{

            width: 400px;
            height: 500px;
            background-color: white;
            text-align: center;

            margin-left: 400px;
            margin-top: 70px;

            font-size: larger;

            font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
            
        }
    </style>
</head>
<body style="background-color: aqua;">



    <div class="container">
        <br>

<img src="image/boodskap.png" alt="" height="100px" width="100px">
<br><br>
    <form action="" class="form-group">
        <label for="">UserName</label><br>
        <input type="text" class="form-control" placeholder="UserName" required><br>

        <label for="">Password</label><br>
        <input type="text" class="form-control" placeholder="Password" required><br>
        
        <button type="submit" class="form-control btn btn-primary" >Submit</button>
    </form>

    </div>
    
</body>
</html>