
<?php

$room_no = $_REQUEST['room'];
$data =  array($_REQUEST['data']);

$rooms_data = $data[0]['rooms'][$room_no]['devices'];

// -- for rooms

$returner['rooms'] = $data[0]['rooms'];

$returner['roomsdata'] = "<div class='card col-12 col-md-6 p-0 shadow-lg rounded'>
<div class='card-header ' style='background-color:blue;'>
    <div class='card-title text-light'>
        Rooms
    </div>
</div>
<div class='card-body'>
    <table class='table' id='rooms_tb'>";
foreach ($data[0]['rooms'] as $key => $room) {
    if ($room != null) {
        $disp = $key == $room_no ? "hidden" : "";
        $color = $key == $room_no ? "green" : "";
        $returner['roomsdata'] .=  "<tr><td class='text-bold' style='color:{$color}'>Room {$key}</td><td style='visibility:{$disp};'><a href='roomsdata.php?room={$key}'>Show</a></td></tr>\n";
    }
}
$returner['roomsdata'] .= "</table>
</div>
</div>";

// for devices

foreach ($data[0] as $rooms_data['devices']) {

    // -- for heaters

    $returner['data'] = "<div class='card col-12 col-md-6 p-0 shadow-lg rounded'>
<div class='card-header ' style='background-color:darkcyan;'>
    <div class='card-title text-light'>
        Heaters
    </div>
   

</div>
<div class='card-body'>
    <table class='table' id='rooms_tb'>";
    foreach ($rooms_data['heaters'] as $key => $row) {
        if ($row != null) {
            $stat = $row['status'] == 1 ? 'heater.png' : 'heater_off.png';
            $color = $row['status'] == 1 ? "green" : "red";
            
            $returner['data'] .=  "<tr class='text-bold'><td>Heater {$key} </td><td style='color:{$color}'><img src='$stat' width='30px' height='30px'></td></tr>\n";
        }
        
    }
    $returner['data'] .= "</table>
</div>
</div>";

    // -- for lights

    $returner['data'] .= "<div class='card col-12 col-md-6 m-2 p-0 shadow-lg rounded'>
<div class='card-header ' style='background-color:darkcyan;'>
    <div class='card-title text-light'>
        Lights
    </div>
</div>
<div class='card-body'>
    <table class='table' id='rooms_tb'>";
    foreach ($rooms_data['lights'] as $key => $row) {
        if ($row != null) {
            $stat = $row['status'] == 1 ? 'lights_ico.png' : "light_off_ico.png";
            $color = $row['status'] == 1 ? "green" : "red";
            $returner['data'] .=  "<tr class='text-bold'><td>Light {$key} </td><td style='color:{$color}'><img src='{$stat}'width='30px' height='30px'></td></tr>\n";
        }
    }
    $returner['data'] .= "</table>
</div>
</div>";

    // -- for maindoors

    $returner['data'] .= "<div class='card col-12 col-md-6 m-2 p-0 shadow-lg rounded'>
<div class='card-header ' style='background-color:darkcyan;'>
    <div class='card-title text-light'>
        Maindoor
    </div>
</div>
<div class='card-body'>
    <table class='table' id='rooms_tb'>";
    $stat = $rooms_data['maindoor'] == 1 ? "unlocked.png" : "locked.png";
    $color_door = $rooms_data['maindoor'] == 1 ? "green" : "red";
    $returner['data'] .=  "<tr class='text-bold'><td>Maindoor </td><td style='color:{$color_door}'><img src='{$stat}'width='30px' height='30px'></td></tr>\n";
    $returner['data'] .= "</table>
</div>
</div>";
}






print_r(json_encode($returner));?>

