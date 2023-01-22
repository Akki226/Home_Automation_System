<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://www.gstatic.com/firebasejs/5.5.1/firebase.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
    <script src="index.js"></script>
    <link rel="stylesheet" href="https://adminlte.io/themes/v3/dist/css/adminlte.min.css">
    <link rel="stylesheet" href="https://adminlte.io/themes/v3/plugins/fontawesome-free/css/all.min.css">
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <title>Automation</title>
</head>

<body class="p-3">
<div id="back" class="row">
    <a href='roomsdata.php'><-Back</a>
    </div>



    <div class="text-right m-3" id="logout_div">
        <img src="https://cdn-icons-png.flaticon.com/512/2529/2529508.png" alt="" width="30px" height="30px" style="object-fit:cover; margin-left:auto;">
        <p style="font-size: 10px;">Logout</p>
        <span id="years">00</span> <span id="months">00</span> <span id="days">00</span> <span id="hrs">00</span>:<span id="min">00</span>:<span id="sec">00</span>
    </div>

    <div class="text-center m-3">
        <img src="https://cdn-icons-png.flaticon.com/512/8118/8118759.png" alt="" width="150px" height="150px" style="object-fit:cover; margin-left:auto;">
    </div>

    <div class='text-center' id='prog_1'>
        <img src='https://acegif.com/wp-content/uploads/loading-25.gif' alt='' width='50px' height='50px' style='object-fit:cover;'>
    </div>



    <div id="room_body" class="row" width='2500px' style="justify-content: center"></div>
    <div id="in_body" class="row" style="justify-content: center;"></div>
    



</body>

</html>

