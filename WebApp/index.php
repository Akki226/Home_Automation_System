<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Log in</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://www.gstatic.com/firebasejs/5.5.1/firebase.js"></script>
    <script src="index.js"></script>

    <link rel="icon" href="https://cdn-icons-png.flaticon.com/512/8118/8118759.png" type="image/gif">

    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
    <!-- icheck bootstrap -->
    <!-- Google Font: Source Sans Pro -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">

    <link rel="stylesheet" href="https://adminlte.io/themes/v3/plugins/fontawesome-free/css/all.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="https://adminlte.io/themes/v3/dist/css/adminlte.min.css">
    <!-- Google Font: Source Sans Pro -->
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
</head>

<body class="hold-transition login-page" style="background-color:white">
    <div class="login-box">
        <div class="login-logo">
            <a style="color:white" href="#"><b>Admin</b> Login</a>
        </div>

        <div class="card">

            <div class="card-body login-card-body shadow-lg">

                <div class="text-center m-3">
                    <img src="https://cdn-icons-png.flaticon.com/512/8118/8118759.png" alt="" width="150px" height="150px" style="object-fit:cover; margin-left:auto;">
                </div>

                <p class="login-box-msg">Sign in to access</p>

                <form action="" name="loginForm" id="loginForm" method="post">
                    <div class="input-group mb-3">
                        <input type="text" name="username" id="username" class="form-control" placeholder="Username" autocomplete="off">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-envelope"></span>
                            </div>
                        </div>
                    </div>

                    <div class="input-group mb-3">
                        <input type="password" name="password" id="password" class="form-control" placeholder="Password">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <!-- /.col -->
                        <div class="col-4">
                            <button id="login_btn" type="submit" class="btn btn-primary btn-block">Sign In</button>
                        </div>
                        <!-- /.col -->
                    </div>
                </form>

            </div>
        </div>

</body>

</html>

<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>

<script>
    (function() {
        localStorage.removeItem("login");

        $("#login_btn").on("click", function(e) {
            e.preventDefault();

            var username = $("#username").val();
            var password = $("#password").val();

            if (username != "") {
                if (password != "") {
                    const dbRefObject = firebase.database().ref().child('users').child(username).child("password");
                    dbRefObject.on('value', snap => {
                        var ol_pass = snap.val();
                        console.log(ol_pass);
                        if (ol_pass != null) {
                            if (password == ol_pass) {
                                localStorage.setItem("login", "yes");
                                localStorage.setItem("username", username);
                                window.location.href = "roomsdata.php";
                            } else {
                                localStorage.removeItem("login");
                                toastr.warning('Wrong password', 'Password!');
                            }
                        }
                        else
                        {
                            toastr.error('No such account', 'Account!');
                        }

                    }, function(error) {
                        // The fetch failed.
                        alert("No such account");
                        console.error(error);
                    });
                } else {
                    toastr.error('Enter password', 'Empty!')
                }
            } else {
                toastr.error('Enter username', 'Empty!')
            }

        });
    }());
</script>