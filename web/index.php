<!DOCTYPE html>
<html>
    <head>
        <title>Moniteur</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="style/design.css"/>
		<script type="text/javascript">
			function changeState(pId)
			{
				alert(pId);
			}

			function updateValues()
			{
				
			}

			function changeColor(pId, pColor)
			{
				
			}

			function changeName(pId, pName)
			{
				alert(pId + " et " + pName);
			}

			function changeLuminosity(pId, pLuminosity)
			{
				
			}

			function changeTemperature(pId, pTemperature)
			{
				
			}
		</script>
    </head>
    <body>

	<?php 
		include('inc/soapFunctions.inc.php'); 
		
		$list = getListSensors();
		
		if ($list->{'code'} == 0)
		{
			for ($i = 0; $i < count($list->{'json'}); $i++)
			{
				$sensor = $list->{'json'}[$i];
				$sensorTypeIconURL;
				$realTimeValue;
				$sensorStateURL = "img/ON.png";
				$color;
				$luminosity;
				$temperature;
				
				switch ($sensor->{"Type"})
				{
					case "Prise":
						$sensorTypeIconURL="";
						$realTimeValue = $sensor->{'Info'}[3] . " W";
						break;
					case "Compteur":
						$sensorTypeIconURL="";
						$realTimeValue = $sensor->{'Info'}[2] . " W";
						break;
					case "Ampoule":
						$sensorTypeIconURL="";
						$realTimeValue = $sensor->{'Info'}[5] . " W";
						$color = $sensor->{'Info'}[3];
						$luminosity = $sensor->{'Info'}[4];
						break;
					case "temperature":
						$sensorTypeIconURL="";
						$realTimeValue = $sensor->{'Info'}[4] . " W";
						$temperature = $sensor->{'Info'}[3] . " °C";
						break;
					default:
						break;
				}

				$sensorTypeIconURL = "img/standard.png";
				if ($sensor->{'Info'}[2] == "off")
				{ 
					$sensorStateURL = "img/OFF.png";
				}
				
				echo '<div id="' . $sensor->{"Id"} . '" class="sensor">';
				
				
				echo	'<div class="sensor-picture-type"><img src="' . $sensorTypeIconURL . '" alt="sensor-type"></div>';
				echo 	'<div class="middle-frame">';
				echo		'<div class="sensor-name" contenteditable="true">' . $sensor->{"Name"} . '</div>';
				echo		'<div class="sensor-state"><img src="' . $sensorStateURL . '" alt="sensor-state" onclick="changeState(' . $sensor->{"Id"} . ')"></div>';
				echo	'</div>';
				#echo	'<img class="edit-name-icon" src="" alt="edit-name" style="width:20px;height:20px;">';
				echo 	'<div class="bottom-frame">';
				echo		'<div class="sensor-value">Consommation (temps réel) : ' . $realTimeValue . '</div>';
				echo		'<div class="statistical"><img src="img/stat.png" alt="statistical-icon"></div>';
				echo	'</div>';
				/*
				if ($sensor->{"Type"} == "Ampoule")
				{
					echo	'<p class="sensor-color">Couleur : ' . $color . '</p>';
					echo	'<p class="sensor-luminosity">Luminosité : ' . $luminosity . '</p>';
				}
				else if ($sensor->{"Type"} == "temperature")
				{
					echo	'<p class="sensor-temperature">Température : ' . $temperature . '</p>';
				}
				
				*/
				
				echo '</div>';
			}
			
			echo '<div class="sensor"><img id="add" src="img/add.png" alt="add-sensor"></div>';
		}
		else
		{
			echo '<h1>' . $list->{"message"} . '</h1>';
		}
						
		?>	

    </body>
</html>