<script>
    (function() {

        const preObject = document.getElementById('data_txt');
        const dbRefObject = firebase.database().ref().child('users').child(localStorage.getItem("username"));

        dbRefObject.on('value', snap => {

            var data = snap.val();
            // preObject.innerText = data;

            $.ajax({
                url: "process1.php",
                type: "POST",
                data: {
                    data: data,
                    room: <?php echo isset($_REQUEST['room']) ? $_REQUEST['room'] : 1; ?>
                },
                success: function(data) {
                    

                    var obj = JSON.parse(data);
                

                    $('#in_body').html(obj['data']);
                    $('#room_body').html(obj['roomsdata']);
                    document.getElementById('prog_1').style.display = 'none';
                    console.log("Hulk: " + obj['data']);
                }
            });

        }, function(error) {
            // The fetch failed.
            alert("No such room. Go Back!!");
            console.error(error);
        });

        var login = localStorage.getItem("login");
        if (login == null) {
            window.location.href = "index.php";
        }

        $("#logout_div").on("click", function(e) {
            localStorage.removeItem("login");
            window.location.href = "index.php";
        });
    
    
    const dbRefDatetime =  firebase.database().ref().child('users').child(localStorage.getItem("username")).child("Date_time");
    dbRefDatetime.on('value', snap => {
        initDatetime=snap.val();
        localStorage.setItem("Datetime",initDatetime);
    });  
   
    
    setInterval(() => {
    const d=new Date();
    var hour =d.getHours();
    var minute=d.getMinutes();
    var second=d.getSeconds();
    var date=d.getDate();
    var mon=d.getMonth();
    var yea=d.getFullYear();


   

    var secLab=document.getElementById('sec');
    var minLab=document.getElementById('min');
    var hrsLab=document.getElementById('hrs');
    var dayLab=document.getElementById('days');
    var monthsLab=document.getElementById('months');
    var yearsLab=document.getElementById('years');
    
   
    
    var time=date+"/"+(mon+1)+"/"+yea+" "+hour+":"+minute+":"+second;
    
    var e = new Date();
    var b = new Date(localStorage.getItem("Datetime"));                                            
    if(b=="Invalid Date")
    {
        var bMonth = 9;
        var bYear = 2022;
        var bDay = 14;
        var bHour= 13;
        var bMinute= 25;
        var bSecond= 11; 
    }
    else
    {
        
        var bMonth = b.getMonth();
        var bYear = b.getFullYear();
        var bDay = b.getDate();
        var bHour= b.getHours();
        var bMinute= b.getMinutes();
        var bSecond= b.getSeconds(); 
    }                                          
    
    var eYear = e.getFullYear();
    var eMonth = e.getMonth()+1;
    
    var eDay = e.getDate();
    
    var eHour= e.getHours();
    
    var eMinute= e.getMinutes();
    
    var eSecond= e.getSeconds();
    
    if (eSecond - bSecond < 0)
    {

     eMinute = eMinute - 1;
     eSecond = eSecond + 60;

    }
    var secondsDiff = eSecond - bSecond;
    
    if(eMinute - bMinute < 0)
    {
        eHour = eHour - 1;
        eMinute = eMinute + 60;
    }

    var minuteDiff = eMinute - bMinute;
    
    if(eHour - bHour < 0)
    {
        eDay = eDay - 1;
        eHour = eHour + 24;
    }
    var hourDiff = eHour - bHour;

   /* if (daysDiff == eDays)
    {
        daysDiff = 0;
        monthDiff = monthDiff + 1;

        if (monthDiff == 12)
        {
            monthDiff = 0;
            yearDiff = yearDiff + 1;
        }

    }*/

    if ((eMonth == 0)||(eMonth == 2)||(eMonth == 4)|| (eMonth == 6) || (eMonth == 7) ||(eMonth == 9)||(eMonth == 11))

    {
        var eDays =  31;
    }

    if ((eMonth == 3)||(eMonth == 5)||(eMonth == 8)|| (eMonth == 10))

    {
        var eDays = 30;
    }

    if (eMonth == 1&&((eYear % 4 == 0) && (eYear % 100 != 0)) || (eYear % 400 == 0))
    {
        var eDays = 29;
    }

    if (eMonth == 1&&((eYear % 4 != 0) || (eYear % 100 == 0)))
    {
        var eDays = 28;
    }


    if ((bMonth == 0)||(bMonth == 2)||(bMonth == 4)|| (bMonth == 6) || (bMonth == 7) ||(bMonth == 9)||(bMonth == 11))

    {
        var bDays =  31;
    }

    if ((bMonth == 3)||(bMonth == 5)||(bMonth == 8)|| (bMonth == 10))

    {
        var bDays = 30;
    }

    if (bMonth == 1&&((bYear % 4 == 0) && (bYear % 100 != 0)) || (bYear % 400 == 0))
    {
        var bDays = 29;
    }

    if (bMonth == 1&&((bYear % 4 != 0) || (bYear % 100 == 0)))
    {
        var bDays = 28;
    }


    var FirstMonthDiff = bDays - bDay + 1;


    if (eDay - bDay < 0)
    {

    eMonth = eMonth - 1;
    eDay = eDay + eDays;

    }

    var daysDiff = eDay - bDay;

    if(eMonth - bMonth < 0)
    {
        eYear = eYear - 1;
        eMonth = eMonth + 12;
    }

    var monthDiff = eMonth - bMonth;

    var yearDiff = eYear - bYear;

    if (daysDiff == eDays)
    {
        daysDiff = 0;
        monthDiff = monthDiff + 1;
        

        if (monthDiff == 12)
        {
            monthDiff = 0;
            yearDiff = yearDiff + 1;
        }
    }



    if ((FirstMonthDiff != bDays)&&(eDay - 1 == eDays))

    {
        daysDiff = FirstMonthDiff;

    }

secLab.innerHTML=secondsDiff;
minLab.innerHTML=minuteDiff;
hrsLab.innerHTML=hourDiff;
dayLab.innerHTML='days:'+daysDiff;
monthsLab.innerHTML='months:'+monthDiff;
yearsLab.innerHTML='years:'+yearDiff;

},999);
    /*var sdf
            = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");
    var d1 = sdf.parse(localStorage.getItem("Datetime"));
    var d2 = sdf.parse(time);
    var difference_In_Time
                = d2.getTime() - d1.getTime();
        alert(difference_In_Time);  

    setInterval(() => {
             
    },999);*/

    }());
</script>