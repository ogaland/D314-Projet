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

			function changeColor(pId)
			{
				
			}

			function changeName(pId, pName)
			{
				document.getElementById("test").innerHtml = "changeName OK : " + pName;
				if (pName.length == 0)
				{
					
				}
				else
				{
					var xmlhttp = new XMLHttpRequest();
					xmlhttp.onreadystatechange = function() {
						if (this.readyState == 4 && this.status == 200)
						{
							var name = document.getElementByName("sensor-name-" +pId).getAttributeNode("value").value;
							document.getElementById("test").innerHtml += "XMLHttpRequest OK : " + name;
						}
					}
				}
			}

			function changeLuminosity(pId)
			{
				
			}

			function changeTemperature(pId)
			{
				
			}
		</script>
    </head>
    <body>

	<?php 
		include('inc/soapFunctions.inc.php'); 
		
		$list = getListSensors();
		
		$colors = array("bleu" => "Bleu", "rouge" => "Rouge", "jaune" => "Jaune", "blanc" => "Blanc", "vert" => "Vert");
		
		if ($list->{'code'} == 0)
		{
			for ($i = 0; $i < count($list->{'json'}); $i++)
			{
				$sensor = $list->{'json'}[$i];
				$sensorTypeIconURL;
				$realTimeValue;
				$sensorStateURL = "img/on.jpg";
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
						$temperature = $sensor->{'Info'}[3];
						break;
					default:
						break;
				}

				$sensorTypeIconURL = "img/standard.png";
				if ($sensor->{'Info'}[2] == "off")
				{ 
					$sensorStateURL = "img/off.jpg";
				}
				
				echo '<div id="' . $sensor->{"Id"} . '" class="sensor">';
				
				
				echo	'<div class="sensor-picture-type"><img src="' . $sensorTypeIconURL . '" alt="sensor-type"></div>';
				echo 	'<div class="middle-frame">';
				echo		'<div class="sensor-name"><form><input type="text" name="sensor-name-' . $sensor->{"Id"} . '" onfocusout="changeName(' . $sensor->{"Id"} . ', this.value)" value="' . $sensor->{"Name"} . '" size=17 max-length=20></form></div>';
				echo		'<div class="sensor-state"><img src="' . $sensorStateURL . '" alt="sensor-state" onclick="changeState(' . $sensor->{"Id"} . ')"></div>';
				echo	'</div>';
				#echo	'<img class="edit-name-icon" src="" alt="edit-name" style="width:20px;height:20px;">';
				echo 	'<div class="bottom-frame">';
				echo		'<div class="sensor-value">Consommation (temps réel) : ' . $realTimeValue . '</div>';
				
				if ($sensor->{"Type"} == "Ampoule")
				{
					#echo	'<div class="sensor-color">Couleur : ' . $color . '</div>';
					#echo	'<div class="sensor-luminosity">Luminosité : ' . $luminosity . '</div>';
					
					echo	'<div class="other-properties">';
					echo		'<form>';
					echo			'<label for="color">Couleur</label>';
					echo			'<select name="color-' . $sensor->{"Id"} . '" id=" . $sensor->{"Id"} . ">';
					
					foreach ($colors as $aColor => $aColorName)
					{
						if ($color == $aColor)
							echo		'<option value="' . $aColor . '" selected>' . $aColorName . '</option>';
						else
							echo		'<option value="' . $aColor . '">' . $aColorName . '</option>';
					}
					echo			'</select>';
					echo			'<br>';
					echo			'<label for="luminosity">Luminosité : </label>';
					echo			'<input type="range" value="' . $luminosity . '" max="5" min="1" step="1" class="slider"/>';
					echo		'</form>';
					echo	'</div>';
					
				}
				else if ($sensor->{"Type"} == "temperature")
				{
					#echo	'<div class="sensor-temperature">Température : ' . $temperature . '</div>';
					
					echo	'<div class="other-properties">';
					echo		'<form>';
					echo			'<label for="temperature">Température : </label>';
					echo			'<input type="number" value="' . $temperature .'" min="0" max="35" step="1" size="2" class="heat"/> °C';
					echo		'</form>';
					echo	'</div>';
				}
				
				echo		'<div class="statistical"><img src="img/stat.png" alt="statistical-icon"></div>';
				echo	'</div>';		
				echo '</div>';
			}
			
			echo '<div class="sensor"><img id="add" src="img/add.png" alt="add-sensor"></div>';
		}
		else
		{
			echo '<h1>' . $list->{"message"} . '</h1>';
		}
		
						
		?>	
		<p id="test">Test</p>

    </body>
</html>
