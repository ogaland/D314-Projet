/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.device.Device;
import com.miage.device.LightBulb;
import com.miage.device.SimulateDevice;
import com.miage.device.TemperatureDevice;
import com.miage.sensors.ElectricMeterSensor;
import com.miage.sensors.ElectricalPlugSensor;
import com.miage.sensors.LightBulbSensor;
import com.miage.sensors.Sensor;
import com.miage.sensors.TemperatureSensor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author olivier
 */
public class Controller {
    
    final int maxNbSensor = 20;
    private int currentNbSensor = 0;
    private final List<Sensor> sensors = new ArrayList(maxNbSensor);
    private final List<List<Thread>> th = new ArrayList(maxNbSensor);
    
    public Controller()
    {
        try
        {
            //ajout des capteurs à la liste des sensor
            addSensor("Prise salon","Prise");
            addSensor("Compteur maison","Compteur");
            addSensor("Ampoule","Ampoule");
            addSensor("Capteur temperature","Temperature");
            
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }     
    }
    
    public List<Map<String,Object>> getListSensors() throws JsonProcessingException
    {
        //construit un string json de la liste des capteur pour envoi au client web
        List<Map<String,Object>> tmp = new ArrayList();
        for(int i =0; i < currentNbSensor; i++)
        {
            tmp.add(i,new HashMap<String, Object>());
            tmp.get(i).put("Id",sensors.get(i).getId());
            tmp.get(i).put("Type",sensors.get(i).getType());
            tmp.get(i).put("Name",sensors.get(i).getName());
            tmp.get(i).put("Info",sensors.get(i).getInformations());
        }
        return tmp;
    }
    
    
    
    public String[] getInfoSensor(int id) throws JsonProcessingException, Exception
    {
        Sensor s = getSensor(id);
        return s.getInformations();
        
    }
    
    public List<String> getStats(int id, String beginDate, String endDate) throws JsonProcessingException, Exception {
        Sensor s = getSensor(id);
        List<String> stats = s.getStats(beginDate, endDate);
        return stats;
    }
    
    public String switchPower(int id) throws Exception
    {
        Sensor s = getSensor(id);
        s.switchPower();
        return s.getDevice().getState();
    }
    
    public String changeColor(int id, String color) throws Exception
    {
        Sensor s = getSensor(id);
        s.getType();
        if("Ampoule".equals(s.getType()))
        {
            LightBulb lb = (LightBulb)s.getDevice();
            lb.setColor(color);
        }
        else
        {
            throw new Exception("Le device n'est pas une ampoule");
        }
        return color;
    }
    
    public String changeLuminosity(int id, int luminosity) throws Exception
    {
        Sensor s = getSensor(id);
        s.getType();
        if("Ampoule".equals(s.getType()))
        {
            LightBulb lb = (LightBulb)s.getDevice();
            lb.setBrightness(luminosity);
        }
        else
        {
            throw new Exception("Le device n'est pas une ampoule");
        }
        return String.valueOf(luminosity);
    }
    
    public String changeTemperature(int id, int temp) throws Exception
    {
        Sensor s = getSensor(id);
        s.getType();
        if("Temperature".equals(s.getType()))
        {
            TemperatureDevice td = (TemperatureDevice)s.getDevice();
            td.setTemperature(temp);
        }
        else
        {
            throw new Exception("Le device n'est pas un chauffage");
        }
        return String.valueOf(temp);
    }
    
    public String changeName(int id,String name) throws Exception
    {
        Sensor s = getSensor(id);
        s.setName(name);
        return s.getName();
    }
    
    public int addSensor(String nom, String type) throws Exception
    {
        try
        {
            Device device;
            /* ajoute un sensor avec pour name nom en fonction du type */
            switch(type)
            {
                case "Prise" :
                    device = new Device();
                    sensors.add(currentNbSensor, new ElectricalPlugSensor(nom, device));
                    break;
                case "Compteur":
                    device = new Device();
                    sensors.add(currentNbSensor, new ElectricMeterSensor(nom, device));
                    break;
                case "Ampoule":
                    device = new LightBulb();
                    sensors.add(currentNbSensor, new LightBulbSensor(nom, (LightBulb)device));
                    break;
                case "Temperature":
                    device = new TemperatureDevice();
                    sensors.add(currentNbSensor, new TemperatureSensor(nom, (TemperatureDevice)device));
                    break;
                default: 
                    throw new Exception("Vous devez indiquer un type de capteur");
            }
            
            List<Thread> threadSensor = new ArrayList();
            threadSensor.add(new Thread(new SimulateDevice(device)));
            threadSensor.add(new Thread((Runnable) sensors.get(currentNbSensor)));
            threadSensor.get(0).start();
            threadSensor.get(1).start();
            
            th.add(currentNbSensor,threadSensor);
            
            currentNbSensor++;

            /*Retourne l'indice du sensor nouvellement créé*/
            return (currentNbSensor - 1);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    
    public String deleteSensor(int id)
    {
        //construit un string json de la liste des capteur pour envoi au client web
        List<Map<String,Object>> tmp = new ArrayList();
        for(int i =0; i < currentNbSensor; i++)
        {
            if(sensors.get(i).getId() == id)
            {
                sensors.remove(i);
                th.remove(i);
                return "Le capteur à été supprimé";
            }        
        }
        return "Impossible de supprimer le capteur";
    }
    
    private Sensor getSensor(int id) throws Exception
    {
        for(int i = 0; i < currentNbSensor; i++)
        {
            if(sensors.get(i).getId() == id )
            {
                return sensors.get(i);
            }
        }
        throw new Exception("Impossible de trouver le sensor");
    }
    
    
}
