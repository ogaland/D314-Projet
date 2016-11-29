<html>
	<head>
		<link rel="stylesheet" type="text/css" href="box-css.css">
	</head>
	<body>

	<?php 
		include('soapFunctions.inc.php'); 
		include('sensors.inc.php');
		
		$list = getListSensors();
		
		if ($list->{'code'} == 0)
		{
			for ($i = 0; $i < count($list->{'json'}); $i++)
			{
				$sensor = $list->{'json'}[$i];
				$sensorTypeIconURL;
				$realTimeValue;
				$sensorStateURL = "";
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

				echo '<div id="' . $sensor->{"Id"} . '" class="card card-1">';
				
				
				echo	'<img class="sensor-type-icon" src="' . $sensorTypeIconURL . '" alt="sensor-type" style="width:100px;height:100px;" >';
				echo	'<p class="name" contenteditable="true">' . $sensor->{"Name"} . '</p>';
				echo	'<img class="edit-name-icon" src="" alt="edit-name" style="width:20px;height:20px;">';
				echo	'<p class="sensor-value">Consommation (temps réel) : ' . $realTimeValue . '</p>';
				
				if ($sensor->{"Type"} == "Ampoule")
				{
					echo	'<p class="sensor-color">Couleur : ' . $color . '</p>';
					echo	'<p class="sensor-luminosity">Luminosité : ' . $luminosity . '</p>';
				}
				else if ($sensor->{"Type"} == "temperature")
				{
					echo	'<p class="sensor-temperature">Température : ' . $temperature . '</p>';
				}
				
				echo	'<img class="sensor-state" src="' . $sensorStateURL . '" alt="sensor-state" style="width:20px;height:20px;">';
				
				echo '</div>';
			}
		}
		else
		{
			echo '<h1>' . $list->{"message"} . '</h1>';
		}
						
		?>

	</body>
</html>