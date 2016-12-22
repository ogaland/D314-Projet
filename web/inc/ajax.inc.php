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
		case "UpdateValues":
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
			break;
		default:
			break;
	}
}

?>