<?php

class Sensor
{
	protected $id;
	protected $name;
	protected $type;
	protected $state;
	protected $realTimeValue;
	
	public function __construct($pId, $pName, $pType, $pState, $pRealTimeValue)
	{
		$this->id = $pId;
		$this->name = $pName;
		$this->type = pType;
		$this->state = pState;
		$this->realTimeValue = pRealTimeValue;
	}
	
	public function getId()
	{
		return $this->id;
	}
	
	public function getName()
	{
		return $this->name;
	}
	
	public function getSensorType()
	{
		return $this->type;
	}
	
	public function getState()
	{
		return $this->state;
	}
	
	public function getRealTimeValue()
	{
		return $this->realTimeValue;
	}
}

class ElectricMeter extends Sensor 
{
	protected $state = -2;
	
	public function __construct($pId, $pName, $pType, $pRealTimeValue)
	{
		$this->id = $pId;
		$this->name = $pName;
		$this->type = pType;
		$this->realTimeValue = pRealTimeValue;
	}
}

class SmartPlug extends Sensor {}

class SmartThermostat extends Sensor 
{
	protected $temperature;
	
	public function __construct($pId, $pName, $pType, $pState, $pRealTimeValue, $pTemperature)
	{
		$this->id = $pId;
		$this->name = $pName;
		$this->type = pType;
		$this->state = pState;
		$this->realTimeValue = pRealTimeValue;
		$this->temperature = pTemperature;
	}
	
	public function getTemperature()
	{
		return $this->temperature;
	}
}

class SmartLight extends Sensor
{
	protected $color;
	protected $luminosity;
	
	public function __construct($pId, $pName, $pType, $pState, $pRealTimeValue, $pColor, $pLuminosity)
	{
		$this->id = $pId;
		$this->name = $pName;
		$this->type = pType;
		$this->state = pState;
		$this->realTimeValue = pRealTimeValue;
		$this->color = pColor;
		$this->luminosity = pLuminosity;
	}
	
	public function getColor()
	{
		return $this->color;
	}
	
	public function getLuminosity()
	{
		return $this->luminosity;
	}
}

?>