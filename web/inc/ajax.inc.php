<?php
## Fichier contenant les fonctions AJAX

include('soapFunctions.php'); 

$query = $_GET["query"];

if($query != "")
{
	switch ($query)
	{
		case "changeState":
			if ($_GET["id"] != "")
			{
				print_r(switchPower($_GET["id"])->{"code"});
			}
			break;
		case "changeName":
			if ($_GET["id"] != "" && $_GET["name"] != "")
			{
				print_r(changeName($_GET["id"], $_GET["name"])->{"code"});
			}
			break;
		case "updateValues":		
			$list = getListSensors();
			
			$return = '{"nbSensor":"' . count($list->{'json'}) . '"';
				
			if ($list->{'code'} == 0 && count($list->{'json'}) > 0)
			{
				$return .= ', "sensors":[';
				for ($i = 0; $i < count($list->{'json'}); $i++)
				{
					$sensor = $list->{'json'}[$i];
					
					$return .= '{"Id":"' . $sensor->{'Id'} . '", "Value":"';
					
					switch ($sensor->{"Type"})
					{
						case "Prise":
							$return .= $sensor->{'Info'}[3] . ' W"';
							break;
						case "Compteur":
							$return .= $sensor->{'Info'}[2] . ' W"';
							break;
						case "Ampoule":
							$return .= $sensor->{'Info'}[5] . ' W"';
							break;
						case "Temperature":
							$return .= $sensor->{'Info'}[4] . ' W"';
							break;
						default:
							break;
					}
					
					if ($i+1 == count($list->{'json'}))
					{
						$return .= '}';
					}
					else
					{
						$return .= '},';
					}
					
				}
				$return .= ']';
			}
			
			$return .= '}';
			print_r($return);
			break;
		case "changeColor":
			if ($_GET["id"] != "" && $_GET["color"] != "")
			{
				print_r(changeColor($_GET["id"], $_GET["color"])->{"code"});
			}
			break;
		case "changeLuminosity":
			if ($_GET["id"] != "" && $_GET["luminosity"] != "")
			{
				print_r(changeLuminosity($_GET["id"], $_GET["luminosity"])->{"code"});
			}
			break;
		case "changeTemperature":
			if ($_GET["id"] != "" && $_GET["temperature"] != "")
			{
				print_r(changeTemperature($_GET["id"], $_GET["temperature"])->{"code"});
			}
			break;
		case "getStats":
			if ($_GET["id"] != "")
			{
				$list = getStats($_GET["id"]);
				
				$return = '{"nbValues":"' . count($list->{'json'}) . '"';
				
				if ($list->{'code'} == 0 && count($list->{'json'}) > 0)
				{
					$return .= ', "values":[';
					
					for ($i = 0; $i < count($list->{'json'}); $i++)
					{						
						$return .= '"' . $list->{'json'}[$i] . '"';
												
						if ($i+1 == count($list->{'json'}))
						{
							$return .= '';
						}
						else
						{
							$return .= ',';
						}
						
					}
					$return .= ']';
				}
			}
			$return .= '}';
			print_r($return);
			break;
		default:
			break;
	}
}

?>