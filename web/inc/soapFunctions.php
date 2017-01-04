<?php
## Fichier contenant les fonctions WSDL du WebService

# Désactivation du stockage du fichier WSDL en cache
ini_set("soap.wsdl_cache_enabled", 0);

# Instanciation du client SOAP à partir du fichier WSDL 
# Bien vérifier que la ligne extension=php_soap.dll du fichier php.ini de votre serveur web soit décommentée
$soapClient = new SoapClient("http://localhost:8080/D314-Projet/WebService?wsdl", array('compression'=> SOAP_COMPRESSION_ACCEPT | SOAP_COMPRESSION_GZIP));

# Récupération de la liste des capteurs
function getListSensors()
{
	global $soapClient;
	$result = $soapClient->getListSensors();
	return json_decode($result->{'return'});
}

# Récupération des informations du capteur(id)
function getInfoSensor($pId)
{
	global $soapClient;
	$result = $soapClient->getInfoSensor(array("id"=>$pId));
	return json_decode($result->{'return'});
}

# Change l'état du capteur(id) On/Off
function switchPower($pId)
{
	global $soapClient;
	$result = $soapClient->switchPower(array("id"=>$pId));
	return json_decode($result->{'return'});
}

# Changement du nom du capteur(id)
function changeName($pId, $pName)
{
	global $soapClient;
	$result = $soapClient->changeName(array("id"=>$pId, "name"=>$pName));
	return json_decode($result->{'return'});
}

# Changement de la couleur du capteur(id)
function changeColor($pId, $pColor)
{
	global $soapClient;
	$result = $soapClient->changeColor(array("id"=>$pId, "color"=>$pColor));
	return json_decode($result->{'return'});
}

# Changement de la température du thermostat(id)
function changeTemperature($pId, $pTemperature)
{
	global $soapClient;
	$result = $soapClient->changeTemperature(array("id"=>$pId, "temperature"=>$pTemperature));
	return json_decode($result->{'return'});
}

# Changement de la luminosité du capteur(id)
function changeLuminosity($pId, $pLuminosity)
{
	global $soapClient;
	$result = $soapClient->changeLuminosity(array("id"=>$pId, "luminosity"=>$pLuminosity));
	return json_decode($result->{'return'});
}

# Chargement des statistiques de la dernière semaine (id)
function getStats($pId)
{
	global $soapClient;
	$result = $soapClient->getStats(array("id"=>$pId, "beginDate"=>date("Y-m-d H:i:s", mktime(0, 0, 0, date("m")  , date("d")-7, date("Y"))), "endDate"=>date("Y-m-d H:i:s")));
	return json_decode($result->{'return'});
}

# Ajouter un capteur
function addSensor($pType, $pName)
{
	global $soapClient;
	$result = $soapClient->addSensor(array("type"=>$pType, "name"=>$pName));
	return json_decode($result->{'return'});
}

# Supprimer un capteur
function deleteSensor($pId)
{
	global $soapClient;
	$result = $soapClient->deleteSensor(array("id"=>$pId));
	return json_decode($result->{'return'});
}

?>