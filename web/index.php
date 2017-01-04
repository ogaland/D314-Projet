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
				var query = "changeState";
				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200)
					{
						if (this.responseText == 0)
						{
							if(document.getElementById("sensor-state-" + pId).src.includes("img/off.icon.png"))
							{
								document.getElementById("sensor-state-" + pId).src="img/on.icon.png";
							}
							else
							{
								document.getElementById("sensor-state-" + pId).src="img/off.icon.png";
							}
						}
					}
				};
				xmlhttp.open("GET", "inc/ajax.inc.php?query=" + query + "&id=" + pId, true);
				xmlhttp.send();
			}
			
			function realTimeValues(pTime)
			{
				var nInterval = setInterval(updateValues, pTime);
			}

			function updateValues()
			{
				var query = "updateValues";
				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200)
					{
						var obj = JSON.parse(this.responseText);
						
						for (i = 0; i < obj.nbSensor; i++)
						{
							document.getElementById("sensor-value-" + (i+1)).innerHTML = obj.sensors[i].Value;
						}
					}
				};
				xmlhttp.open("GET", "inc/ajax.inc.php?query=" + query, true);
				xmlhttp.send();
			}

			function changeColor(pId, pColor)
			{
				var query = "changeColor";
				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200)
					{
						if (this.responseText == 0)
						{
							document.getElementById("color-" + pId).value=pColor;
						}
					}
					
				};
				xmlhttp.open("GET", "inc/ajax.inc.php?query=" + query + "&id=" + pId + "&color=" + pColor, true);
				xmlhttp.send();
			}

			function changeName(pId, pName)
			{
				var query = "changeName";
				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200)
					{
						if (this.responseText == 0)
						{
							document.getElementById("sensor-name-" + pId).value=pName;
						}
					}
					
				};
				xmlhttp.open("GET", "inc/ajax.inc.php?query=" + query + "&id=" + pId + "&name=" + pName, true);
				xmlhttp.send();
			}

			function changeLuminosity(pId, pLuminosity)
			{
				var query = "changeLuminosity";
				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200)
					{
						if (this.responseText == 0)
						{
							document.getElementById("choose-luminosity-" + pId).value=pLuminosity;
						}
					}
				};
				xmlhttp.open("GET", "inc/ajax.inc.php?query=" + query + "&id=" + pId + "&luminosity=" + pLuminosity, true);
				xmlhttp.send();
			}

			function changeTemperature(pId, pTemperature)
			{
				var query = "changeTemperature";
				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200)
					{
						if (this.responseText == 0)
						{
							document.getElementById("choose-temperature-" + pId).value=pLuminosity;
						}
					}
				};
				xmlhttp.open("GET", "inc/ajax.inc.php?query=" + query + "&id=" + pId + "&temperature=" + pTemperature, true);
				xmlhttp.send();
			}
			
			function openStats(pId)
			{
				window.open("graph.php?id="+pId,'popUpGraph','width=600, height=600');
				return false;
			}
		</script>
    </head>
    <body onpageshow="realTimeValues(5000)">

	<?php 
		include('inc/soapFunctions.php'); 
		
		$list = getListSensors();
		
		$colors = array("bleu" => "Bleu", "rouge" => "Rouge", "jaune" => "Jaune", "blanc" => "Blanc", "vert" => "Vert");
		
		if ($list->{'code'} == 0)
		{
			for ($i = 0; $i < count($list->{'json'}); $i++)
			{
				$sensor = $list->{'json'}[$i];
				$sensorTypeIconURL;
				$sensorStateURL = "img/on.icon.png";
				$color;
				$luminosity;
				$temperature;
				
				switch ($sensor->{"Type"})
				{
					case "Prise":
						$sensorTypeIconURL="img/socket.png";
						break;
					case "Compteur":
						$sensorTypeIconURL="img/flash.png";
						break;
					case "Ampoule":
						$sensorTypeIconURL="img/light.png";
						$color = $sensor->{'Info'}[3];
						$luminosity = $sensor->{'Info'}[4];
						break;
					case "Temperature":
						$sensorTypeIconURL="img/thermostat.png";
						$temperature = $sensor->{'Info'}[3];
						break;
					default:
						$sensorTypeIconURL = "img/standard.png";
						break;
				}

				
				if ($sensor->{'Info'}[2] == "off")
				{ 
					$sensorStateURL = "img/off.icon.png";
				}
				
				echo '<div id="' . $sensor->{"Id"} . '" class="sensor">';
				
				
				echo	'<div class="sensor-picture-type"><img src="' . $sensorTypeIconURL . '" alt="sensor-type"></div>';
				echo 	'<div class="middle-frame">';
				echo		'<div class="sensor-name"><form><input type="text" id="sensor-name-' . $sensor->{"Id"} . '" name="sensor-name-' . $sensor->{"Id"} . '" onchange="changeName(' . $sensor->{"Id"} . ', this.value)" value="' . $sensor->{"Name"} . '" size=17 max-length=20></form></div>';
				echo		'<div class="sensor-state"><img id="sensor-state-' . $sensor->{"Id"} . '" src="' . $sensorStateURL . '" alt="sensor-state" onclick="changeState(' . $sensor->{"Id"} . ')"></div>';
				echo	'</div>';
				echo 	'<div class="bottom-frame">';
				echo		'<div class="sensor-value">Consommation (temps réel) : <span id="sensor-value-' . $sensor->{"Id"} . '"></span></div>';
				
				if ($sensor->{"Type"} == "Ampoule")
				{					
					echo	'<div class="other-properties">';
					echo		'<form>';
					echo			'<label class="label-color" for="color">Couleur : </label>';
					echo			'<select oninput="changeColor(' . $sensor->{"Id"} . ', this.value)" class="choose-color" name="color-' . $sensor->{"Id"} . '" id="' . $sensor->{"Id"} . '">';
					
					foreach ($colors as $aColor => $aColorName)
					{
						if ($color == $aColor)
							echo		'<option value="' . $aColor . '" selected>' . $aColorName . '</option>';
						else
							echo		'<option value="' . $aColor . '">' . $aColorName . '</option>';
					}
					echo			'</select>';
					echo			'<br>';
					echo			'<label class="label-luminosity" for="luminosity">Luminosité : </label>';
					echo			'<input id="choose-luminosity-' . $sensor->{"Id"} . '" onchange="changeLuminosity(' . $sensor->{"Id"} . ', this.value)" class="choose-luminosity" type="range" value="' . $luminosity . '" max="5" min="1" step="1" />';
					echo		'</form>';
					echo	'</div>';
					
				}
				else if ($sensor->{"Type"} == "Temperature")
				{					
					echo	'<div class="other-properties">';
					echo		'<form>';
					echo			'<label class="label-temperature" for="temperature">Température (°C) : </label>';
					echo			'<input id="choose-temperature-' . $sensor->{"Id"} . '" onchange="changeTemperature(' . $sensor->{"Id"} . ', this.value)" class="choose-temperature" type="number" value="' . $temperature .'" min="0" max="35" step="1" size="2" />';
					echo		'</form>';
					echo	'</div>';
				}
				
				echo		'<div class="statistical"><img id="statistics-' . $sensor->{"Id"} . '" src="img/bars-chart.png" alt="statistical-icon" onclick="openStats(' . $sensor->{"Id"} . ')"></div>';
				echo	'</div>';		
				echo '</div>';
			}
			
			echo '<div class="add-sensor"><img id="add" src="img/add.png" alt="add-sensor"></div>';
		}
		else
		{
			echo '<h1>' . $list->{"message"} . '</h1>';
		}
		
						
		?>	
    </body>
</html>
